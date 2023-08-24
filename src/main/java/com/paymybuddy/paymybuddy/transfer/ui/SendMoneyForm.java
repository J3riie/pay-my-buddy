package com.paymybuddy.paymybuddy.transfer.ui;

import com.paymybuddy.paymybuddy.user.model.User;

import jakarta.validation.constraints.NotNull;

public class SendMoneyForm {

    @NotNull
    private User friend;

    @NotNull
    private int amount;

    public SendMoneyForm() {
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
