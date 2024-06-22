package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.persistence.Industry;
import com.accenture.jive.assignment.isa.persistence.Stock;
import com.accenture.jive.assignment.isa.persistence.Stockmarket;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Interactions with user (Input & Output), that are called in the commandos.
 */

public class UserInteraction {

    private final Scanner scanner;

    public UserInteraction(Scanner scanner) {
        this.scanner = scanner;
    }

    public void listCommandos () {
        System.out.println("The following commandos can be executed:");
        System.out.println("help - shows all commandos that can be called");
        System.out.println("import - imports a CSV file to the database");
        System.out.println("delete - deletes all data from the database");
        System.out.println("search - searches the ID of a stock by typing the first characters of the company's name");
        System.out.println("show - shows the last ten prices for a specific stock");
        System.out.println("add - adds a new price for a specific stock and date");
        System.out.println("max - shows date and amount of highest price ever recorded for a stock");
        System.out.println("min - shows date and amount of lowest price ever recorded for a stock");
        System.out.println("gap - shows the difference between highest and lowest price ever recorded for a stock");
        System.out.println("update - updates a stock's industry");
        System.out.println("list - lists all industries with ID and number of stocks assigned");
        System.out.println("export - exports all data to a CSV file");
    }

    public void successfulCommando () {
        System.out.println("The requested commando was successfully executed.");
    }

    public void successTermination () {
        System.out.println("The requested commando has been canceled.");
    }

    public String failedCommandoSQL () {
        return "The requested commando could not be executed due to issues with the database.";
    }

    public String failedCommandoIO () {
        return "The requested commando could not be executed due to issues with the output operation.";
    }

    public String failedCommandoFile () {
        return "The requested commando could not be executed because the file could not be found. " +
                "Please try again.";
    }

    public String failedCommandoNumberFormat () {
        return "The requested commando could not be executed because the prices do not conform " +
                "to the number format. Please try again.";
    }

    public String failedCommandoDateTime () {
        return "The requested commando could not be executed because the dates do not conform " +
                "to the date format. Please try again.";
    }

    public void missingStock () {
        System.out.println("There is no stock with this ID in the database. Please try again.");
    }

    public String readImportName () {
        System.out.println("Please enter the file path of the CSV file you want to import " +
                "(for example STOCK_DATA.csv):");
        return scanner.nextLine();
    }

    public String readExportName () {
        System.out.println("Please enter the file path where you would like to save the new CSV file " +
                "(for example STOCK_DATA_export.csv):");
        return scanner.nextLine();
    }

    public int readCompanyId () {
        System.out.println("Please enter the company ID:");
        String inputId = scanner.nextLine();
        try {
            return Integer.parseInt(inputId);
        } catch (NumberFormatException e) {
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
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number!");
            readPrice();
        }
        return null;
    }

    public LocalDate readDate () {
        System.out.println("Please enter the date in dd.mm.yy format:");
        String date = scanner.nextLine();
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yy"));
        } catch (DateTimeParseException e) {
            System.out.println("Please enter a date!");
            readDate();
        }
        return null;
    }

    public String readIndustry () {
        System.out.println("Which industry should the company be assigned to instead?");
        return scanner.nextLine();
    }

    public String knowCompany () {
        System.out.println("Do you know the ID of the company?");
        return scanner.nextLine();
    }

    public String knowIndustry () {
        System.out.println("Do you know the name of the new industry?");
        return scanner.nextLine();
    }

    public String shouldAdd () {
        System.out.println("Are you sure you want to add this stockmarket entry?");
        return scanner.nextLine();
    }

    public String shouldDelete() {
        System.out.println("Are you sure you want to delete everything from the database?");
        return scanner.nextLine();
    }

    public String shouldExport () {
        System.out.println("Are you sure you want to export the data from the database?");
        return scanner.nextLine();
    }

    public String shouldImport () {
        System.out.println("Are you sure you want to import the data to the database?");
        return scanner.nextLine();
    }

    public String shouldUpdate() {
        System.out.println("Are you sure you want to update this stock's industry?");
        return scanner.nextLine();
    }

    public String readSearchCompany () {
        System.out.println("What company do you want to search for? Please enter the first characters:");
        return scanner.nextLine();
    }

    public String readSearchIndustry () {
        System.out.println("What industry do you want to search for? Please enter the first characters:");
        return scanner.nextLine();
    }

    public void printCompanyPlaceholder (List<Stock> stocks, String userCommando) {
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
            System.out.println("There are currently no stockmarket entries for this stock.");
        } else {
            System.out.println("These are the last ten prices for this stock:");
            for (Stockmarket stockmarket : stockmarkets) {
                System.out.println(stockmarket.getMarketPrice() + " € on " + stockmarket.getMarketDate());
            }
        }
    }

    public boolean foundCompany () {
        System.out.println("Did you find the desired company ID?");
        String foundCompany = scanner.nextLine();
        return !"yes".equalsIgnoreCase(foundCompany);
    }

    public boolean foundIndustry () {
        System.out.println("Did you find the desired industry?");
        String foundCompany = scanner.nextLine();
        return !"yes".equalsIgnoreCase(foundCompany);
    }

    public void maxPrice (Stockmarket stockmarket) {
        if (stockmarket.getStockId() != null) {
            System.out.println("The highest price for this stock was " + stockmarket.getMarketPrice() + "€ on "
                    + stockmarket.getMarketDate() + ".");
        } else {
            noEntries();
        }
    }

    public void minPrice (Stockmarket stockmarket) {
        if (stockmarket.getStockId() != null) {
            System.out.println("The lowest price for this stock was " + stockmarket.getMarketPrice() + "€ on "
                    + stockmarket.getMarketDate() + ".");
        } else {
            noEntries();
        }
    }

    public void gapPrice (Stockmarket stockmarketMax, Stockmarket stockmarketMin) {
        if (stockmarketMax.getMarketPrice().equals(stockmarketMin.getMarketPrice())) {
            System.out.println("There is currently only one stockmarket entry for this stock: "
                    + stockmarketMax.getMarketPrice() + "€ on " + stockmarketMax.getMarketDate());
        } else {
            maxPrice(stockmarketMax);
            minPrice(stockmarketMin);
            System.out.println("The difference between highest and lowest price is "
                    + stockmarketMax.getMarketPrice().subtract(stockmarketMin.getMarketPrice()) + "€.");
        }
    }

    public void noEntries () {
        System.out.println("There are currently no stock market entries for this company.");
    }

    public void goodbye() {
        System.out.println("Thank you very much and Goodbye!");
    }
}