package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.persistence.Stock;
import com.accenture.jive.assignment.isa.service.DateService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class UserInteraction {

    private final Scanner scanner;
    private final DateService dateService;

    public UserInteraction(Scanner scanner, DateService dateService) {
        this.scanner = scanner;
        this.dateService = dateService;
    }

    public void startCommando () {
        System.out.println("Please enter the relevant data:");
    }

    public void successUpdate (int addedRows) {
        System.out.println("You successfully updated " + addedRows + " entries.");
    }

    public int readCompanyId () {
        System.out.println("Please enter the company id:");
        String id = scanner.nextLine();
        return Integer.parseInt(id);
    }

    public BigDecimal readPrice () {
        System.out.println("Please enter the price in Euro:");
        String price = scanner.nextLine();
        return new BigDecimal(price);
    }

    //TODO: Tell user, that year only has 2 digits, not 4.
    public LocalDate readDate () {
        System.out.println("Please enter the date in dd.mm.yy Format:");
        String date = scanner.nextLine();
        return dateService.importDate(date);
    }

    public String knowCompany () {
        System.out.println("Do you know the id of the company?");
        return scanner.nextLine();
    }

    public String readSearchCompany () {
        System.out.println("What company id do you want to search for? Please enter the first characters:");
        return scanner.nextLine();
    }

    public void printCompany (List<Stock> stocks, String userCommando) {
        if (stocks.isEmpty()) {
            System.out.println("There is currently no company starting with " + userCommando + " in the database.");
        } else {
            System.out.println("The following companies start with " + userCommando + ":");
            for (Stock stock : stocks) {
                System.out.println("ID: " + stock.getId() + " - " + stock.getName());
            }
        }
    }

    public boolean foundCompany () {
        System.out.println("Did you find the desired company id?");
        String foundCompany = scanner.nextLine();
        if ("yes".equalsIgnoreCase(foundCompany)) {
            return false;
        } else {
            return true;
        }
    }

    public void goodbye() {
        System.out.println("Thanks and Goodbye!");
    }
}