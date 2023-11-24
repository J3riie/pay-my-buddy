package com.paymybuddy.paymybuddy.transfer.service;

import static com.paymybuddy.paymybuddy.utils.UserUtil.getAuthenticatedUserEmail;

import java.math.BigDecimal;

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
        final Account userAccount = getAuthenticatedUserAccount();
        final Account connectionAccount = accountRepository.findByUsernameOrEmail(connection).orElseThrow(
                () -> new FunctionalException(String.format("No account found for connection %s", connection)));
        if (!userAccount.canSendTo(connectionAccount)) {
            throw new FunctionalException(
                    String.format("User %s has no friend with email %s", userAccount.getUsername(), connection));
        }
        logger.info("Sending {0}€ from {1} to {2}", amount, userAccount.getUsername(), connection);
        final Transaction transaction = userAccount.sendMoney(connectionAccount, amount, description);
        saveTransactionAndAccounts(transaction, userAccount, connectionAccount);
    }

    public void deposit(BigDecimal amount, String description) {
        final Account account = getAuthenticatedUserAccount();
        logger.info("Adding {0}€ to {1}'s account", amount, account.getUsername());
        final Transaction transaction = account.deposit(amount, description);
        saveTransactionAndAccounts(transaction, account);
    }

    public void withdraw(BigDecimal amount, String description) {
        final Account account = getAuthenticatedUserAccount();
        logger.info("Withdrawing {0}€ from {1}'s account", amount, account.getUsername());
        final Transaction transaction = account.withdraw(amount, description);
        saveTransactionAndAccounts(transaction, account);
    }

    private Account getAuthenticatedUserAccount() {
        final String authenticated = getAuthenticatedUserEmail();
        return accountRepository.findByUsernameOrEmail(authenticated).orElseThrow(
                () -> new FunctionalException(String.format("No account found for user %s", authenticated)));
    }

    private void saveTransactionAndAccounts(Transaction transaction, Account... accounts) {
        transactionRepository.save(transaction);
        for (final Account account : accounts) {
            accountRepository.save(account);
        }
    }

}
