package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.persistence.Industry;
import com.accenture.jive.assignment.isa.persistence.Stock;
import com.accenture.jive.assignment.isa.persistence.Stockmarket;
import com.accenture.jive.assignment.isa.service.DateService;
import com.accenture.jive.assignment.isa.service.IndustryService;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class UserInteraction {

    private final Scanner scanner;
    private final DateService dateService;
    private final IndustryService industryService;

    public UserInteraction(Scanner scanner, DateService dateService, IndustryService industryService) {
        this.scanner = scanner;
        this.dateService = dateService;
        this.industryService = industryService;
    }

    public void startCommando () {
        System.out.println("Please enter the relevant data:");
    }

    public void successImport () {
        System.out.println("You successfully imported the data to the database!");
    }

    public void successUpdate (int addedRows) {
        System.out.println("You successfully updated " + addedRows + " entry.");
    }

    public void successDelete () {
        System.out.println("You successfully deleted everything from the database!");
    }

    public String readImport () {
        System.out.println("Please enter the file path of the csv file you want to import " +
                "(for example C://dev1//isa//STOCK_DATA.csv):");
        return scanner.nextLine();
    }

    //TODO: return String
    public int readCompanyId () {
        System.out.println("Please enter the company id:");
        String id = scanner.nextLine();
        return Integer.parseInt(id);
    }

    //TODO: return String
    public BigDecimal readPrice () {
        System.out.println("Please enter the price in Euro:");
        String price = scanner.nextLine();
        return new BigDecimal(price);
    }

    //TODO: Tell user, that year only has 2 digits, not 4.
    //TODO: return String
    public LocalDate readDate () {
        System.out.println("Please enter the date in dd.mm.yy Format:");
        String date = scanner.nextLine();
        return dateService.importDate(date);
    }

    //TODO: return String
    public int readIndustry () throws SQLException {
        System.out.println("Which industry should the company be assigned to instead?");
        String industry = scanner.nextLine();
        return industryService.searchIndustryId(industry);
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
        System.out.println("These are all the industries with the number of stocks assigned:");
        for (Industry industry : industries) {
            System.out.println("ID: " + industry.getId() + " - " + industry.getName()
                    + " - currently " + industry.getStockCount() + " stocks assigned");
        }
    }

    public void showIndustry (String industry) {
        System.out.println("The company is currently assigned to the following industry: " + industry);
    }

    public void printPrice (List<Stockmarket> stockmarkets) {
        System.out.println("These are the last ten prices for your desired company:");
        for (Stockmarket stockmarket : stockmarkets) {
            System.out.println(stockmarket.getMarketPrice() + " € on " + stockmarket.getMarketDate());
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

    public void maxPrice (BigDecimal maxPrice) {
        System.out.println("The highest price was " + maxPrice + "€.");
    }

    public void minPrice (BigDecimal minPrice) {
        System.out.println("The lowest price was " + minPrice + "€.");
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