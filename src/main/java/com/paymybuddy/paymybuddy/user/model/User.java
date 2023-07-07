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

    public void connection(String email, String password) {

    }

    public void disconnection() {

    }

    public void addFriend(String email) {

    }

    public void deleteFriend(String email) {

    }

    public void addBankAccount(String IBAN) {

    }

    public void deleteBankAccount(String IBAN) {

    }
}
