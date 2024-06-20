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

    public void startCommando () {
        System.out.println("Please enter the relevant data:");
    }

    public void succes() {
        System.out.println("You successfully " + "the database!");
    }

    public void successImport () {
        System.out.println("You successfully imported the data to the database!");
    }

    public void successUpdate (int addedRows) {
        System.out.println("You successfully updated " + addedRows + " entry.");
    }

    public void successExport () {
        System.out.println("You successfully exported the database!");
    }

    public void successDelete () {
        System.out.println("You successfully deleted everything from the database!");
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

    public String shouldDelete() {
        System.out.println("Are you sure you want to delete everything from the database?");
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

    public void failedCommando () {
        System.out.println("The requested command could not be executed. Please try again.");
    }

    public void goodbye() {
        System.out.println("Thanks and Goodbye!");
    }
}