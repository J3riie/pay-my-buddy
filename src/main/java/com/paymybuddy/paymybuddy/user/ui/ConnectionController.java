package com.paymybuddy.paymybuddy.user.ui;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.paymybuddy.exception.FunctionalException;
import com.paymybuddy.paymybuddy.user.service.ConnectionService;
import com.paymybuddy.paymybuddy.utils.MainLogger;

@RestController
@RequestMapping(value = "/connections", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ConnectionController {

    private static final MainLogger logger = MainLogger.getLogger(ConnectionController.class);

    private final ConnectionService connectionService;

    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @GetMapping
    public List<String> getConnections() {
        return connectionService.getConnections();
    }

    @PostMapping
    public ResponseEntity<AddConnectionResponse> add(@RequestBody AddConnectionForm connectionForm) {
        try {
            logger.info("Trying to add add connection {0}", connectionForm);
            connectionService.addConnection(connectionForm.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new AddConnectionResponse(HttpStatus.CREATED.value(), "Connection created"));
        } catch (final FunctionalException e) {
            logger.error("Connection email is invalid.", e);
            return ResponseEntity.status(e.getStatus())
                    .body(new AddConnectionResponse(e.getStatus().value(), e.getMessage()));
        }
    }
}
