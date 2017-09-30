package br.com.bruno.meumetro.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Bruno on 11/02/2017.
 */

public class DateUtils {

    public static int getYear(Date date){
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy", Locale.US);
            return Integer.parseInt(format.format(date));
        }
        return 0;
    }

    public static int getMonth(Date date){
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat("MM", Locale.US);
            return Integer.parseInt(format.format(date));
        }
        return 0;
    }

    public static int getDay(Date date){
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat("dd", Locale.US);
            return Integer.parseInt(format.format(date));
        }
        return 0;
    }

    public static int getHours(Date date){
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat("HH", Locale.US);
            return Integer.parseInt(format.format(date));
        }
        return 0;
    }

    public static int getMinutes(Date date){
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat("mm", Locale.US);
            return Integer.parseInt(format.format(date));
        }
        return 0;
    }

    public static int getSeconds(Date date){
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat("ss", Locale.US);
            return Integer.parseInt(format.format(date));
        }
        return 0;
    }
}
