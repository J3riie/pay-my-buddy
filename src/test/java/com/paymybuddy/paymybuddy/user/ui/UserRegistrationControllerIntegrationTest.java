package com.paymybuddy.paymybuddy.user.ui;

import com.paymybuddy.paymybuddy.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserRegistrationController.class)
public class UserRegistrationControllerIntegrationTest {

    @MockBean
    UserService userService;

    @MockBean
    SecurityFilterChain securityFilterChain;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("show user registration form")
    @Test
    public void givenUserRegistrationForm_whenGet_thenUserRegistrationViewIsReturned() throws Exception {
        mockMvc.perform(get("/register")).andExpect(status().isOk()).andExpect(view().name("user_registration"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    public void givenValidUserInfo_whenPost_thenUserRegistrationSuccessViewIsReturned() throws Exception {
        mockMvc.perform(post("/register")
                .param("email", "johndoe@email.com")
                .param("username", "johndoe")
                .param("password", "passer@123")
                .param("passwordConfirmation", "passer@123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void givenNonMatchingPasswords_whenPost_thenExceptionThrown() throws Exception {
        mockMvc.perform(post("/register")
                        .param("email", "an@email.com")
                        .param("username", "johndoe")
                        .param("password", "apassword")
                        .param("passwordConfirmation", "anotherpassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register"));
                // .andExpect(model().hasErrors()); // TODO avoid redirection if we want to access the model and assert errors effectively occurs
    }
}
