package com.paymybuddy.paymybuddy.user.ui;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.paymybuddy.paymybuddy.user.service.UserRegistrationService;

import jakarta.servlet.ServletException;

@WebMvcTest(UserRegistrationController.class)
public class UserRegistrationControllerIntegrationTest {

    @MockBean
    UserRegistrationService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenUserRegistrationForm_whenGet_thenUserRegistrationViewIsReturned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user")).andExpect(status().isOk()).andExpect(view().name("user_registration"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    public void givenValidUserInfo_whenPost_thenUserRegistrationSuccessViewIsReturned() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/user").param("email", "an@email.com").param("password", "apassword").param("passwordConfirmation", "apassword"))
                .andExpect(status().isOk()).andExpect(view().name("user_registration_success")).andExpect(model().attributeExists("userEmail"));
    }

    @Test
    public void givenNonMatchingPasswords_whenPost_thenExceptionThrown() throws Exception {
        assertThrows(ServletException.class, () -> {
            mockMvc.perform(MockMvcRequestBuilders.post("/user").param("email", "an@email.com").param("password", "apassword").param("passwordConfirmation",
                    "anotherpassword"));
        });
    }
}
