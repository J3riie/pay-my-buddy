package com.paymybuddy.paymybuddy.transfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTransactionRepository extends JpaRepository<TransactionEntity, String> {

}