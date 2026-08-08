package com.ecommerce.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class OrderNumberGenerator {

    private OrderNumberGenerator() {
    }

    public static String generate() {

        String date =
                LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String random =
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();

        return "LM-" + date + "-" + random;

    }

}