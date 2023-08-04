package com.paymybuddy.paymybuddy.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.paymybuddy.paymybuddy.user.model.User;
import com.paymybuddy.paymybuddy.user.repository.UserRepository;

@Service
public class UserRegistrationService {

    private final UserRepository userRepository;

    public UserRegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createAccount(String email, String password) {
        final Optional<User> optionalUser = userRepository.checkIfAccountExists(email);
        if (optionalUser.isPresent()) {
            // ajouter des exceptions personnalisées
            throw new RuntimeException("An account with this credential already exists");
        }
        final User user = new User(email, password);
        userRepository.saveUser(user);
    }
}
