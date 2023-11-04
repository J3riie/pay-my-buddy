package com.paymybuddy.paymybuddy.transfer.service;

import static com.paymybuddy.paymybuddy.utils.UserUtil.getAuthenticatedUserEmail;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.paymybuddy.paymybuddy.transfer.repository.TransactionEntity;
import com.paymybuddy.paymybuddy.transfer.repository.TransactionRepository;
import com.paymybuddy.paymybuddy.user.model.User;
import com.paymybuddy.paymybuddy.user.repository.UserRepository;
import com.paymybuddy.paymybuddy.utils.MainLogger;

@Service
public class TransactionService {

    private static final MainLogger logger = MainLogger.getLogger(TransactionService.class);

    private final UserRepository userRepository;

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public void deposit(BigDecimal amount) {
    }

    public void sendMoney(String friend, BigDecimal amount) {
        final User authenticatedUser = userRepository.findByEmail(getAuthenticatedUserEmail());
        logger.info("Sending {0}€ from {1} to {2}", amount, authenticatedUser.getUsername(), friend);
        final User friendUser = userRepository.findByUsername(friend);
        final BigDecimal authenticatedUserBalance = authenticatedUser.getAccount().getBalance().subtract(amount);
        final BigDecimal friendBalance = friendUser.getAccount().getBalance().add(amount);
        authenticatedUser.getAccount().setBalance(authenticatedUserBalance);
        friendUser.getAccount().setBalance(friendBalance);
        userRepository.saveUser(authenticatedUser);
        userRepository.saveUser(friendUser);

        final TransactionEntity transaction = TransactionEntity.createSendMoneyTransaction(amount, friend,
                authenticatedUser.getUsername(), "");
        transactionRepository.save(transaction);

    }

    public void withdraw(BigDecimal amount) {
    }

}
