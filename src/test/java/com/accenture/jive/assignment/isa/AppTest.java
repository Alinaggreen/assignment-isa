package com.accenture.jive.assignment.isa;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.math.BigDecimal;
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

        String s = "abc";
        LocalDate d = LocalDate.parse(s, DateTimeFormatter.ofPattern("dd.MM.yy"));


}
}
