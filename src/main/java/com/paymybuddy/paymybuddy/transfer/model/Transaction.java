package com.paymybuddy.paymybuddy.transfer.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TRANSACTIONS")
public class Transaction {

    @Id
    private String id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType type;

    @Column(nullable = false)
    private String receiver;

    @Column(nullable = false)
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

    private Transaction(BigDecimal amount, TransactionType type, String receiver, String sender, String description) {
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

    public static Transaction createDepositTransaction(BigDecimal amount, String receiverSender, String description) {
        return new Transaction(amount, TransactionType.DEPOSIT, receiverSender, receiverSender, description);
    }

    public static Transaction createWithdrawTransaction(BigDecimal amount, String receiverSender, String description) {
        return new Transaction(amount, TransactionType.WITHDRAW, receiverSender, receiverSender, description);
    }
}
