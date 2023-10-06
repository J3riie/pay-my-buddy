package com.paymybuddy.paymybuddy.user.ui;

import com.paymybuddy.paymybuddy.exception.FunctionalException;
import com.paymybuddy.paymybuddy.user.service.UserRegistrationService;
import com.paymybuddy.paymybuddy.utils.MainLogger;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserRegistrationController {

    private static final MainLogger logger = MainLogger.getLogger(UserRegistrationController.class);

    private final UserRegistrationService userRegistrationService;

    private static final String USESR_REGISTRATION_TEMPLATE = "user_registration";

    public UserRegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @GetMapping("/register")
    public String displayUserRegistrationForm(Model model) {
        logger.info("Getting the registration form");
        final UserRegistrationForm userRegistrationForm = new UserRegistrationForm();
        model.addAttribute("user", userRegistrationForm);
        return USESR_REGISTRATION_TEMPLATE;
    }

    @PostMapping("/register")
    public String handleUserForm(@Valid @ModelAttribute("user") UserRegistrationForm userRegistrationForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("One or several errors occured during fields validation:");
            for (final ObjectError error : result.getAllErrors()) {
                logger.error("{0}", error.getDefaultMessage());
            }
            return USESR_REGISTRATION_TEMPLATE;
        }
        try {
            userRegistrationService.createAccount(userRegistrationForm.getEmail(), userRegistrationForm.getPassword());
            return "redirect:/login";
        } catch (final FunctionalException e) {
            model.addAttribute("emailInUse", true);
            logger.error("Email already exists", e);
            return USESR_REGISTRATION_TEMPLATE;
        }
    }

}
