package com.example.demo.mock.common.util;

import java.util.regex.Pattern;

public class RegexUtil {
    private RegexUtil(){

    }

    private static final String PATTERN_ANS = "^([ANS]|AN|AS|ANS)(|\\.{2})[1-9]\\d*$";

    private static final String PATTERN_D ="^D\\([1-9]\\d*,[1-9]\\d*\\)$";


    public static boolean matchesANS(String s) {
        return Pattern.matches(PATTERN_ANS, s.replaceAll(" ",""));
    }

    public static boolean matchesD(String s) {
        return Pattern.matches( PATTERN_D,s.replace(" ", ""));
    }

}
