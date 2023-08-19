package com.paymybuddy.paymybuddy.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.paymybuddy.paymybuddy.user.model.User;
import com.paymybuddy.paymybuddy.user.repository.UserRepository;
import com.paymybuddy.paymybuddy.utils.MainLogger;

@Service
public class UserRegistrationService {

    private static final MainLogger logger = MainLogger.getLogger(UserRegistrationService.class);

    private final UserRepository userRepository;

    public UserRegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createAccount(String email, String password) {
        logger.info("Creating a new account for email {0}", email);
        logger.info("Checking if the email is already present in the database");
        final Optional<User> optionalUser = userRepository.checkIfAccountExists(email);
        if (optionalUser.isPresent()) {
            logger.error("{0} already is present in the database", email);
            // ajouter des exceptions personnalisées
            throw new RuntimeException("An account with this credential already exists");
        }
        logger.info("The email is new, saving the new user in the database");
        final User user = new User(email, password);
        userRepository.saveUser(user);
    }
}
