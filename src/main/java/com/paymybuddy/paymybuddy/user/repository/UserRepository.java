package com.paymybuddy.paymybuddy.user.repository;

import com.paymybuddy.paymybuddy.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM User u WHERE u.email = :email OR u.username = :email")
    Optional<User> findByUsernameOrEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.email = :email OR u.username = :username")
    Optional<User> findByUsernameOrEmail(@Param("email") String email, @Param("username") String username);
}
