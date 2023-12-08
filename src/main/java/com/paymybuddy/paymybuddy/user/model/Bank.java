package com.paymybuddy.paymybuddy.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "BANK")
public class Bank {

    @Id
    private String rib;

    private String name;

    @ManyToOne
    private User user;

    public Bank(String rib, String name, User user) {
        this.rib = rib;
        this.name = name;
        this.user = user;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
