package com.matthewyaqobi.dodo;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {


    public static String getFormattedTime(int time) {
        int hour = time / 60;
        int minute = time % 60;
        return String.format(Locale.US, "%d:%02d", hour, minute);
    }


    public static String getTodayDate() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE-ddMMM", Locale.ENGLISH);
        return sdf.format(today).toUpperCase();
    }


    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    public static String getFormattedDate(Long millis) {

        LocalDate localDate = Instant.ofEpochMilli(millis)
                .atZone(ZoneOffset.UTC)
                .toLocalDate();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE dd MMM", Locale.US);
        String formatted = localDate.format(formatter);

        return "(" + formatted.substring(0, 3) + ")" +
                formatted.substring(4, 6) + " " +
                formatted.substring(7).toUpperCase();
    }


    public static List<Long> get30Days() {

        List<Long> result = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        for (int i = 0; i < 30; i++) {
            result.add(calendar.getTimeInMillis());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return result;
    }


    public static String getFormattedDate2(Long millis) {

        LocalDate localDate = Instant.ofEpochMilli(millis)
                .atZone(ZoneOffset.UTC)
                .toLocalDate();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEdd.MM", Locale.US);
        String formatted = localDate.format(formatter);

        return formatted.substring(0, 3) + "\n" + formatted.substring(3);
    }


    public static long timestampChanger(long timestamp) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTimeInMillis(timestamp);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }


    public static String getFormattedDate3(Long millis) {

        LocalDate localDate = Instant.ofEpochMilli(millis)
                .atZone(ZoneOffset.UTC)
                .toLocalDate();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM", Locale.US);
        return localDate.format(formatter).toUpperCase();
    }

}
