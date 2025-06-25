package com.sudeeppadalkar.journalapp.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class StringUtils {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String getEncodedString(String string) {
        return passwordEncoder.encode(string);
    }

}
