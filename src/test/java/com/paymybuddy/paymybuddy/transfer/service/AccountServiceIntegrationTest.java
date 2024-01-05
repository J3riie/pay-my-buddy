package com.paymybuddy.paymybuddy.transfer.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import com.paymybuddy.paymybuddy.exception.FunctionalException;
import com.paymybuddy.paymybuddy.transfer.repository.AccountRepository;

@SpringBootTest
class AccountServiceIntegrationTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Test
    @WithMockUser(username = "robin.hugues@mail.com")
    public void givenValidConnectionAndAmount_whenSendMoney_thenMoneyIsTransfered() {
        // Given
        final String connectionName = "tony";
        final BigDecimal amount = BigDecimal.valueOf(10);
        // When
        accountService.send(connectionName, amount, "");
        // Then
        assertThat(accountRepository.findByUsernameOrEmail("robinhugues").get().getBalance())
                .isEqualByComparingTo(BigDecimal.valueOf(90.00));
        assertThat(accountRepository.findByUsernameOrEmail("tony").get().getBalance())
                .isEqualByComparingTo(BigDecimal.valueOf(40.00));
    }

    @Test
    @WithMockUser(username = "robin.hugues@mail.com")
    public void givenInvalidAmount_whenSendMoney_thenErrorIsThrown() {
        // Given
        final String connectionName = "tony";
        final BigDecimal amount = BigDecimal.valueOf(1000); // robinhugues doesn't have that much money
        // When
        final Throwable throwable = catchThrowable(() -> accountService.send(connectionName, amount, ""));
        // Then
        then(throwable).isInstanceOf(FunctionalException.class);
    }

    @Test
    @WithMockUser(username = "robin.hugues@mail.com")
    public void givenUnknownConnection_whenSendMoney_thenErrorIsThrown() {
        // Given
        final String connectionName = "who?";
        final BigDecimal amount = BigDecimal.valueOf(10);
        // When
        final Throwable throwable = catchThrowable(() -> accountService.send(connectionName, amount, ""));
        // Then
        then(throwable).isInstanceOf(FunctionalException.class);
    }

    @Test
    @WithMockUser(username = "martin@mail.com")
    public void givenValidAmount_whenDeposit_thenAccountIsCredited() {
        // Given
        final BigDecimal amount = BigDecimal.valueOf(10);
        // When
        accountService.deposit(amount, "");
        // Then
        assertThat(accountRepository.findByUsernameOrEmail("martin").get().getBalance())
                .isEqualByComparingTo(BigDecimal.valueOf(40.00));
    }

    @Test
    @WithMockUser(username = "anothermartin@mail.com")
    public void givenValidAmount_whenWithdraw_thenAccountIsDebited() {
        // Given
        final BigDecimal amount = BigDecimal.valueOf(10);
        // When
        accountService.withdraw(amount, "");
        // Then
        assertThat(accountRepository.findByUsernameOrEmail("martin le second").get().getBalance())
                .isEqualByComparingTo(BigDecimal.valueOf(20.00));
    }

    @Test
    @WithMockUser(username = "robin.hugues@mail.com")
    public void givenInvalidAmount_whenWithdraw_thenErrorIsThrown() {
        // Given
        final BigDecimal amount = BigDecimal.valueOf(1000);
        // When
        final Throwable throwable = catchThrowable(() -> accountService.withdraw(amount, ""));
        // Then
        then(throwable).isInstanceOf(FunctionalException.class);
    }
}
