package com.paymybuddy.paymybuddy.transfer.ui;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.paymybuddy.paymybuddy.exception.FunctionalException;
import com.paymybuddy.paymybuddy.transfer.service.AccountService;
import com.paymybuddy.paymybuddy.transfer.ui.BankOperationForm.BankOperationType;
import com.paymybuddy.paymybuddy.utils.MainLogger;

import jakarta.validation.Valid;

@Controller
public class AccountController {

    private static final MainLogger logger = MainLogger.getLogger(AccountController.class);

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/transfer")
    public String initTransfer(Model model) {
        logger.info("Getting the transfer page");
        final SendMoneyForm sendMoneyForm = new SendMoneyForm();
        model.addAttribute("transaction", sendMoneyForm);
        model.addAttribute("transactions", accountService.findAllAuthenticatedUserTransactions());
        return "transfer";
    }

    @PostMapping("/transfer")
    public ResponseEntity<SendMoneyResponse> pay(@RequestBody SendMoneyForm sendMoneyForm) {
        try {
            logger.info("Trying to send {0}€ to {1}: {2}", sendMoneyForm.getAmount(), sendMoneyForm.getFriend(),
                    sendMoneyForm.getDescription());
            // TODO amount validation and ensure connection is a friend
            accountService.send(sendMoneyForm.getFriend(), sendMoneyForm.getAmount(), sendMoneyForm.getDescription());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SendMoneyResponse(HttpStatus.CREATED.value(), "Transaction done"));
        } catch (final FunctionalException e) {
            logger.error("An error has occured: {0}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new SendMoneyResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
            // return ResponseEntity.status(e.getStatus()).body(new SendMoneyResponse(e.getStatus().value(), e.getMessage()));
        }
    }

    @GetMapping("/profile")
    public String initBankOperation(Model model) {
        logger.info("Getting the profile page");
        final BankOperationForm bankOperationForm = new BankOperationForm();
        model.addAttribute("bankOperation", bankOperationForm);
        return "profile";
    }

    @PostMapping("/profile")
    public String handleBankOperation(@Valid @ModelAttribute("bankOperation") BankOperationForm bankOperationForm) {
        logger.info("Posting the filled form");
        validateOperationType();
        if (bankOperationForm.getType() == BankOperationType.WITHDRAW) {
            accountService.withdraw(bankOperationForm.getAmount(), bankOperationForm.getDescription());
        } else {
            accountService.deposit(bankOperationForm.getAmount(), bankOperationForm.getDescription());
        }
        return "profile";
    }

    private void validateOperationType() {
        final List<BankOperationType> types = Arrays.asList(BankOperationForm.BankOperationType.values());
        if (!(types.contains(BankOperationType.WITHDRAW) || types.contains(BankOperationType.DEPOSIT))) {
            // TODO définir exception personnalisée
            throw new RuntimeException();
        }

    }
}
