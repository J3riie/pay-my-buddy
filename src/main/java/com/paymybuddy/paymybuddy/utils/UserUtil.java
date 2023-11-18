package com.paymybuddy.paymybuddy.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {

    public UserUtil() {
    }

    public static String getAuthenticatedUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
