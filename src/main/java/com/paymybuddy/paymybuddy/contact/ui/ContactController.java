package com.paymybuddy.paymybuddy.contact.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.paymybuddy.paymybuddy.utils.MainLogger;

@Controller
public class ContactController {

    private static final MainLogger logger = MainLogger.getLogger(ContactController.class);

    @GetMapping("/contact")
    public String getContactPage(Model model) {
        logger.info("Getting the contact page");
        return "contact";
    }
}