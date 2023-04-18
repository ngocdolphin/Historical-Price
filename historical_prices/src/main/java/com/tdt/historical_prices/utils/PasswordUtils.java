package com.tdt.historical_prices.utils;


import com.tdt.historical_prices.config.RegexConstants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PasswordUtils {
    public static final Pattern VALID_PASSWORD_REGEX = Pattern.compile(RegexConstants.PASSWORD_REGEX);

    public static boolean isPassword(final String Str) {
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(Str);
        return matcher.find();
    }
}
