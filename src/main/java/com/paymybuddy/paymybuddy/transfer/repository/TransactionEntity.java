package com.paymybuddy.paymybuddy.transfer.repository;

import com.paymybuddy.paymybuddy.user.repository.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TransactionEntity {

    @Id
    private String idTransaction;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private transactionType type;

    // @Column(nullable = true)
    // private UserEntity connection;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String date;

    @Column(nullable = true)
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
    public TransactionEntity(String idTransaction, int amount, UserEntity connection, String email, String date, String description) {
        this.idTransaction = idTransaction;
        this.amount = amount;
        this.type = transactionType.SEND_MONEY;
        // this.connection = connection;
        this.email = email;
        this.date = date;
        this.description = description;
    }

    /**
     * Constructor used for transaction of type send money to a connection without description
     */
    public TransactionEntity(String idTransaction, int amount, UserEntity connection, String email, String date) {
        this.idTransaction = idTransaction;
        this.amount = amount;
        this.type = transactionType.SEND_MONEY;
        // this.connection = connection;
        this.email = email;
        this.date = date;
    }

    /**
     * Constructor used for transaction of type withdraw or deposit from/on the user's account
     */
    public TransactionEntity(String idTransaction, int amount, transactionType type, String email, String date, String description) {
        this.idTransaction = idTransaction;
        this.amount = amount;
        this.type = type;
        this.email = email;
        this.date = date;
        this.description = description;
    }

    /**
     * Constructor used for transaction of type withdraw or deposit from/on the user's account without description
     */
    public TransactionEntity(String idTransaction, int amount, transactionType type, String email, String date) {
        this.idTransaction = idTransaction;
        this.amount = amount;
        this.type = type;
        this.email = email;
        this.date = date;
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

    // public UserEntity getConnection() {
    // return connection;
    // }
    //
    // public void setConnection(UserEntity connection) {
    // this.connection = connection;
    // }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
