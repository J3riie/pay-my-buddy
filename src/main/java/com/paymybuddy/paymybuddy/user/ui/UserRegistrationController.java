package com.paymybuddy.paymybuddy.user.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.paymybuddy.paymybuddy.exception.FunctionalException;
import com.paymybuddy.paymybuddy.user.service.UserRegistrationService;
import com.paymybuddy.paymybuddy.utils.MainLogger;

import jakarta.validation.Valid;

@Controller
public class UserRegistrationController {

    private static final MainLogger logger = MainLogger.getLogger(UserRegistrationController.class);

    private final UserRegistrationService userRegistrationService;

    public UserRegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @GetMapping("/register")
    public String displayUserRegistrationForm(Model model) {
        logger.info("Getting the registration form");
        final UserRegistrationForm userRegistrationForm = new UserRegistrationForm();
        model.addAttribute("user", userRegistrationForm);
        return "user_registration";
    }

    @PostMapping("/register")
    public String handleUserForm(@Valid @ModelAttribute("user") UserRegistrationForm userRegistrationForm, Model model) {
        logger.info("Posting the filled form");
        try {
            model.addAttribute("userEmail", userRegistrationForm.getEmail());
            userRegistrationService.createAccount(userRegistrationForm.getEmail(), userRegistrationForm.getPassword());
            return "redirect:/login";
        } catch (final FunctionalException e) {
            model.addAttribute("emailInUse", true);
            return "user_registration";
        }
    }

}
