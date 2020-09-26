package com.transon.securityDemo.utils;

import java.util.UUID;

public class Utils {
    private Utils() {
        throw new UnsupportedOperationException("Cannot instantiate a Util class");
    }

    public static String generateRandomUuid() {
        return UUID.randomUUID().toString();
    }
}
