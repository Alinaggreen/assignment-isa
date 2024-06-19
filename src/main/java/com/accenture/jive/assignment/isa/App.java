package com.accenture.jive.assignment.isa;

import com.accenture.jive.assignment.isa.commando.Commando;
import com.accenture.jive.assignment.isa.commando.CommandoException;
import com.accenture.jive.assignment.isa.commando.CommandoFactory;
import com.accenture.jive.assignment.isa.service.DateService;
import com.accenture.jive.assignment.isa.service.IndustryService;
import com.accenture.jive.assignment.isa.service.StockService;
import com.accenture.jive.assignment.isa.service.StockmarketService;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class App {

    public void run(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        StockService stockService = new StockService(scanner, connection);
        IndustryService industryService = new IndustryService(connection);
        StockmarketService stockmarketService = new StockmarketService(connection);
        DateService dateService = new DateService();
        CommandoFactory commandoFactory = new CommandoFactory(scanner, stockService, industryService, stockmarketService, dateService);
        List<Commando> commandos = commandoFactory.createCommando();

        System.out.println("Hello there!");

        boolean shouldRun = true;
        while (shouldRun) {
            System.out.println("What do you want to do?");
            String userCommando = scanner.nextLine();

            for (Commando commando : commandos) {
                if (commando.shouldExecute(userCommando)) {
                    try {
                        shouldRun = commando.execute();
                    } catch (CommandoException cause) {
                        System.out.println("System stopped because of an Exception");
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Connector connector = new Connector();
        try (Connection connection = connector.getConnection()) {
            new App().run(connection);
        } catch (Exception cause) {
            System.out.println("System stopped because of an Exception");
        }
    }
}