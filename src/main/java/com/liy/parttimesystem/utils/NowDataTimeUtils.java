package com.liy.parttimesystem.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * NowDataTimeConstans$
 *
 * @author liy
 * @date 2024/3/20$
 */
public class NowDataTimeUtils {
    public static String getNowTime(){
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

}
