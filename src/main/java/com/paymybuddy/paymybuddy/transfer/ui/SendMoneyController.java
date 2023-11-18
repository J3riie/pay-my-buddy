package com.paymybuddy.paymybuddy.transfer.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.paymybuddy.paymybuddy.transfer.service.AccountService;
import com.paymybuddy.paymybuddy.utils.MainLogger;

import jakarta.validation.Valid;

@Controller
public class SendMoneyController {

    private static final MainLogger logger = MainLogger.getLogger(SendMoneyController.class);

    private final AccountService accountService;

    public SendMoneyController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/transfer")
    public String initTransfer(Model model) {
        logger.info("Getting the transfer page");
        final SendMoneyForm sendMoneyForm = new SendMoneyForm();
        model.addAttribute("transaction", sendMoneyForm);
        return "transfer";
    }

    @PostMapping("/transfer")
    public String handleTransfer(@Valid @ModelAttribute("transaction") SendMoneyForm sendMoneyForm) {
        logger.info("Posting the filled form");
        // TODO amount validation and ensure connection is a friend
        accountService.send(sendMoneyForm.getFriend(), sendMoneyForm.getAmount(), sendMoneyForm.getDescription());
        return "transfer";
    }
}
