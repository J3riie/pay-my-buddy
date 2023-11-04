package com.paymybuddy.paymybuddy.user.model;

import java.util.ArrayList;
import java.util.List;

import com.paymybuddy.paymybuddy.transfer.model.Account;

public class User {

    private final String email;

    private final String password;

    private final String username;

    private final List<String> friends;

    private final Account account;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.username = email;
        this.friends = new ArrayList<>();
        this.account = null;
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

    public Account getAccount() {
        return account;
    }
}
