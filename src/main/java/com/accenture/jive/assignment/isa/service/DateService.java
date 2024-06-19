package com.accenture.jive.assignment.isa.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateService {

    public Date readDate(String importDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
        LocalDate localdate = LocalDate.parse(importDate, formatter);

        return Date.valueOf(localdate);
    }
}