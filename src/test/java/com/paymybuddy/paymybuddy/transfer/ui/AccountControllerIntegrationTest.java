package com.paymybuddy.paymybuddy.transfer.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.paymybuddy.exception.FunctionalException;
import com.paymybuddy.paymybuddy.transfer.service.AccountService;

@WebMvcTest(AccountController.class)
public class AccountControllerIntegrationTest {

    @MockBean
    private AccountService service;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private SecurityFilterChain securityFilterChain;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @WithMockUser(username = "robin.hugues@mail.com")
    public void givenSendMoneyForm_whenGet_thenTransferViewIsReturned() throws Exception {
        // Given
        given(service.findAllTransactions(any(Pageable.class))).willReturn(new PageImpl<>(new ArrayList<>()));
        // When
        final ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/transfer").with(csrf()));
        // Then
        result.andExpect(status().isOk()).andExpect(view().name("transfer"))
                .andExpect(model().attributeExists("transaction"));
    }

    @Test
    @WithMockUser(username = "robin.hugues@mail.com")
    public void givenValidTransactionInfo_whenPost_thenResponseStatusIs201() throws Exception {
        // Given
        given(service.findAllTransactions(any(Pageable.class))).willReturn(new PageImpl<>(new ArrayList<>()));
        final SendMoneyForm sendForm = new SendMoneyForm();
        sendForm.setFriend("tony");
        sendForm.setAmount(new BigDecimal(10));
        sendForm.setDescription("send money to tony");
        final String payload = mapper.writeValueAsString(sendForm);
        // When
        final ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/transfer").with(csrf())
                .content(payload).contentType(MediaType.APPLICATION_JSON));
        // Then
        result.andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "robin.hugues@mail.com")
    public void givenInvalidParameterType_whenPost_thenResponseStatusIs400() throws Exception {
        // Given
        final String content = "{\"friend\":\"tony\",\"amount\":\"an amount\",\"description\":\"\"}";
        // When
        final ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/transfer").with(csrf())
                .content(content).contentType(MediaType.APPLICATION_JSON));
        // Then
        result.andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "robin.hugues@mail.com")
    public void givenNonFriendParam_whenPost_thenResponseStatusIs400() throws Exception {
        // Given
        final String notAFriend = "unknown";
        final BigDecimal amount = new BigDecimal(10);
        final String description = "Sending money to someone I dont know";
        doThrow(new FunctionalException("Error", HttpStatus.BAD_REQUEST)).when(service).send(notAFriend, amount,
                description);
        final SendMoneyForm sendForm = new SendMoneyForm();
        sendForm.setFriend(notAFriend);
        sendForm.setAmount(amount);
        sendForm.setDescription(description);
        final String payload = mapper.writeValueAsString(sendForm);
        // When
        final ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/transfer").with(csrf())
                .content(payload).contentType(MediaType.APPLICATION_JSON));
        // Then
        result.andExpect(status().is4xxClientError());
    }
}
