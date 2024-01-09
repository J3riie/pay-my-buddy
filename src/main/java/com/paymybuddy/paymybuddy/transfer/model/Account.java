package com.paymybuddy.paymybuddy.transfer.model;

import static com.paymybuddy.paymybuddy.transfer.model.Transaction.createDepositTransaction;
import static com.paymybuddy.paymybuddy.transfer.model.Transaction.createSendMoneyTransaction;
import static com.paymybuddy.paymybuddy.transfer.model.Transaction.createWithdrawTransaction;

import java.math.BigDecimal;

import com.paymybuddy.paymybuddy.user.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ACCOUNTS")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;

    @OneToOne(mappedBy = "account")
    private User user;

    public Account() {
        this.balance = BigDecimal.ZERO;
    }

    public Account(BigDecimal initialValue) {
        this.balance = initialValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean canSendTo(Account connectionAccount, BigDecimal amount) {
        return hasFriend(connectionAccount.getUser()) && hasEnoughMoney(amount);
    }

    public boolean canWithdraw(BigDecimal amount) {
        return hasEnoughMoney(amount);
    }

    private boolean hasFriend(User connection) {
        return this.getUser().getConnections().contains(connection.getEmail())
                || this.getUser().getConnections().contains(connection.getUsername());
    }

    private boolean hasEnoughMoney(BigDecimal amount) {
        return amount.intValue() <= this.getBalance().intValue();
    }

    public String getUsername() {
        return this.user.getUsername();
    }

    public Transaction sendMoney(Account connectionAccount, BigDecimal amount, String description) {
        this.setBalance(this.balance.subtract(amount));
        connectionAccount.setBalance(connectionAccount.balance.add(amount));
        return createSendMoneyTransaction(amount, connectionAccount.getUsername(), this.getUsername(), description);
    }

    public Transaction deposit(BigDecimal amount, String description) {
        this.setBalance(this.balance.add(amount));
        return createDepositTransaction(amount, this.getUsername(), description);
    }

    public Transaction withdraw(BigDecimal amount, String description) {
        this.setBalance(this.balance.subtract(amount));
        return createWithdrawTransaction(amount, this.getUsername(), description);
    }

    @Override
    public String toString() {
        return String.format("Account[balance: %s]", this.balance);
    }
}
