package com.accenture.jive.assignment.isa;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    public static void main (String[] args) {
        float price = Float.parseFloat("€ 123,45".replace(",", ".").substring(2));
        System.out.println(price);
}
}
