package com.paymybuddy.paymybuddy.transfer.ui;

import com.paymybuddy.paymybuddy.exception.FunctionalException;
import com.paymybuddy.paymybuddy.transfer.service.AddConnectionService;
import com.paymybuddy.paymybuddy.utils.MainLogger;

public class AddConnectionController {

    private static final MainLogger logger = MainLogger.getLogger(AddConnectionController.class);

    private final AddConnectionService addConnectionService;

    public AddConnectionController(AddConnectionService addConnectionService) {
        this.addConnectionService = addConnectionService;
    }

    public void addConnection(String email) {
        logger.info("Adding connection");
        try {
            addConnectionService.addConnection(email);
        } catch (final FunctionalException e) {

        }
    }
}
