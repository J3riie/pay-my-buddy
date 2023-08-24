package com.paymybuddy.paymybuddy.user.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private final String email;

    private final String password;

    private final String nickname;

    private final List<String> friends;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.nickname = email;
        this.friends = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public List<String> getFriends() {
        return friends;
    }
}
