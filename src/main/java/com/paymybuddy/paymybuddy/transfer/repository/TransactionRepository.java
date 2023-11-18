package com.paymybuddy.paymybuddy.transfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.paymybuddy.transfer.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

}
