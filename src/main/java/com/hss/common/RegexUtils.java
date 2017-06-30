package com.hss.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    public static boolean isNumber(String msg) {
        if (StringUtils.isEmpty(msg)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher matcher = pattern.matcher(msg);
        return matcher.find();
    }

    public static boolean isLong(String msg) {
        if (StringUtils.isEmpty(msg)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^\\d+[Ll]?$");
        Matcher matcher = pattern.matcher(msg);
        return matcher.find();
    }
}
