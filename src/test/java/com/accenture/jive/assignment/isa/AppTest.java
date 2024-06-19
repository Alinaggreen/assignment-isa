package com.accenture.jive.assignment.isa;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    public static void main (String[] args) {

        String importDate = "01.01.22";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
        LocalDate exportDate = LocalDate.parse(importDate, formatter);

        Date date = Date.valueOf(exportDate);

        System.out.println(date);

        LocalDate localdate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-yy");
        String s = DateTimeFormatter.ofPattern("dd.MM.yy").format(localdate);

        System.out.println(s);





}
}
