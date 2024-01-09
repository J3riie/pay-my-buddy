package com.paymybuddy.paymybuddy.contact.ui;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(ContactController.class)
public class ContactControllerIntegrationTest {

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "test")
    public void givenContactPageUrl_whenGet_thenContactViewIsReturned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/contact").with(csrf())).andExpect(status().isOk())
                .andExpect(view().name("contact"));
    }
}
