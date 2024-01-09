package com.paymybuddy.paymybuddy.transfer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.paymybuddy.paymybuddy.transfer.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Query("SELECT t FROM Transaction t WHERE t.sender = :email OR t.receiver = :email ORDER BY t.date DESC")
    Page<Transaction> findAllAuthenticatedUserTransactions(@Param("email") String email, Pageable pageable);

}
