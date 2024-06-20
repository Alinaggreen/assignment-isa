package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.persistence.Industry;
import com.accenture.jive.assignment.isa.persistence.Stock;
import com.accenture.jive.assignment.isa.persistence.Stockmarket;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class UserInteraction {

    private final Scanner scanner;

    public UserInteraction(Scanner scanner) {
        this.scanner = scanner;
    }

    public void listCommandos () {
        System.out.println("The following commands can be executed:");
        System.out.println("import - imports a csv file to the database");
        System.out.println("delete - deletes all data from the database");
        System.out.println("search - searches the id of a stock by typing the first characters of the company name");
        System.out.println("show - shows the the last ten prices for a specific stock");
        System.out.println("add - adds a new price for a specific stock and date");
        System.out.println("max - shows the highest price ever had for a stock when");
        System.out.println("min - shows the lowest price ever had for a stock and when");
        System.out.println("gap - shows the difference between highest and lowest price ever had for a stock");
        System.out.println("update - updates a stock's industry");
        System.out.println("list - lists all industries with Id and number of stocks assigned");
        System.out.println("export - exports all data to a csv file");
    }

    public void successfulCommando () {
        System.out.println("The requested commando was successfully executed.");
    }

    public void failedCommandoSQL () {
        System.out.println("The requested commando could not be executed due to issues with the database. " +
                "Please try again.");
    }

    public void missingStock () {
        System.out.println("There is no stock with this Id in the database. Please try again.");
    }

    public String readImportName () {
        System.out.println("Please enter the file path of the csv file you want to import " +
                "(for example C://dev1//isa//STOCK_DATA.csv):");
        return scanner.nextLine();
    }

    public String readExportName () {
        System.out.println("Please enter the file path where you would like to save the created csv file " +
                "(for example C://dev1//isa//STOCK_DATA_export.csv):");
        return scanner.nextLine();
    }

    public int readCompanyId () {
        System.out.println("Please enter the company id:");
        String inputId = scanner.nextLine();
        try {
            return Integer.parseInt(inputId);
        } catch (NumberFormatException cause) {
            System.out.println("Please enter a number!");
            readCompanyId();
        }
        return 0;
    }

    public BigDecimal readPrice () {
        System.out.println("Please enter the price in Euro:");
        String inputPrice = scanner.nextLine();
        try {
            return new BigDecimal(inputPrice);
        } catch (NumberFormatException cause) {
            System.out.println("Please enter a number!");
            readPrice();
        }
        return null;
    }

    public LocalDate readDate () {
        System.out.println("Please enter the date in dd.mm.yy Format (for example 01.01.01 for 01.01.2001):");
        String date = scanner.nextLine();
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yy"));
        } catch (DateTimeParseException cause) {
            System.out.println("Please enter a date!");
            readDate();
        }
        return null;
    }

    public String readIndustry () throws SQLException {
        System.out.println("Which industry should the company be assigned to instead?");
        return scanner.nextLine();
    }

    public String knowCompany () {
        System.out.println("Do you know the id of the company?");
        return scanner.nextLine();
    }

    public String knowIndustry () {
        System.out.println("Do you know the name of the new industry?");
        return scanner.nextLine();
    }

    public String shouldDelete() {
        System.out.println("Are you sure you want to delete everything from the database?");
        return scanner.nextLine();
    }

    public String readSearchCompany () {
        System.out.println("What company id do you want to search for? Please enter the first characters:");
        return scanner.nextLine();
    }

    public String readSearchIndustry () {
        System.out.println("What industry do you want to search for? Please enter the first characters:");
        return scanner.nextLine();
    }

    //TODO: rename printCompanyPlaceholder
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

    public void printIndustryPlaceholder (List<Industry> industries, String userCommando) {
        if (industries.isEmpty()) {
            System.out.println("There is currently no industry starting with " + userCommando + " in the database.");
        } else {
            System.out.println("The following industries start with " + userCommando + ":");
            for (Industry industry : industries) {
                System.out.println(industry.getName());
            }
        }
    }

    public void printIndustry (List<Industry> industries) {
        if (industries.isEmpty()) {
            System.out.println("There are currently no industries in the database.");
        } else {
            System.out.println("These are all the industries with the number of stocks assigned:");
            for (Industry industry : industries) {
                System.out.println("ID: " + industry.getId() + " - " + industry.getName()
                        + " - currently " + industry.getStockCount() + " stocks assigned");
            }
        }
    }

    public void showIndustry (String industry) {
        System.out.println("The company is currently assigned to the following industry: " + industry);
    }

    public void printPrice (List<Stockmarket> stockmarkets) {
        if (stockmarkets.isEmpty()) {
            System.out.println("There are currently no stockmarket entries for your desired stock.");
        } else {
            System.out.println("These are the last ten prices for your desired company:");
            for (Stockmarket stockmarket : stockmarkets) {
                System.out.println(stockmarket.getMarketPrice() + " € on " + stockmarket.getMarketDate());
            }
        }
    }

    //TODO: Boolean okay oder String ausgeben?
    public boolean foundCompany () {
        System.out.println("Did you find the desired company id?");
        String foundCompany = scanner.nextLine();
        return !"yes".equalsIgnoreCase(foundCompany);
    }

    public boolean foundIndustry () {
        System.out.println("Did you find the desired industry?");
        String foundCompany = scanner.nextLine();
        return !"yes".equalsIgnoreCase(foundCompany);
    }

    public void maxPrice (Stockmarket stockmarket) {
        System.out.println("The highest price was " + stockmarket.getMarketPrice() + "€ on "
                + stockmarket.getMarketDate() + ".");
    }

    public void minPrice (Stockmarket stockmarket) {
        System.out.println("The lowest price was " + stockmarket.getMarketPrice() + "€ on "
                + stockmarket.getMarketDate() + ".");
    }

    public void gapPrice (BigDecimal gapPrice) {
        System.out.println("The difference between highest and lowest price is " + gapPrice + "€.");
    }

    public void noEntries () {
        System.out.println("There are currently no stock market entries for this company.");
    }

    public void goodbye() {
        System.out.println("Thanks and Goodbye!");
    }
}