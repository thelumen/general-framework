package com.ri.generalFramework.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherUtil {

    public static boolean match(String regEx, String value) {
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
