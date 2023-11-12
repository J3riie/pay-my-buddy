package com.paymybuddy.paymybuddy.transfer.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TRANSACTIONS")
public class Transaction {
    @Id
    private String id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false, length = 125)
    private TransactionType type;

    @Column
    private String receiver;

    @Column
    private String sender;

    @Column(name = "created", nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String description;

    private enum TransactionType {
        DEPOSIT,
        WITHDRAW,
        SEND_MONEY
    }

    public Transaction() {
    }

    private Transaction(BigDecimal amount, TransactionType type, String receiver, String sender,
                              String description) {
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
        this.type = type;
        this.receiver = receiver;
        this.sender = sender;
        this.date = LocalDateTime.now();
        this.description = description;
    }

    public static Transaction createSendMoneyTransaction(BigDecimal amount, String receiver, String sender,
                                                               String description) {
        return new Transaction(amount, TransactionType.SEND_MONEY, receiver, sender, description);
    }
}
