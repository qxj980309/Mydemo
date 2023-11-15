package com.example.demo.mock.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DateUtil {

    private static final Logger log = LoggerFactory.getLogger(DateUtil.class);

    private static final Set<Character> legalStr = Stream.of('Y','M','D','H','S','W','T','Z','y','m','d'
            ,'h','s','w','z','-','/',':','\'','+').collect(Collectors.toSet());

    private DateUtil(){

    }

    public static boolean isRightPattern(String pattern) {
        String tmp = pattern.replace(" ","");
        for (char c :tmp.toCharArray()){
            if (!legalStr.contains(c)){
                return false;
            }
        }
        try {
            DateTimeFormatter.ofPattern(pattern);
            return true;
        }catch (IllegalArgumentException e){
            log.warn("无效的时间格式：{}",pattern);
            return false;
        }
    }

}
