package com.paymybuddy.paymybuddy.transfer.service;

import com.paymybuddy.paymybuddy.transfer.repository.AccountRepository;
import com.paymybuddy.paymybuddy.transfer.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AccountServiceIntegrationTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;


    @Test
    @WithMockUser(username = "present", password = "passer@123")
    void sendShouldTransferMoneyToConnectionAccount() {
        accountService.send("example", BigDecimal.valueOf(10), "Envoi 10€ a Example");

        assertThat(accountRepository.findByUsernameOrEmail("present").get().getBalance())
                .isEqualByComparingTo(BigDecimal.valueOf(90.00));

        assertThat(accountRepository.findByUsernameOrEmail("example@mail.com").get().getBalance())
                .isEqualByComparingTo(BigDecimal.valueOf(40.00));
    }

}
