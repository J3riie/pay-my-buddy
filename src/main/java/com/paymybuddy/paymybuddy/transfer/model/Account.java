package com.paymybuddy.paymybuddy.transfer.model;

import static com.paymybuddy.paymybuddy.transfer.model.Transaction.createDepositTransaction;
import static com.paymybuddy.paymybuddy.transfer.model.Transaction.createSendMoneyTransaction;
import static com.paymybuddy.paymybuddy.transfer.model.Transaction.createWithdrawTransaction;

import java.math.BigDecimal;

import com.paymybuddy.paymybuddy.user.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ACCOUNTS")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;

    @Column(nullable = false)
    private String rib;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Account() {
        this.balance = BigDecimal.ZERO;
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

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
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
}
