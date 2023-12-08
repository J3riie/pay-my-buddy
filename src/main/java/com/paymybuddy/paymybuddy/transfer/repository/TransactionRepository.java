package com.paymybuddy.paymybuddy.transfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.paymybuddy.transfer.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    // @Query("SELECT t FROM Transaction t WHERE t.sender = :email")
    // List<Transaction> findAllAuthenticatedUserTransactions(@Param("email") String email);

}
