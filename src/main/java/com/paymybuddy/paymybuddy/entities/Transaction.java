package com.paymybuddy.paymybuddy.entities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class Transaction {
    private final UUID transactionId;

    private final BigDecimal amount;

    public enum type {
        DEPOSIT,
        PAY_FRIEND,
        WITHDRAW
    }

    private final String connection;

    private final Date date;

    public Transaction(BigDecimal amount, type type, String connection) {
        this.transactionId = UUID.randomUUID();
        this.amount = amount;
        this.connection = connection;
        this.date = new Date();
    }

    @Override
    public String toString() {
        return "";
    }

}
