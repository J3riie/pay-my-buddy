package com.paymybuddy.paymybuddy.transfer.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import com.paymybuddy.paymybuddy.transfer.model.PayMyBuddyAccount;
import com.paymybuddy.paymybuddy.transfer.repository.AccountRepository;
import com.paymybuddy.paymybuddy.transfer.repository.TransactionRepository;
import com.paymybuddy.paymybuddy.user.model.User;
import com.paymybuddy.paymybuddy.user.repository.UserRepository;

@SpringBootTest
class AccountServiceIntegrationTest {

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
        final User user = new User("johndoe@test.com", "johndoe", "passer@123");
        final User bobby = new User("bobby@test.com", "bobby", "passer@123");
        user.addConnection(bobby.getEmail());
        final PayMyBuddyAccount userAccount = new PayMyBuddyAccount();
        userAccount.setBalance(BigDecimal.valueOf(100.00));
        user.setAccount(userAccount);

        bobby.addConnection(user.getEmail());
        final PayMyBuddyAccount bobbyAccount = new PayMyBuddyAccount();
        bobbyAccount.setBalance(BigDecimal.valueOf(25.00));
        bobby.setAccount(bobbyAccount);
        accountRepository.saveAll(List.of(userAccount, bobbyAccount));
        userRepository.saveAll(List.of(user, bobby));
    }

    @Test
    @WithMockUser(username = "johndoe", password = "passer@123")
    void sendShouldTransferMoneyToConnectionAccount() {
        accountService.send("bobby", BigDecimal.valueOf(10), "Envoi 10€ a Bobby");

        assertThat(transactionRepository.findAll()).isNotEmpty();
        // TODO improve this assertion to make it sonar compliant
        assertThat(accountRepository.findByUsernameOrEmail("johndoe").get().getBalance())
                .isEqualByComparingTo(BigDecimal.valueOf(90.00));
        assertThat(accountRepository.findByUsernameOrEmail("bobby@test.com").get().getBalance())
                .isEqualByComparingTo(BigDecimal.valueOf(35.00));
    }

}
