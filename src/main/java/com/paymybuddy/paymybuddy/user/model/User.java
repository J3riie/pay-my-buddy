package com.paymybuddy.paymybuddy.user.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private final String email;

    private final String password;

    private final String username;

    private final List<String> friends;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.username = email;
        this.friends = new ArrayList<>();
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

    public List<String> getFriends() {
        return friends;
    }
}
