package com.paymybuddy.paymybuddy.transfer.ui;

import com.paymybuddy.paymybuddy.transfer.service.AccountService;
import com.paymybuddy.paymybuddy.utils.MainLogger;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SendMoneyController {

    private static final MainLogger logger = MainLogger.getLogger(SendMoneyController.class);

    private final AccountService sendMoneyService;

    public SendMoneyController(AccountService sendMoneyService) {
        this.sendMoneyService = sendMoneyService;
    }

    @GetMapping("/transfer")
    public String displaySendMoneyFormAndTransactions(Model model) {
        logger.info("Getting the transfer page");
        final SendMoneyForm sendMoneyForm = new SendMoneyForm();
        model.addAttribute("transaction", sendMoneyForm);
        return "transfer";
    }

    @PostMapping("/transfer")
    public String handleUserForm(@Valid @ModelAttribute("transaction") SendMoneyForm sendMoneyForm, Model model) {
        logger.info("Posting the filled form");
        sendMoneyService.send(sendMoneyForm.getFriend(), sendMoneyForm.getAmount(), sendMoneyForm.getDescription());
        return "transfer";
    }
}
