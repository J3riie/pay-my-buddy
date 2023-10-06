package com.paymybuddy.paymybuddy.transfer.ui;

import com.paymybuddy.paymybuddy.exception.FunctionalException;
import com.paymybuddy.paymybuddy.transfer.service.ConnectionService;
import com.paymybuddy.paymybuddy.utils.MainLogger;

public class ConnectionController {

    private static final MainLogger logger = MainLogger.getLogger(ConnectionController.class);

    private final ConnectionService connectionService;

    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    public void addConnection(String email) {
        logger.info("Adding connection");
        try {
            connectionService.addConnection(email);
        } catch (final FunctionalException e) {

        }
    }
}
