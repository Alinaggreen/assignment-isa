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
    private final String RESET = "\u001B[0m";
    private final String RED = "\u001B[31m";
    private final String GREEN = "\u001B[32m";
    private final String CYAN = "\u001B[36m";
    private final String MAGENTA = "\u001B[35m";
    private final String YELLOW = "\u001B[33m";

    public UserInteraction(Scanner scanner) {
        this.scanner = scanner;
    }

    public void listCommandos () {
        System.out.println(CYAN + "The following commandos can be executed:" + RESET);
        System.out.println(YELLOW + "help" + RESET + " - shows all commandos that can be called");
        System.out.println(YELLOW + "import" + RESET + " - imports a CSV file to the database");
        System.out.println(YELLOW + "delete" + RESET + " - deletes all data from the database");
        System.out.println(YELLOW + "search" + RESET + " - searches the ID of a stock " +
                "by typing the first characters of the company's name");
        System.out.println(YELLOW + "show" + RESET + " - shows the last ten prices for a specific stock");
        System.out.println(YELLOW + "add" + RESET + " - adds a new price for a specific stock and date");
        System.out.println(YELLOW + "max" + RESET + " - shows date and amount " +
                "of highest price ever recorded for a stock");
        System.out.println(YELLOW + "min" + RESET + " - shows date and amount " +
                "of lowest price ever recorded for a stock");
        System.out.println(YELLOW + "gap" + RESET + " - shows the difference between " +
                "highest and lowest price ever recorded for a stock");
        System.out.println(YELLOW + "update" + RESET + " - updates a stock's industry");
        System.out.println(YELLOW + "list" + RESET + " - lists all industries with ID and number of stocks assigned");
        System.out.println(YELLOW + "export" + RESET + " - exports all data to a CSV file");
    }

    public void successfulCommando () {
        System.out.println(GREEN + "The requested commando was successfully executed." + RESET);
    }

    public void successTermination () {
        System.out.println(MAGENTA + "The requested commando has been canceled." + RESET);
    }

    public String failedCommandoSQL () {
        return RED + "The requested commando could not be executed due to issues with the database." + RESET;
    }

    public String failedCommandoIO () {
        return RED + "The requested commando could not be executed due to issues with the output operation." + RESET;
    }

    public String failedCommandoFile () {
        return RED + "The requested commando could not be executed because the file could not be found. " +
                "Please try again." + RESET;
    }

    public String failedCommandoNumberFormat () {
        return RED + "The requested commando could not be executed because the prices do not conform " +
                "to the number format. Please try again." + RESET;
    }

    public String failedCommandoDateTime () {
        return RED + "The requested commando could not be executed because the dates do not conform " +
                "to the date format. Please try again." + RESET;
    }

    public void missingStock () {
        System.out.println(MAGENTA + "There is no stock with this ID in the database. Please try again." + RESET);
    }

    public void missingExtension () {
        System.out.println(MAGENTA + "Please use the file extension .csv" + RESET);
    }

    public String readImportName () {
        System.out.println("Please enter the file path of the CSV file you want to import "
                + YELLOW + "(for example STOCK_DATA.csv)" + RESET + ":");
        return scanner.nextLine();
    }

    public String readExportName () {
        System.out.println("Please enter the file path where you would like to save the new CSV file "
                + YELLOW + "(for example STOCK_DATA_export.csv)" + RESET + ":");
        return scanner.nextLine();
    }

    public int readCompanyId () {
        System.out.println("Please enter the company ID:");
        String inputId = scanner.nextLine();
        try {
            return Integer.parseInt(inputId);
        } catch (NumberFormatException e) {
            System.out.println(RED + "Please enter a number!" + RESET);
            return readCompanyId();
        }
    }

    public BigDecimal readPrice () {
        System.out.println("Please enter the price in Euro:");
        String inputPrice = scanner.nextLine();
        try {
            return new BigDecimal(inputPrice);
        } catch (NumberFormatException e) {
            System.out.println(RED + "Please enter a number!" + RESET);
            return readPrice();
        }
    }

    public LocalDate readDate () {
        System.out.println("Please enter the date in dd.mm.yy format:");
        String date = scanner.nextLine();
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yy"));
        } catch (DateTimeParseException e) {
            System.out.println(RED + "Please enter a date!" + RESET);
            return readDate();
        }
    }

    public String readIndustry () {
        System.out.println("Which industry should the company be assigned to instead?");
        return scanner.nextLine();
    }

    public String knowCompany () {
        System.out.println("Do you know the ID of the company?"  + YELLOW + " yes / no" + RESET);
        return scanner.nextLine();
    }

    public String knowIndustry () {
        System.out.println("Do you know the name of the new industry?"  + YELLOW + " yes / no" + RESET);
        return scanner.nextLine();
    }

    public String shouldAdd () {
        System.out.println("Are you sure you want to add this stockmarket entry?"
                + YELLOW + " yes / no" + RESET);
        return scanner.nextLine();
    }

    public String shouldDelete() {
        System.out.println("Are you sure you want to delete everything from the database?"
                + YELLOW + " yes / no" + RESET);
        return scanner.nextLine();
    }

    public String shouldExport () {
        System.out.println("Are you sure you want to export the data from the database?"
                + YELLOW + " yes / no" + RESET);
        return scanner.nextLine();
    }

    public String shouldImport () {
        System.out.println("Are you sure you want to import the data to the database?"
                + YELLOW + " yes / no" + RESET);
        return scanner.nextLine();
    }

    public String shouldUpdate() {
        System.out.println("Are you sure you want to update this stock's industry?"
                + YELLOW + " yes / no" + RESET);
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
            System.out.println(MAGENTA + "There is currently no company starting with " + RESET
                    + "'" + userCommando + "'" + MAGENTA + " in the database." + RESET);
        } else {
            System.out.println(CYAN + "The following companies start with " + RESET
                    + "'" + userCommando + "'" + CYAN + ":" + RESET);
            for (Stock stock : stocks) {
                System.out.println("ID: " + stock.getId() + " - " + stock.getName());
            }
        }
    }

    public void printIndustryPlaceholder (List<Industry> industries, String userCommando) {
        if (industries.isEmpty()) {
            System.out.println(MAGENTA + "There is currently no industry starting with "+ RESET
                    + "'" + userCommando + "'" + MAGENTA + " in the database." + RESET);
        } else {
            System.out.println(CYAN + "The following industries start with " + RESET
                    + "'" + userCommando + "'" + CYAN + ":" + RESET);
            for (Industry industry : industries) {
                System.out.println(industry.getName());
            }
        }
    }

    public void printIndustry (List<Industry> industries) {
        if (industries.isEmpty()) {
            System.out.println(MAGENTA + "There are currently no industries in the database." + RESET);
        } else {
            System.out.println(CYAN + "These are all the industries with the number of stocks assigned:"+ RESET);
            for (Industry industry : industries) {
                System.out.println("ID: " + industry.getId() + " - " + industry.getName()
                        + " - currently " + industry.getStockCount() + " stocks assigned");
            }
        }
    }

    public void showIndustry (String industry) {
        System.out.println("The company is currently assigned to the following industry: " + CYAN + industry + RESET);
    }

    public void printPrice (List<Stockmarket> stockmarkets) {
        if (stockmarkets.isEmpty()) {
            System.out.println(MAGENTA + "There are currently no stockmarket entries for this stock." + RESET);
        } else {
            System.out.println(CYAN + "These are the last ten prices for this stock:" + RESET);
            for (Stockmarket stockmarket : stockmarkets) {
                System.out.println(stockmarket.getMarketPrice() + "€ on " + stockmarket.getMarketDate());
            }
        }
    }

    public boolean foundCompany () {
        System.out.println("Did you find the desired company ID?" + YELLOW + " yes / no" + RESET);
        String foundCompany = scanner.nextLine();
        return !"yes".equalsIgnoreCase(foundCompany);
    }

    public boolean foundIndustry () {
        System.out.println("Did you find the desired industry?" + YELLOW + " yes / no" + RESET);
        String foundCompany = scanner.nextLine();
        return !"yes".equalsIgnoreCase(foundCompany);
    }

    public void maxPrice (Stockmarket stockmarket) {
        if (stockmarket.getStockId() != null) {
            System.out.println("The highest price for this stock was " + CYAN
                    + stockmarket.getMarketPrice() + "€" + RESET + " on " + CYAN
                    + stockmarket.getMarketDate() + RESET + ".");
        } else {
            noEntries();
        }
    }

    public void minPrice (Stockmarket stockmarket) {
        if (stockmarket.getStockId() != null) {
            System.out.println("The lowest price for this stock was " + CYAN
                    + stockmarket.getMarketPrice() + "€" + RESET + " on " + CYAN
                    + stockmarket.getMarketDate() + RESET + ".");
        } else {
            noEntries();
        }
    }

    public void gapPrice (Stockmarket stockmarketMax, Stockmarket stockmarketMin) {
        if (stockmarketMax.getMarketPrice().equals(stockmarketMin.getMarketPrice())) {
            System.out.println("There is currently only one stockmarket entry for this stock: " + CYAN
                    + stockmarketMax.getMarketPrice() + "€" + RESET + " on " + CYAN +
                    stockmarketMax.getMarketDate() + RESET + ".");
        } else {
            maxPrice(stockmarketMax);
            minPrice(stockmarketMin);
            System.out.println("The difference between highest and lowest price is " + CYAN
                    + stockmarketMax.getMarketPrice().subtract(stockmarketMin.getMarketPrice()) +
                    "€" + RESET + ".");
        }
    }

    public void noEntries () {
        System.out.println(MAGENTA + "There are currently no stock market entries for this company." + RESET);
    }

    public void goodbye() {
        System.out.println(GREEN + "Thank you very much and Goodbye!" + RESET);
    }
}