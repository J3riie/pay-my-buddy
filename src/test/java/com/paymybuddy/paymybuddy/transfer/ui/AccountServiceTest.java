package com.paymybuddy.paymybuddy.transfer.ui;

import com.paymybuddy.paymybuddy.transfer.model.Account;
import com.paymybuddy.paymybuddy.transfer.repository.AccountRepository;
import com.paymybuddy.paymybuddy.transfer.repository.TransactionRepository;
import com.paymybuddy.paymybuddy.transfer.service.AccountService;
import com.paymybuddy.paymybuddy.user.model.User;
import com.paymybuddy.paymybuddy.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountService accountService;

    @BeforeEach
    void init() {
        User user = new User("johndoe@test.com", "johndoe", "passer@123");
        User bobby = new User("bobby@test.com", "bobby", "passer@123");
        userRepository.saveAll(List.of(user, bobby));

        Account userAccount = new Account();
        userAccount.setUser(user);
        userAccount.setBalance(BigDecimal.valueOf(100.00));
        userAccount.setRib(UUID.randomUUID().toString());

        Account connectionAccount = new Account();
        connectionAccount.setUser(bobby);
        connectionAccount.setBalance(BigDecimal.valueOf(25.00));
        connectionAccount.setRib(UUID.randomUUID().toString());
        accountRepository.saveAll(List.of(userAccount, connectionAccount));
    }

    @Test
    @WithMockUser(username = "johndoe", password = "passer@123")
    void sendShouldTransferMoneyToConnectionAccount() {
        accountService.send("bobby", BigDecimal.valueOf(10), "Envoi 10€ a Bobby");

        assertThat(transactionRepository.findAll()).isNotEmpty();
        // TODO improve this assertion to make it sonar compliant
        assertThat(accountRepository.findByUsernameOrEmail("johndoe").get().getBalance()).isEqualByComparingTo(BigDecimal.valueOf(90.00));
        assertThat(accountRepository.findByUsernameOrEmail("bobby@test.com").get().getBalance()).isEqualByComparingTo(BigDecimal.valueOf(35.00));
    }

}
