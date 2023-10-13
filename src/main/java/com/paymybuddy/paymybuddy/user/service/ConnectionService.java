package com.paymybuddy.paymybuddy.user.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.paymybuddy.paymybuddy.exception.FunctionalException;
import com.paymybuddy.paymybuddy.user.model.User;
import com.paymybuddy.paymybuddy.user.repository.UserRepository;
import com.paymybuddy.paymybuddy.utils.MainLogger;

@Service
public class ConnectionService {

    private static final MainLogger logger = MainLogger.getLogger(ConnectionService.class);

    private final UserRepository userRepository;

    public ConnectionService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addConnection(String newConnectionEmail) {
        final String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Adding {0} to {1}'s connections", newConnectionEmail, authenticatedUserEmail);
        logger.info("Checking if given connection email is present in the database");
        checkIfAccountExists(newConnectionEmail);
        final User user = userRepository.findByEmail(authenticatedUserEmail);
        logger.info("Checking if given connection email is already {0}'s friend", authenticatedUserEmail);
        if (user.getFriends().contains(newConnectionEmail)) {
            logger.error("{0} already has {1} as a friend", authenticatedUserEmail, newConnectionEmail);
            // Returns a 200 OK even though an error occurs because the user can recover by changing its input email
            throw new FunctionalException("Already added to the friendlist", HttpStatus.OK);
        }
        logger.info("Request is valid, proceeding...");
        userRepository.addConnection(newConnectionEmail, user);

    }

    private void checkIfAccountExists(String email) {
        if (!userRepository.checkIfAccountExists(email)) {
            logger.error("{0} not present in the database", email);
            // Returns a 200 OK even though an error occurs because the user can recover by changing its input email
            throw new FunctionalException("No account with this email exist", HttpStatus.OK);
        }
    }
}
