package com.paymybuddy.paymybuddy.entities;

import java.math.BigDecimal;

public class Account {

    private final String email;

    private final int balance;

    public Account(String email, int balance) {
        this.email = email;
        this.balance = balance;
    }

    public Transaction deposit(BigDecimal amount) {
        return null;
    }

    public Transaction payFriend(BigDecimal amount, String email) {
        return null;
    }

    public Transaction withdraw(BigDecimal amount) {
        return null;
    }
}
