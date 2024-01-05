package com.paymybuddy.paymybuddy.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.paymybuddy.paymybuddy.transfer.model.Account;
import com.paymybuddy.paymybuddy.user.model.User;
import com.paymybuddy.paymybuddy.user.repository.UserRepository;
import com.paymybuddy.paymybuddy.user.service.UserService;
import com.paymybuddy.paymybuddy.user.ui.UserRegistrationForm;

@SpringBootTest
class UserRegisterIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void registerUser() {
        final UserRegistrationForm registerForm = new UserRegistrationForm();
        registerForm.setUsername("omar");
        registerForm.setEmail("omar@local.test");
        registerForm.setPassword("passer@123");
        registerForm.setPasswordConfirmation("passer@123");
        userService.signUp(registerForm);

        final Optional<User> optionalUser = userRepository.findByUsernameOrEmail("omar");

        final Account account = optionalUser.get().getAccount();
        System.out.println("## Account " + account);

        assertThat(optionalUser).isPresent();
        assertThat(optionalUser.get().getAccount()).isNotNull();
        assertThat(optionalUser.get().getAccount().getBalance()).isEqualByComparingTo(BigDecimal.ZERO);
    }
}
