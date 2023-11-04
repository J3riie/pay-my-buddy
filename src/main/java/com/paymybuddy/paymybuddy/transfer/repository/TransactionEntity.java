package com.paymybuddy.paymybuddy.transfer.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    private String idTransaction;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = true)
    private String receiver;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String description;

    private enum TransactionType {
        DEPOSIT,
        WITHDRAW,
        SEND_MONEY
    }

    public TransactionEntity() {
    }

    private TransactionEntity(BigDecimal amount, TransactionType type, String receiver, String sender,
            String description) {
        this.idTransaction = UUID.randomUUID().toString();
        this.amount = amount;
        this.type = type;
        this.receiver = receiver;
        this.sender = sender;
        this.date = LocalDateTime.now();
        this.description = description;
    }

    public static TransactionEntity createSendMoneyTransaction(BigDecimal amount, String receiver, String sender,
            String description) {
        return new TransactionEntity(amount, TransactionType.SEND_MONEY, receiver, sender, description);
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public String getDate() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(date);
    }

    public String getDescription() {
        return description;
    }
}
