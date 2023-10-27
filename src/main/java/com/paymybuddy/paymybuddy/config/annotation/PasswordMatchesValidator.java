package com.paymybuddy.paymybuddy.config.annotation;

import com.paymybuddy.paymybuddy.user.ui.UserRegistrationForm;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        final UserRegistrationForm user = (UserRegistrationForm) obj;
        return user.getPassword().equals(user.getPasswordConfirmation());
    }
}