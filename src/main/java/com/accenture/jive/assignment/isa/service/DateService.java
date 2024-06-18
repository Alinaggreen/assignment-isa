package com.accenture.jive.assignment.isa.service;

import java.sql.Date;
import java.time.LocalDate;

public class DateService {

    public Date readDate(String importDate) {
        String[] dayMonthYear = importDate.split("\\.");
        String day = dayMonthYear[0];
        String month = dayMonthYear[1];
        String year = dayMonthYear[2];
        int dayParsed = Integer.parseInt(day);
        int monthParsed = Integer.parseInt(month);
        int yearParsed = Integer.parseInt(year)+2000;
        LocalDate localdate = LocalDate.of(yearParsed, monthParsed, dayParsed);
        return Date.valueOf(localdate);
    }
}