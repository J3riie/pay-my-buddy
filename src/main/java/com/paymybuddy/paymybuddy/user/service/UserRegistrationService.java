package com.paymybuddy.paymybuddy.user.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.paymybuddy.paymybuddy.user.model.User;
import com.paymybuddy.paymybuddy.user.repository.UserRepository;

@Service
public class UserRegistrationService {

    private static final Logger logger = LoggerFactory.getLogger(UserRegistrationService.class);

    private final UserRepository userRepository;

    public UserRegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createAccount(String email, String password) {
        logger.info("Creating a new account for email {}", email);
        logger.info("Checking if the email is already present in the database");
        final Optional<User> optionalUser = userRepository.checkIfAccountExists(email);
        if (optionalUser.isPresent()) {
            logger.error("{} already is present in the database", email);
            // ajouter des exceptions personnalisées
            throw new RuntimeException("An account with this credential already exists");
        }
        logger.info("The email is new, saving the new user in the database");
        final User user = new User(email, password);
        userRepository.saveUser(user);
    }
}
