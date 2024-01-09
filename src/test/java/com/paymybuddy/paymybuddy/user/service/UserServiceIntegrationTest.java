package com.paymybuddy.paymybuddy.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import com.paymybuddy.paymybuddy.exception.FunctionalException;
import com.paymybuddy.paymybuddy.user.repository.UserRepository;

@SpringBootTest
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "robin.hugues@mail.com")
    public void givenValidConnection_whenAddConnection_thenConnectionIsAdded() {
        // Given
        final String connection = "martin";
        // When
        userService.addConnection(connection);
        // Then
        assertThat(userRepository.findByUsernameOrEmail("robinhugues").get().getConnections()).contains("martin");
    }

    @Test
    @WithMockUser(username = "robin.hugues@mail.com")
    public void givenInvalidConnection_whenAddConnection_thenErrorIsThrown() {
        // Given
        final String connection = "unknown";
        // When
        final Throwable throwable = catchThrowable(() -> userService.addConnection(connection));
        // Then
        then(throwable).isInstanceOf(FunctionalException.class);
    }

    @Test
    @WithMockUser(username = "robin.hugues@mail.com")
    public void givenConnectionAlreadyAdded_whenAddConnection_thenErrorIsThrown() {
        // Given
        final String connection = "tony";
        // When
        final Throwable throwable = catchThrowable(() -> userService.addConnection(connection));
        // Then
        then(throwable).isInstanceOf(FunctionalException.class);
    }

    @Test
    @WithMockUser(username = "tony.hawk@mail.com")
    public void givenConnectedUser_whenGetConnection_thenConnectionsAreReturned() {
        // Given When
        final List<String> connections = userService.getConnections();
        // Then
        assertThat(connections).containsExactly("robinhugues");
    }
}
