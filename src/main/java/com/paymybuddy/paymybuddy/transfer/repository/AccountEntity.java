package com.paymybuddy.paymybuddy.transfer.repository;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AccountEntity {

    @Id
    private int accountId;

    @Column(nullable = false)
    private String rib;

    @Column(nullable = false)
    private BigDecimal balance;
}
