package com.paymybuddy.paymybuddy.transfer.service;

import static com.paymybuddy.paymybuddy.utils.UserUtil.getAuthenticatedUserEmail;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.paymybuddy.paymybuddy.exception.FunctionalException;
import com.paymybuddy.paymybuddy.transfer.model.Account;
import com.paymybuddy.paymybuddy.transfer.model.Transaction;
import com.paymybuddy.paymybuddy.transfer.repository.AccountRepository;
import com.paymybuddy.paymybuddy.transfer.repository.TransactionRepository;
import com.paymybuddy.paymybuddy.utils.MainLogger;

@Service
public class AccountService {

    private static final MainLogger logger = MainLogger.getLogger(AccountService.class);

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public void send(String connection, BigDecimal amount, String description) {
        final String authenticated = getAuthenticatedUserEmail();
        final Account userAccount = accountRepository.findByUsernameOrEmail(authenticated).orElseThrow(
                () -> new FunctionalException(String.format("No account found for user %s", authenticated)));
        final Account connectionAccount = accountRepository.findByUsernameOrEmail(connection).orElseThrow(
                () -> new FunctionalException(String.format("No account found for connection %s", connection)));
        if (!userAccount.canSendTo(connectionAccount)) {
            throw new FunctionalException(
                    String.format("User %s has no friend with email %s", authenticated, connection));
        }
        logger.info("Sending {0}€ from {1} to {2}", amount, userAccount.getUsername(), connection);
        final Transaction transaction = userAccount.makeTransaction(connectionAccount, amount, description);
        accountRepository.saveAll(List.of(userAccount, connectionAccount));
        transactionRepository.save(transaction);
    }

    public void deposit(BigDecimal amount) {
    }

    public void withdraw(BigDecimal amount) {
    }

}
