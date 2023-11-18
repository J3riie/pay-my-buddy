package com.paymybuddy.paymybuddy.user.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    private List<String> connections;

    public User() {
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.connections = new ArrayList<>();
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

    public void addConnection(String connection) {
        this.connections.add(connection);
    }

}
