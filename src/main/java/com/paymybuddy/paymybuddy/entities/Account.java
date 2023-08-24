package com.paymybuddy.paymybuddy.entities;

import java.math.BigDecimal;

import com.paymybuddy.paymybuddy.transfer.model.Transfer;

public class Account {

    private final String email;

    private final int balance;

    public Account(String email, int balance) {
        this.email = email;
        this.balance = balance;
    }

    public Transfer deposit(BigDecimal amount) {
        return null;
    }

    public Transfer payFriend(BigDecimal amount, String email) {
        return null;
    }

    public Transfer withdraw(BigDecimal amount) {
        return null;
    }
}
