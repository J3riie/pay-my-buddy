package com.paymybuddy.paymybuddy.user.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/connections", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ConnectionController {

    private static final Logger log = LoggerFactory.getLogger(ConnectionController.class);

    @PostMapping
    public ResponseEntity<AddConnectionResponse> add(@RequestBody AddConnectionForm connectionForm) {
        log.info("add connection {}", connectionForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AddConnectionResponse(HttpStatus.CREATED.value(), "Created"));
    }
}
