package com.example.sara.billards;

import java.util.Date;
import java.text.SimpleDateFormat;

public class DateUtils {
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public static String formatDate(Date date) {
        return df.format(date);
    }
}