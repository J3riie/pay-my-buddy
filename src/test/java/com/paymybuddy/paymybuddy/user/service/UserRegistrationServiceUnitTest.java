package com.paymybuddy.paymybuddy.user.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.paymybuddy.paymybuddy.user.model.User;
import com.paymybuddy.paymybuddy.user.repository.MySqlUserRepositoryAdapter;
import com.paymybuddy.paymybuddy.user.repository.UserRepository;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class UserRegistrationServiceUnitTest {

    UserRegistrationService service;

    @Mock
    UserRepository userRepository = new MySqlUserRepositoryAdapter();

    @BeforeEach
    public void setup() {
        service = new UserRegistrationService(userRepository);
    }

    @Test
    public void givenNewUserInfo_whenCreateAccount_thenAccountIsSaved() {
        // given
        final String email = "valid@email.com";
        final String password = "avalidpassword";
        when(userRepository.checkIfAccountExists(email)).thenReturn(Optional.empty());
        // when
        service.createAccount(email, password);
        // then
        verify(userRepository, times(1)).saveUser(any());
    }

    @Test
    public void givenExistingUserInfo_whenCreateAccount_thenThrowNewRuntimeException() {
        // given
        final String email = "existing@email.com";
        final String password = "avalidpassword";
        when(userRepository.checkIfAccountExists(email)).thenReturn(Optional.of(new User(email, password)));
        // when then
        assertThrows(RuntimeException.class, () -> { service.createAccount(email, password); });
    }
}
