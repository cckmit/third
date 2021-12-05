package com.beitie.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/11/25
 */
public class DateFormatUtils {
    static SimpleDateFormat format = new SimpleDateFormat();

    public static String format(Date date,String formatStr) {
        format.applyPattern(formatStr);
        return format.format(date);
    }
}
