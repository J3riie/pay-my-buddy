package com.paymybuddy.paymybuddy.transfer.model;

import com.paymybuddy.paymybuddy.user.model.User;
import jakarta.persistence.*;

import java.math.BigDecimal;

import static com.paymybuddy.paymybuddy.transfer.model.Transaction.*;

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

    public boolean canSendTo(Account connectionAccount) {
        return this.getUser().getConnections().contains(connectionAccount.getUser().getEmail());

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
