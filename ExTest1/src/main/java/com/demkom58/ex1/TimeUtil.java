package com.demkom58.ex1;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class TimeUtil {

    public static String getNowDate() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uu");
        return now.format(formatter);
    }

    public static String getNowTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh.mm");
        return now.format(formatter);
    }

}
