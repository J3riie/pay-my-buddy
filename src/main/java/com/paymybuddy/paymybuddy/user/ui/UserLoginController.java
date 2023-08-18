package com.paymybuddy.paymybuddy.user.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.paymybuddy.paymybuddy.user.service.UserLoginService;
import com.paymybuddy.paymybuddy.utils.MainLogger;

import jakarta.validation.Valid;

@Controller
public class UserLoginController {

    private static final MainLogger logger = MainLogger.getLogger(UserLoginController.class);

    private final UserLoginService userLoginService;

    public UserLoginController(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

    @GetMapping("/login")
    public String displayUserLoginForm(Model model) {
        logger.info("Getting the login form");
        final UserLoginForm userLoginForm = new UserLoginForm();
        model.addAttribute("user", userLoginForm);
        return "log_in";
    }

    @PostMapping("/login")
    public String handleUserForm(@Valid @ModelAttribute("user") UserLoginForm userLoginForm, Model model) {
        logger.info("Posting the filled form");
        model.addAttribute("userEmail", userLoginForm.getEmail());
        userLoginService.loginUser(userLoginForm.getEmail(), userLoginForm.getPassword());
        return "home";
    }

}
