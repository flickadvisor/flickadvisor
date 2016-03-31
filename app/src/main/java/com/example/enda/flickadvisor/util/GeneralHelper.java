package com.example.enda.flickadvisor.util;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by enda on 22/03/16.
 */
public class GeneralHelper {

    // http://stackoverflow.com/a/6982154/2324937
    // getting the date format used in the current device
    public static String dateToSystemFormat(Context context, Date date) {
        // get the format, e.g dd/MM/yyyy
        final String format = Settings.System.getString(context.getContentResolver(), Settings.System.DATE_FORMAT);
        DateFormat dateFormat;
        if (TextUtils.isEmpty(format)) {
            dateFormat = android.text.format.DateFormat.getMediumDateFormat(context);
        } else {
            dateFormat = new SimpleDateFormat(format);
        }
        return dateFormat.format(date);
    }
}
