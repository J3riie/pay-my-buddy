package com.paymybuddy.paymybuddy.transfer.repository;

import com.paymybuddy.paymybuddy.transfer.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.user.email = :email OR a.user.username = :username")
    Optional<Account> findByUsernameOrEmail(@Param("email") String email);
}
