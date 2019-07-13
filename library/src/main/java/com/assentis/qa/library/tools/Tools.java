package com.assentis.qa.library.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Tools {

    public static String getActDateAsString(String formatString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
        //2019-04-29
        java.util.Date actDate = new java.util.Date();
        return format.format(actDate);
    }
}
