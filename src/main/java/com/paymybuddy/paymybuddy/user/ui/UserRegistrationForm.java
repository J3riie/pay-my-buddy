package com.paymybuddy.paymybuddy.user.ui;

import com.paymybuddy.paymybuddy.config.annotation.PasswordMatches;
import com.paymybuddy.paymybuddy.config.annotation.ValidEmail;

import jakarta.validation.constraints.NotBlank;

@PasswordMatches
public class UserRegistrationForm {

    @ValidEmail
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String passwordConfirmation;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

}
