package com.paymybuddy.paymybuddy.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.paymybuddy.paymybuddy.user.model.User;
import com.paymybuddy.paymybuddy.user.repository.UserRepository;
import com.paymybuddy.paymybuddy.utils.MainLogger;

@Service
public class UserLoginService {

    private static final MainLogger logger = MainLogger.getLogger(UserLoginService.class);

    private final UserRepository userRepository;

    public UserLoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void loginUser(String email, String password) {
        logger.info("Logging in {0}", email);
        logger.info("Checking if the email is present in the database");
        final Optional<User> optionalUser = userRepository.checkIfAccountExists(email);
        if (optionalUser.isEmpty()) {
            logger.error("{0} is not present in the database", email);
            // ajouter des exceptions personnalisées
            throw new RuntimeException("No account matches this email address");
        }
        logger.info("Email exists, checking the password");
        if (!userRepository.loginUser(email, password)) {
            logger.error("No account for email {0} and password {1}", email, password);
            // ajouter des exceptions personnalisées
            throw new RuntimeException("The given email and password do not match");
        }
        logger.info("Email and password are matching");
        logger.info("User successfully logged in");
    }

}
