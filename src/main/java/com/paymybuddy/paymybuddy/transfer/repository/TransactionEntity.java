package com.paymybuddy.paymybuddy.transfer.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    private String idTransaction;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private transactionType type;

    // @Column(nullable = true)
    // private String receiver;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String description;

    private enum transactionType {
        DEPOSIT,
        WITHDRAW,
        SEND_MONEY
    }

    public TransactionEntity() {
    }

    /**
     * Constructor used for transaction of type send money to a connection
     */
    public TransactionEntity(String idTransaction, int amount, String receiver,
            String sender, String date, String description) {
        this.idTransaction = idTransaction;
        this.amount = amount;
        this.type = transactionType.SEND_MONEY;
        // this.receiver = receiver;
        this.sender = sender;
        this.date = date;
        this.description = description;
    }

    /**
     * Constructor used for transaction of type withdraw or deposit from/on the user's account
     */
    public TransactionEntity(String idTransaction, int amount,
            transactionType type, String sender, String date,
            String description) {
        this.idTransaction = idTransaction;
        this.amount = amount;
        this.type = type;
        this.sender = sender;
        this.date = date;
        this.description = description;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public transactionType getType() {
        return type;
    }

    public void setType(transactionType type) {
        this.type = type;
    }

    // public String getReceiver() {
    // return receiver;
    // }
    //
    // public void setReceiver(String receiver) {
    // this.receiver = receiver;
    // }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
