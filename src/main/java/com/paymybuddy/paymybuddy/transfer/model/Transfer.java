package com.paymybuddy.paymybuddy.transfer.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class Transfer {

    private UUID transactionId;

    private BigDecimal amount;

    public enum type {
        DEPOSIT,
        PAY_FRIEND,
        WITHDRAW
    }

    private String connection;

    private Date date;

    public Transfer(BigDecimal amount, type type, String connection) {
        this.transactionId = UUID.randomUUID();
        this.amount = amount;
        this.connection = connection;
        this.date = new Date();
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "";
    }

}
