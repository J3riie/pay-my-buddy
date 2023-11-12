package com.paymybuddy.paymybuddy.transfer.model;

import com.paymybuddy.paymybuddy.user.model.User;
import jakarta.persistence.*;

import java.math.BigDecimal;

import static com.paymybuddy.paymybuddy.transfer.model.Transaction.createSendMoneyTransaction;

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

    public Transaction makeTransaction(Account connectionAccount, BigDecimal amount, String description) {
        this.setBalance(this.balance.subtract(amount));
        connectionAccount.setBalance(connectionAccount.balance.add(amount));
        return createSendMoneyTransaction(amount, connectionAccount.getUsername(), this.getUsername(), description);
    }

    public String getUsername() {
        return this.user.getUsername();
    }
}
