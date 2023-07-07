package src.main.java.com.paymybuddy.paymybuddy.entities;

import java.math.BigDecimal;

import src.main.java.com.paymybuddy.paymybuddy.entities.Transaction.type;

public class Account {

    private final String email;

    private final int balance;

    public Account(String email, int balance) {
        this.email = email;
        this.balance = balance;
    }

    public Transaction deposit(BigDecimal amount) {
        return new Transaction(amount, type.DEPOSIT, "");
    }

    public Transaction payFriend(BigDecimal amount, String email) {
        return new Transaction(amount, type.PAY_FRIEND, "");
    }

    public Transaction withdraw(BigDecimal amount) {
        return new Transaction(amount, type.WITHDRAW, "");
    }
}
