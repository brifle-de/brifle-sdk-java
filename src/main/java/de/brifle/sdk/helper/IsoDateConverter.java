package de.brifle.sdk.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class IsoDateConverter{

    private static DateFormat df;
    private static DateFormat dfShort;

    static {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        dfShort = new SimpleDateFormat("yyyy-MM-dd");
        df.setTimeZone(tz);
        dfShort.setTimeZone(tz);
    }

    /**
     * Converts a date to a string in the format yyyy-MM-dd'T'HH:mm'Z'
     * @param date
     * @return
     */
    public static String toIsoString(Date date){
        return df.format(date);
    }

    /**
     * Converts a date to a string in the format yyyy-MM-dd
     * @param date
     * @return
     */
    public static String toIsoStringShort(Date date){
        return dfShort.format(date);
    }


    /**
     * Converts a string in the format yyyy-MM-dd'T'HH:mm'Z' to a date
     * @param date
     * @return
     * @throws Exception
     */
    public static Date fromIsoString(String date) throws Exception{
        return df.parse(date);
    }

    /**
     * Converts a string in the format yyyy-MM-dd to a date
     * @param date
     * @return
     * @throws Exception
     */
    public static Date fromIsoStringShort(String date) throws Exception {
        return dfShort.parse(date);
    }



}
