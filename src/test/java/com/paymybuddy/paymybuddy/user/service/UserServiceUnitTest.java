package com.paymybuddy.paymybuddy.user.service;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.paymybuddy.paymybuddy.exception.FunctionalException;
import com.paymybuddy.paymybuddy.user.model.User;
import com.paymybuddy.paymybuddy.user.repository.UserRepository;
import com.paymybuddy.paymybuddy.user.ui.UserRegistrationForm;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class UserServiceUnitTest {

    UserService userService;

    // TODO check point
    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder encoder;

    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        encoder = mock(PasswordEncoder.class);
        userService = new UserService(encoder, userRepository);
    }

    @Test
    public void givenNewUserInfo_whenCreateAccount_thenAccountIsSaved() {
        // given
        given(encoder.encode(null)).willReturn("encoded");
        given(userRepository.findByUsernameOrEmail(null, null)).willReturn(Optional.empty());
        // when
        userService.signUp(new UserRegistrationForm());
        // then
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void givenExistingUserInfo_whenCreateAccount_thenThrowNewFunctionalException() {
        // given
        given(userRepository.findByUsernameOrEmail(null, null)).willReturn(Optional.of(new User()));
        // when
        final Throwable thrown = catchThrowable(() -> userService.signUp(new UserRegistrationForm()));
        // then
        then(thrown).isInstanceOf(FunctionalException.class);
    }
}
