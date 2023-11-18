package com.paymybuddy.paymybuddy.transfer.ui;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.paymybuddy.paymybuddy.transfer.service.AccountService;

@WebMvcTest(SendMoneyController.class)
public class SendMoneyControllerIntegrationTest {

    @MockBean
    AccountService service;

    @MockBean
    SecurityFilterChain securityFilterChain;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenSendMoneyForm_whenGet_thenTransferViewIsReturned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/transfer").with(csrf())).andExpect(status().isOk())
                .andExpect(view().name("transfer")).andExpect(model().attributeExists("transaction"));
    }

    @Test
    public void givenValidTransactionInfo_whenPost_thenTransferViewIsReturned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/transfer").with(csrf()).param("friend", "my_friend")
                .param("amount", "10")).andExpect(status().isOk()).andExpect(view().name("transfer"));
    }

    @Test
    public void givenInvalidParameterType_whenPost_thenStatusIs400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/transfer").with(csrf()).param("friend", "my_friend")
                .param("amount", "invalid_amount")).andExpect(status().is4xxClientError());
    }

    // TODO gérer l'exception et envoyer un code 400
    // @Test
    // public void givenNonFriendParam_whenPost_thenErrorIsThrown() throws Exception {
    // mockMvc.perform(MockMvcRequestBuilders.post("/transfer").with(csrf()).param("friend", "not_my_friend")
    // .param("amount", "10")).andExpect(status().is4xxClientError());
    // }
}
