package com.paymybuddy.paymybuddy.user.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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
    public RedirectView handleUserForm(@Valid @ModelAttribute("user") UserRegistrationForm userRegistrationForm,
            BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            logger.error("One or several errors occured during fields validation:");
            for (final ObjectError error : result.getAllErrors()) {
                logger.error("{0}", error.getDefaultMessage());
            }
            return new RedirectView("/register", true);
        }
        try {
            userRegistrationService.createAccount(userRegistrationForm.getEmail(), userRegistrationForm.getPassword());
            redirectAttributes.addFlashAttribute("registrationSuccess", true);
            return new RedirectView("/login", true);
        } catch (final FunctionalException e) {
            model.addAttribute("emailInUse", true);
            logger.error("Email already exists", e);
            return new RedirectView("/register", true);
        }
    }

}
