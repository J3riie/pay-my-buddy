package com.paymybuddy.paymybuddy.transfer.repository;

import java.math.BigDecimal;

import com.paymybuddy.paymybuddy.transfer.model.Transaction;

public class MySqlTransactionRepositoryAdapter implements TransactionRepository {

    @Override
    public Transaction deposit(BigDecimal amount) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Transaction sendMoney(BigDecimal amount, String email) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Transaction withdraw(BigDecimal amount) {
        // TODO Auto-generated method stub
        return null;
    }

}
