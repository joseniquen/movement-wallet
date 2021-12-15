package com.example.walletmovementmicroservice.utils;

import java.util.regex.Pattern;

public class EmailValidation {

    public static String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    public static boolean patternMatches(String emailAddress) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
}
