package com.paymybuddy.paymybuddy.transfer.repository;

import com.paymybuddy.paymybuddy.transfer.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {


}
