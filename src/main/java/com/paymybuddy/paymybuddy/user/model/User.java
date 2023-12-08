package com.paymybuddy.paymybuddy.user.model;

import java.util.ArrayList;
import java.util.List;

import com.paymybuddy.paymybuddy.transfer.model.PayMyBuddyAccount;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

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
    @Column(nullable = true)
    private List<Bank> banks;

    @OneToOne
    private PayMyBuddyAccount account;

    public User() {
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

    public PayMyBuddyAccount getAccount() {
        return account;
    }

    public void setAccount(PayMyBuddyAccount account) {
        this.account = account;
    }

}
