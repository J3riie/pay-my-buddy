package com.paymybuddy.paymybuddy.user.ui;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegistrationForm {

    @Email(message = "Le mail est invalide")
    @NotBlank
    @Size(min = 15, max = 50, message = "Veuillez saisir un mail valide")
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
