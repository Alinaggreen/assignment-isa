package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.service.DateService;
import com.accenture.jive.assignment.isa.service.StockmarketService;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

public class AddCommando implements Commando {

    private final Scanner scanner;
    private final DateService dateService;
    private final StockmarketService stockmarketService;

    public AddCommando(Scanner scanner, DateService dateService, StockmarketService stockmarketService) {
        this.scanner = scanner;
        this.dateService = dateService;
        this.stockmarketService = stockmarketService;
    }

    @Override
    public boolean execute() throws CommandoException {

        System.out.println("Please enter the stock market data you want to add:");
        // TODO: Search for Company Id if unknown
        System.out.println("Company Id:");
        String id = scanner.nextLine();
        int stockId = Integer.parseInt(id);
        System.out.println("Price in Euro:");
        String price = scanner.nextLine();
        float priceParsed = Float.parseFloat(price);
        System.out.println("Date in dd.mm.yyyy Format:");
        String date = scanner.nextLine();
        Date dateFormatted = dateService.readDate(date);

        try {
            int addedRows = stockmarketService.addStockmarket(stockId, priceParsed, dateFormatted);
            System.out.println("You successfully added " + addedRows + " new stock market entry.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    @Override
    public boolean shouldExecute(String line) {
        return "add".equalsIgnoreCase(line);
    }
}
