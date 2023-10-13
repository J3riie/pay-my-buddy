package com.paymybuddy.paymybuddy.user.repository;

import com.paymybuddy.paymybuddy.user.model.User;

public interface UserRepository {

    boolean checkIfAccountExists(String email);

    void saveUser(User user);

    boolean loginUser(String email, String password);

    User findByEmail(String email);

    boolean checkIfEmailIsAFriend(String friendEmail, String authenticatedUserEmail);

    void addConnection(String newConnectionEmail, User authenticatedUser);
}
