package com.paymybuddy.paymybuddy.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.paymybuddy.paymybuddy.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM Users u WHERE u.email = :email OR u.username = :email")
    Optional<User> findByUsernameOrEmail(@Param("email") String email);

    @Query("SELECT u FROM Users u WHERE u.email = :email OR u.username = :username")
    Optional<User> findByUsernameOrEmail(@Param("email") String email, @Param("username") String username);
}
