package com.accenture.jive.assignment.isa.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateService {

    public LocalDate readDate(String importDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");

        return LocalDate.parse(importDate, formatter);
    }

    public String exportDate (LocalDate exportDate) {

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-yy");

        return DateTimeFormatter.ofPattern("dd.MM.yy").format(exportDate);
    }
}