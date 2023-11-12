package com.paymybuddy.paymybuddy.user.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection
    @CollectionTable(name = "connections", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "connections")
    private List<String> connections;

    public User() {}

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
