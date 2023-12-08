package com.paymybuddy.paymybuddy.user.model;

import com.paymybuddy.paymybuddy.transfer.model.Account;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection
    @CollectionTable(name = "connections", joinColumns = @JoinColumn(name = "email"))
    @Column(name = "connection")
    private final List<String> connections;

    @OneToMany
    private List<Bank> banks;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    public User() {
        this.account = new Account();
        this.connections = new ArrayList<>();
    }

    public User(String email, String username, String password) {
        this();
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getConnections() {
        return connections;
    }

    public void addConnection(String connectionEmail) {
        this.connections.add(connectionEmail);
    }

    public List<Bank> getBanks() {
        return banks;
    }

    public void setBanks(List<Bank> banks) {
        this.banks = banks;
    }

    public void addBank(String rib, String name) {
        this.banks.add(new Bank(rib, name, this));
    }

    public void removeBank(Bank bank) {
        this.banks.remove(bank);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
