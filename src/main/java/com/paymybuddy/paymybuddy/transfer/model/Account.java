package com.paymybuddy.paymybuddy.transfer.model;

import java.math.BigDecimal;

public class Account {

    private BigDecimal balance;

    public Account() {
        this.balance = BigDecimal.ZERO;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
