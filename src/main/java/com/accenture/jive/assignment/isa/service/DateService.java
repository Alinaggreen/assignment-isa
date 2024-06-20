package com.accenture.jive.assignment.isa.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateService {

    //TODO: Exception
    public LocalDate importDate(String importDate) {
        return LocalDate.parse(importDate, DateTimeFormatter.ofPattern("dd.MM.yy"));
    }

    //TODO: Exception
    public String exportDate (LocalDate exportDate) {
        return DateTimeFormatter.ofPattern("dd.MM.yy").format(exportDate);
    }
}