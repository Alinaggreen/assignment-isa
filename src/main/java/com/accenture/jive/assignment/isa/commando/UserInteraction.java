package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.persistence.Stock;
import java.util.List;
import java.util.Scanner;

public class UserInteraction {

    private final Scanner scanner;

    public UserInteraction(Scanner scanner) {
        this.scanner = scanner;
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

}