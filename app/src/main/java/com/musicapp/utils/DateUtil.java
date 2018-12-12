package com.musicapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {
    public static String convertToLocal(String from){
        SimpleDateFormat fromFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat toFormate = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return toFormate.format(fromFormater.parse(from));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return from;
    }
}
