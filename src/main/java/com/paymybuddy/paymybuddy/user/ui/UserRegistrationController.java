package com.paymybuddy.paymybuddy.user.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.paymybuddy.paymybuddy.user.service.UserRegistrationService;

@Controller
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;

    public UserRegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping("/user")
    public void createAccount(@ModelAttribute UserRegistrationRequest userRegistrationRequest, Model model) {
        // Faire les validations du modèle (password est bon, email est valide)
        userRegistrationService.createAccount(userRegistrationRequest.getEmail(), userRegistrationRequest.getPassword());
    }

}
