package com.paymybuddy.paymybuddy.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.paymybuddy.paymybuddy.user.model.User;

@Repository
public class MySqlUserRepositoryAdapter implements UserRepository {

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Override
    public boolean checkIfAccountExists(String email) {
        return jpaUserRepository.findById(email).isPresent();
    }

    @Override
    public void saveUser(User user) {
        final UserEntity userEntity = new UserEntity(user.getEmail(), user.getPassword());
        jpaUserRepository.save(userEntity);
    }

    @Override
    public boolean loginUser(String email, String password) {
        return jpaUserRepository.getReferenceById(email).getPassword().equals(password);
    }

    @Override
    public User findByEmail(String email) {
        final Optional<UserEntity> optionalUserEntity = jpaUserRepository.findById(email);
        if (optionalUserEntity.isEmpty()) {
            throw new RuntimeException();
        }
        return new User(optionalUserEntity.get().getEmail(), optionalUserEntity.get().getPassword());
    }

    @Override
    public boolean checkIfEmailIsAFriend(String friendEmail, String authenticatedUserEmail) {
        return jpaUserRepository.getReferenceById(authenticatedUserEmail).getFriends().contains(friendEmail);
    }

    @Override
    public void addConnection(User newConnectionUser, User authenticatedUser) {
        final UserEntity userEntity = jpaUserRepository.getReferenceById(authenticatedUser.getEmail());
        final UserEntity connectionUserEntity = jpaUserRepository.getReferenceById(newConnectionUser.getEmail());
        userEntity.addFriend(connectionUserEntity.getUsername());
        jpaUserRepository.save(userEntity);
    }

    @Override
    public List<String> getFriends(User authenticatedUser) {
        final UserEntity userEntity = jpaUserRepository.getReferenceById(authenticatedUser.getEmail());
        return userEntity.getFriends();
    }

    @Override
    public User findByUsername(String friend) {
        // TODO Auto-generated method stub
        return null;
    }

}
