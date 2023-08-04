package com.paymybuddy.paymybuddy.user.ui;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class PasswordValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegistrationForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required.password", "Field name is required.");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "required.confirmPassword", "Field name is required.");

        final UserRegistrationForm user = (UserRegistrationForm) target;

        if (!(user.getPassword().equals(user.getPasswordConfirmation()))) {
            errors.rejectValue("password", "notmatch.password");
        }

    }

}