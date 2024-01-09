package com.paymybuddy.paymybuddy.transfer.ui;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public class BankOperationForm {

    @NotNull
    private BankOperationType type;

    @NotNull
    private BigDecimal amount;

    private String description;

    public enum BankOperationType {
        DEPOSIT,
        WITHDRAW
    }

    public BankOperationType getType() {
        return type;
    }

    public void setType(BankOperationType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
