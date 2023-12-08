package com.paymybuddy.paymybuddy.transfer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.paymybuddy.paymybuddy.transfer.model.PayMyBuddyAccount;

public interface AccountRepository extends JpaRepository<PayMyBuddyAccount, Long> {

    @Query("SELECT a FROM PayMyBuddyAccount a WHERE a.user.email = :usernameOrEmail OR a.user.username = :usernameOrEmail")
    Optional<PayMyBuddyAccount> findByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail);
}
