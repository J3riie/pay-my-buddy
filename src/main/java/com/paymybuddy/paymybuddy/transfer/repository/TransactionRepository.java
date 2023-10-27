package com.paymybuddy.paymybuddy.transfer.repository;

import java.math.BigDecimal;

import com.paymybuddy.paymybuddy.transfer.model.Transaction;

public interface TransactionRepository {
    Transaction deposit(BigDecimal amount);

    Transaction sendMoney(BigDecimal amount, String email);

    Transaction withdraw(BigDecimal amount);
}
