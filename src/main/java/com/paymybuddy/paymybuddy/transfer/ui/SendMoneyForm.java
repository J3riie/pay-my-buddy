package com.paymybuddy.paymybuddy.transfer.ui;

import jakarta.validation.constraints.NotNull;

public class SendMoneyForm {

    @NotNull
    private String friend;

    @NotNull
    private int amount;

    public SendMoneyForm() {
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
