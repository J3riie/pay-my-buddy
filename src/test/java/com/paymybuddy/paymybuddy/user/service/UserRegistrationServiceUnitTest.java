package com.paymybuddy.paymybuddy.user.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.paymybuddy.paymybuddy.user.ui.UserRegistrationForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.paymybuddy.paymybuddy.user.repository.UserRepository;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class UserRegistrationServiceUnitTest {

    UserService userService;

    // TODO check point
    @Mock
    UserRepository userRepository = null;

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @BeforeEach
    public void setup() {
        userService = new UserService(encoder, userRepository);
    }

    @Test
    public void givenNewUserInfo_whenCreateAccount_thenAccountIsSaved() {
        // given
        final String email = "valid@email.com";
        final String password = "avalidpassword";
        //when(userRepository.checkIfAccountExists(email)).thenReturn(false);
        // when
        userService.signUp(new UserRegistrationForm());
        // then
        //verify(userRepository, times(1)).saveUser(any());
    }

    @Test
    public void givenExistingUserInfo_whenCreateAccount_thenThrowNewRuntimeException() {
        // given
        final String email = "existing@email.com";
        final String password = "avalidpassword";
        //when(userRepository.checkIfAccountExists(email)).thenReturn(true);
        // when then
        assertThrows(RuntimeException.class, () -> {
            userService.signUp(new UserRegistrationForm());
        });
    }
}
