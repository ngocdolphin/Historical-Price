package com.tdt.historical_prices.config;

public interface RegexConstants {
    /**
     * (?=.{8,}): Password must contain at least 8 characters
     * (?=.*[0-9]): a digit must occur at least once
     * (?=.*[a-z]): a lower case letter must occur at least once
     * (?=.*[A-Z]): an upper case letter must occur at least once
     * (?=.*[!"#$%&'()*+,\\./:;<>=?\[@\]_`{}|~^-]): a special character must occur at least once
     * List of special characters: !"#$%&'()*+,\./:;<>=?[@]_`{}|~^-
     */
    String PASSWORD_REGEX = "^.*(?=.{8,})(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!\"#$%&'()*+,\\\\./:;<>=?\\[@\\]_`{}|~^-]).*$";
}
