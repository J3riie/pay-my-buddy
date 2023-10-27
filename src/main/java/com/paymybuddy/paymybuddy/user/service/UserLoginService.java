package com.paymybuddy.paymybuddy.user.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.paymybuddy.paymybuddy.exception.FunctionalException;
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
        if (!userRepository.checkIfAccountExists(email)) {
            logger.error("{0} is not present in the database", email);
            // Returns a 200 OK even though an error occurs because the user can recover by changing its input email
            throw new FunctionalException("No account matches this email address", HttpStatus.OK);
        }
        logger.info("Email exists, checking the password");
        if (!userRepository.loginUser(email, password)) {
            logger.error("No account for email {0} and password {1}", email, password);
            // Returns a 200 OK even though an error occurs because the user can recover by changing its input email
            throw new FunctionalException("The given email and password do no match a known account", HttpStatus.OK);
        }
        logger.info("Email and password are matching");
        logger.info("User successfully logged in");
    }

}
