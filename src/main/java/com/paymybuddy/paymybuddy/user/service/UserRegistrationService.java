package com.paymybuddy.paymybuddy.user.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.paymybuddy.paymybuddy.exception.FunctionalException;
import com.paymybuddy.paymybuddy.user.model.User;
import com.paymybuddy.paymybuddy.user.repository.UserRepository;
import com.paymybuddy.paymybuddy.utils.MainLogger;

@Service
public class UserRegistrationService {

    private static final MainLogger logger = MainLogger.getLogger(UserRegistrationService.class);

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserRegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createAccount(String email, String password) {
        logger.info("Creating a new account for email {0}", email);
        logger.info("Checking if the email is already present in the database");
        final Optional<User> optionalUser = userRepository.checkIfAccountExists(email);
        if (optionalUser.isPresent()) {
            logger.error("{0} already is present in the database", email);
            // Returns a 200 OK even though an error occurs because the user can recover by changing its input email
            throw new FunctionalException("An account with this credential already exists", HttpStatus.OK);
        }
        logger.info("The email is new, saving the new user in the database");
        userRepository.saveUser(new User(email, passwordEncoder.encode(password)));
    }
}
