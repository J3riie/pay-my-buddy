package com.paymybuddy.paymybuddy.transfer.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.paymybuddy.paymybuddy.transfer.service.SendMoneyService;
import com.paymybuddy.paymybuddy.utils.MainLogger;

import jakarta.validation.Valid;

@Controller
public class SendMoneyController {

    private static final MainLogger logger = MainLogger.getLogger(SendMoneyController.class);

    private final SendMoneyService sendMoneyService;

    public SendMoneyController(SendMoneyService sendMoneyService) {
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
        sendMoneyService.sendMoney(sendMoneyForm.getFriend(), sendMoneyForm.getAmount());
        return "transfer";
    }
}
