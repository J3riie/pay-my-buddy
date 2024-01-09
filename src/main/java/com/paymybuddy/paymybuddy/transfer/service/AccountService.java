package com.paymybuddy.paymybuddy.transfer.service;

import static com.paymybuddy.paymybuddy.utils.UserUtil.getAuthenticatedUserEmail;

import java.math.BigDecimal;
import java.util.Arrays;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Transaction> findAllTransactions(Pageable pageable) {
        final User authenticatedUser = getUserInformation();
        return transactionRepository.findAllAuthenticatedUserTransactions(authenticatedUser.getUsername(), pageable);
    }

    public void send(String connection, BigDecimal amount, String description) {
        final User authenticatedUser = userService.getUser(getAuthenticatedUserEmail());
        logger.info(authenticatedUser.getEmail());
        final Account userAccount = authenticatedUser.getAccount();

        final User connectionUser = userService.getUser(connection);
        final Account connectionAccount = connectionUser.getAccount();

        if (!userAccount.canSendTo(connectionAccount, amount)) {
            logger.info("WARNING, cant send to {0}", connection);
            throw new FunctionalException(
                    String.format("User %s either has no friend with email or username %s or doesnt have enough money",
                            userAccount.getUsername(), connection),
                    HttpStatus.BAD_REQUEST);
        }
        logger.info("Sending {0}€ from {1} to {2}", amount, userAccount.getUsername(), connection);
        final Transaction transaction = userAccount.sendMoney(connectionAccount, amount, description);
        saveTransactionAndAccounts(transaction, userAccount, connectionAccount);
    }

    public void deposit(BigDecimal amount, String description) {
        final Account account = userService.getUser(getAuthenticatedUserEmail()).getAccount();
        logger.info("Current {0}s balance: {1} ", account.getUsername(), account.getBalance());
        logger.info("Adding {0}€ to {1}s account", amount, account.getUsername());
        final Transaction transaction = account.deposit(amount, description);
        logger.info("New balance: {0}", account.getBalance());
        saveTransactionAndAccounts(transaction, account);
    }

    public void withdraw(BigDecimal amount, String description) {
        final Account account = userService.getUser(getAuthenticatedUserEmail()).getAccount();
        logger.info("Current {0}s balance: {1} ", account.getUsername(), account.getBalance());
        logger.info("Withdrawing {0}€ from {1}s account", amount, account.getUsername());
        if (!account.canWithdraw(amount)) {
            logger.info("WARNING, cant withdraw this much money");
            throw new FunctionalException(String.format("User %s doesnt have enough money", account.getUsername()),
                    HttpStatus.BAD_REQUEST);
        }
        final Transaction transaction = account.withdraw(amount, description);
        logger.info("New balance: {0}", account.getBalance());
        saveTransactionAndAccounts(transaction, account);
    }

    private void saveTransactionAndAccounts(Transaction transaction, Account... accounts) {
        transactionRepository.save(transaction);
        accountRepository.saveAll(Arrays.asList(accounts));
    }

    public User getUserInformation() {
        return userService.getUser(getAuthenticatedUserEmail());
    }
}
