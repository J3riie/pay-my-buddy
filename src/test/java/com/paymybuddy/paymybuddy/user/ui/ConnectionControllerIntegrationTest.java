package com.paymybuddy.paymybuddy.user.ui;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.paymybuddy.paymybuddy.exception.FunctionalException;
import com.paymybuddy.paymybuddy.user.service.UserService;

@WebMvcTest(ConnectionController.class)
public class ConnectionControllerIntegrationTest {

    @MockBean
    private UserService service;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private SecurityFilterChain securityFilterChain;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "robin.hugues@mail.com")
    public void givenExistingConnection_whenGet_thenResponseIsAsExpected() throws Exception {
        // Given
        final List<String> connections = new ArrayList<String>();
        connections.add("tony");
        given(service.getConnections()).willReturn(connections);
        // When
        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.get("/connections").with(csrf()).contentType(MediaType.APPLICATION_JSON_VALUE));
        // Then
        result.andExpect(status().isOk()).andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$").value(hasSize(1)))
                .andExpect(jsonPath("$[0]").value("tony"));
    }

    @Test
    @WithMockUser(username = "robin.hugues@mail.com")
    public void givenValidUser_whenPost_thenResponseStatusIs201() throws Exception {
        // Given
        final String content = "{\"emailOrUsername\":\"martin\"}";
        // When
        final ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/connections").with(csrf())
                .content(content).contentType(MediaType.APPLICATION_JSON));
        // Then
        result.andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "robin.hugues@mail.com")
    public void givenUnknownUser_whenPost_thenResponseStatusIs400() throws Exception {
        // Given
        final String unknown = "unknown";
        doThrow(new FunctionalException(String.format("Error"), HttpStatus.BAD_REQUEST)).when(service)
                .addConnection(unknown);
        final String content = "{\"emailOrUsername\":\"" + unknown + "\"}";
        // When
        final ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/connections").with(csrf())
                .content(content).contentType(MediaType.APPLICATION_JSON));
        // Then
        result.andExpect(status().is4xxClientError());
    }
}
