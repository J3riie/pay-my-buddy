package com.paymybuddy.paymybuddy.transfer.service;

import static com.paymybuddy.paymybuddy.utils.UserUtil.getAuthenticatedUserEmail;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.paymybuddy.exception.FunctionalException;
import com.paymybuddy.paymybuddy.transfer.model.Account;
import com.paymybuddy.paymybuddy.transfer.model.Transaction;
import com.paymybuddy.paymybuddy.transfer.repository.AccountRepository;
import com.paymybuddy.paymybuddy.transfer.repository.TransactionRepository;
import com.paymybuddy.paymybuddy.user.model.User;
import com.paymybuddy.paymybuddy.user.service.UserService;
import com.paymybuddy.paymybuddy.utils.MainLogger;

@Service
@Transactional
public class AccountService {

    private static final MainLogger logger = MainLogger.getLogger(AccountService.class);

    private final AccountRepository accountRepository;

    private final UserService userService;

    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, UserService userService,
            TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.userService = userService;
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> findAllAuthenticatedUserTransactions() {
        final User authenticatedUser = userService.getUser(getAuthenticatedUserEmail());
        return transactionRepository.findAllAuthenticatedUserTransactions(authenticatedUser.getUsername());
    }

    public void send(String connection, BigDecimal amount, String description) {
        final User authenticatedUser = userService.getUser(getAuthenticatedUserEmail());
        final Account userAccount = authenticatedUser.getAccount();

        final User connectionUser = userService.getUser(connection);
        final Account connectionAccount = connectionUser.getAccount();

        if (!userAccount.canSendTo(connectionAccount)) {
            logger.info("WARNING, can't send to {0}", connectionAccount);
            throw new FunctionalException(
                    String.format("User %s has no friend with email %s", userAccount.getUsername(), connection),
                    HttpStatus.BAD_REQUEST);
        }
        logger.info("Sending {0}€ from {1} to {2}", amount, userAccount.getUsername(), connection);
        final Transaction transaction = userAccount.sendMoney(connectionAccount, amount, description);
        saveTransactionAndAccounts(transaction, userAccount, connectionAccount);
    }

    public void deposit(BigDecimal amount, String description) {
        final Account account = userService.getUser(getAuthenticatedUserEmail()).getAccount();
        logger.info("Adding {0}€ to {1}'s account", amount, account.getUsername());
        final Transaction transaction = account.deposit(amount, description);
        saveTransactionAndAccounts(transaction, account);
    }

    public void withdraw(BigDecimal amount, String description) {
        final Account account = userService.getUser(getAuthenticatedUserEmail()).getAccount();
        logger.info("Withdrawing {0}€ from {1}'s account", amount, account.getUsername());
        final Transaction transaction = account.withdraw(amount, description);
        saveTransactionAndAccounts(transaction, account);
    }

    private void saveTransactionAndAccounts(Transaction transaction, Account... accounts) {
        transactionRepository.save(transaction);
        accountRepository.saveAll(Arrays.asList(accounts));
    }
}
