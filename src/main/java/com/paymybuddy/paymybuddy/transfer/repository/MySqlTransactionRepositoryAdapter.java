package com.paymybuddy.paymybuddy.transfer.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MySqlTransactionRepositoryAdapter implements TransactionRepository {

    @Autowired
    private JpaTransactionRepository jpaTransactionRepository;

    @Override
    public void save(TransactionEntity transaction) {
        jpaTransactionRepository.save(transaction);

    }

}
