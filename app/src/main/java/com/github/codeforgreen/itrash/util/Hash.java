package com.github.codeforgreen.itrash.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Hash {

    public static String hash(String string) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(string.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return string;
    }
}
