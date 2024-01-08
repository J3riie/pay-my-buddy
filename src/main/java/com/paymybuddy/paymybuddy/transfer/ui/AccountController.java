package com.paymybuddy.paymybuddy.transfer.ui;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.paymybuddy.paymybuddy.exception.FunctionalException;
import com.paymybuddy.paymybuddy.transfer.model.Transaction;
import com.paymybuddy.paymybuddy.transfer.service.AccountService;
import com.paymybuddy.paymybuddy.transfer.ui.BankOperationForm.BankOperationType;
import com.paymybuddy.paymybuddy.user.model.User;
import com.paymybuddy.paymybuddy.utils.MainLogger;

import jakarta.validation.Valid;

@Controller
public class AccountController {

    private static final MainLogger logger = MainLogger.getLogger(AccountController.class);

    private final AccountService accountService;
    private final Validator validator;

    public AccountController(AccountService accountService, Validator validator) {
        this.accountService = accountService;
        this.validator = validator;
    }

    @GetMapping("/transfer")
    public String initTransfer(Model model, @RequestParam(defaultValue = "1") int page) {
        logger.info("Getting the transfer page");
        final SendMoneyForm sendMoneyForm = new SendMoneyForm();
        model.addAttribute("transaction", sendMoneyForm);
        final Pageable paging = PageRequest.of(page - 1, 4);
        final Page<Transaction> pageTransactions = accountService.findAllTransactions(paging);
        final List<Transaction> transactions = pageTransactions.getContent();
        model.addAttribute("transactions", transactions);
        model.addAttribute("currentPage", pageTransactions.getNumber() + 1);
        model.addAttribute("totalItems", pageTransactions.getTotalElements());
        model.addAttribute("totalPages", pageTransactions.getTotalPages());
        return "transfer";
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> pay(@Valid @RequestBody SendMoneyForm sendMoneyForm) {
        Set<ConstraintViolation<SendMoneyForm>> violations = validator.validate(sendMoneyForm);
        if (!violations.isEmpty()) {
            // TODO throw your custom defined exception here
            throw new ConstraintViolationException(violations);
        }
        try {
            logger.info("Trying to send {0}€ to {1}: {2}", sendMoneyForm.getAmount(), sendMoneyForm.getFriend(),
                    sendMoneyForm.getDescription());
            accountService.send(sendMoneyForm.getFriend(), sendMoneyForm.getAmount(), sendMoneyForm.getDescription());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new TransactionResponse(HttpStatus.CREATED.value(), "Transaction done"));
        } catch (final FunctionalException e) {
            logger.error("An error has occured: {0}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new TransactionResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @GetMapping("/profile")
    public String initBankOperation(Model model) {
        logger.info("Getting the profile page");
        final BankOperationForm bankOperationForm = new BankOperationForm();
        final User connectedUser = accountService.getUserInformation();
        model.addAttribute("username", connectedUser.getUsername());
        model.addAttribute("email", connectedUser.getEmail());
        model.addAttribute("balance", connectedUser.getAccount().getBalance());
        model.addAttribute("bankOperation", bankOperationForm);
        return "profile";
    }

    @PostMapping("/profile")
    public ResponseEntity<TransactionResponse> handleBankOperation(
            @Valid @RequestBody BankOperationForm bankOperationForm) {
        try {
            logger.info("Trying to {0} {1}€: {2}", bankOperationForm.getType().toString().toLowerCase(),
                    bankOperationForm.getAmount(), bankOperationForm.getDescription());
            validateOperationType();
            if (bankOperationForm.getType() == BankOperationType.WITHDRAW) {
                accountService.withdraw(bankOperationForm.getAmount(), bankOperationForm.getDescription());
                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(new TransactionResponse(HttpStatus.ACCEPTED.value(), "Withdrawal done"));
            } else {
                accountService.deposit(bankOperationForm.getAmount(), bankOperationForm.getDescription());
                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(new TransactionResponse(HttpStatus.ACCEPTED.value(), "Deposit done"));
            }
        } catch (final FunctionalException e) {
            logger.error("An error has occured: {0}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new TransactionResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    private void validateOperationType() {
        final List<BankOperationType> types = Arrays.asList(BankOperationForm.BankOperationType.values());
        if (!(types.contains(BankOperationType.WITHDRAW) || types.contains(BankOperationType.DEPOSIT))) {
            throw new RuntimeException();
        }

    }
}
