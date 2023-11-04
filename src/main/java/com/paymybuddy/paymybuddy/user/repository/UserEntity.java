package com.paymybuddy.paymybuddy.user.repository;

import java.util.ArrayList;
import java.util.List;

import com.paymybuddy.paymybuddy.transfer.repository.AccountEntity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String username;

    @ElementCollection
    @CollectionTable(name = "friends", joinColumns = @JoinColumn(name = "user_email"))
    @Column(name = "friends")
    private List<String> friends;

    @OneToOne
    private AccountEntity account;

    public UserEntity() {
    }

    public UserEntity(String email, String password) {
        this.email = email;
        this.password = password;
        this.username = email;
        this.friends = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String nickname) {
        this.username = nickname;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public void addFriend(String friend) {
        this.friends.add(friend);
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }
}
