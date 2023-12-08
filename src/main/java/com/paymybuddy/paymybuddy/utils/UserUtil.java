package com.paymybuddy.paymybuddy.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class UserUtil {

    private static final MainLogger logger = MainLogger.getLogger(UserUtil.class);

    public UserUtil() {
    }

    public static String getAuthenticatedUserEmail() {
        try {
            final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            logger.info("Authenticated user found: {0}", user.getUsername());
            return user.getUsername();
        } catch (final Exception e) {
            logger.error("No user is connected, pricipal is anonymous");
            return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
    }
}
