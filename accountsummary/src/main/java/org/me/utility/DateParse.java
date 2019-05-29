package org.me.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateParse {

    // static method for parsing fromDate and toDate
    public static LocalDateTime parseDate(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime localDate = LocalDateTime.parse(date, dtf);
        return localDate;
    }

}
