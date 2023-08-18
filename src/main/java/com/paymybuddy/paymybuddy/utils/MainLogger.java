package com.paymybuddy.paymybuddy.utils;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainLogger {
    private final Logger logger;

    private MainLogger(Logger logger) {
        this.logger = logger;
    }

    public static MainLogger getLogger(Class<?> clazz) {
        return new MainLogger(LoggerFactory.getLogger(clazz));
    }

    public void info(String message, Object... params) {
        final String sanitizedMessage = sanitize(MessageFormat.format(message, params));
        logger.info(sanitizedMessage);
    }

    public void debug(String message, Object... params) {
        final String sanitizedMessage = sanitize(MessageFormat.format(message, params));
        logger.debug(sanitizedMessage);
    }

    public void error(String message, Object... params) {
        final String sanitizedMessage = sanitize(MessageFormat.format(message, params));
        logger.error(sanitizedMessage);
    }

    private String sanitize(String msg) {
        final String sanitized = replaceCRLFWithUnderscore(msg);
        return escapeNLFChars(sanitized);
    }

    // Replace any carriage returns (CR) and line feeds (LF) with empty string to prevent log injection attacks.
    private String replaceCRLFWithUnderscore(String value) {
        return value.replace('\n', '_').replace('\r', '_');
    }

    // Escape any new line function (NLF) and backspace to prevent log injection attacks.
    private String escapeNLFChars(String value) {
        return value.replace("\n", "\\n").replace("\r", "\\r").replace("\u0085", "\\u0085").replace("\u000B", "\\u000B").replace("\u000C", "\\u000C")
                .replace("\u2028", "\\u2028").replace("\u2029", "\\u2029").replace("\b", "\\b");
    }
}
