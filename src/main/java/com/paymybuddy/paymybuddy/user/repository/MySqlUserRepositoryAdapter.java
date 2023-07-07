package com.paymybuddy.paymybuddy.user.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.paymybuddy.paymybuddy.user.model.User;

@Repository
public class MySqlUserRepositoryAdapter implements UserRepository {

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Override
    public Optional<User> checkIfAccountExists(String email) {
        final Optional<UserEntity> optionalUser = jpaUserRepository.findById(email);
        if (optionalUser.isPresent()) {
            final UserEntity userEntity = optionalUser.get();
            final User user = new User(userEntity.getEmail(), userEntity.getNickname());
            return Optional.of(user);
        }
        return Optional.empty();
    }

    @Override
    public void saveUser(User user) {
        // TODO Auto-generated method stub

    }

}
