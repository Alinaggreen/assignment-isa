package com.accenture.jive.assignment.isa;

import com.accenture.jive.assignment.isa.commando.Commando;
import com.accenture.jive.assignment.isa.commando.CommandoException;
import com.accenture.jive.assignment.isa.commando.CommandoFactory;
import com.accenture.jive.assignment.isa.commando.UserInteraction;
import com.accenture.jive.assignment.isa.service.IndustryService;
import com.accenture.jive.assignment.isa.service.StockService;
import com.accenture.jive.assignment.isa.service.StockmarketService;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

/**
 * Starter class for the application.
 * Contains main method and executes commandos based on the user's input.
 */

public class App {
    public void run (Connection connection) throws CommandoException {
        Scanner scanner = new Scanner(System.in);
        List<Commando> commandos = getCommandos(connection, scanner);

        System.out.println("Hello there!");

        boolean shouldRun = true;
        while (shouldRun) {
            System.out.println("What do you want to do? Type 'help' for a list of all Commandos.");
            String userCommando = scanner.nextLine();

            for (Commando commando : commandos) {
                if (commando.shouldExecute(userCommando)) {
                    try {
                        shouldRun = commando.execute();
                    } catch (CommandoException e) {
                        throw new CommandoException("System stopped because of a Commando Exception", e);
                    }
                }
            }
        }
    }

    public List<Commando> getCommandos (Connection connection, Scanner scanner) {
        StockService stockService = new StockService(connection);
        IndustryService industryService = new IndustryService(connection);
        StockmarketService stockmarketService = new StockmarketService(connection);
        UserInteraction userInteraction = new UserInteraction(scanner);
        CommandoFactory commandoFactory = new CommandoFactory(stockService, industryService,
                stockmarketService, userInteraction);
        return commandoFactory.createCommando();
    }

    public static void main (String[] args) {
        Connector connector = new Connector();
        try (Connection connection = connector.getConnection()) {
            new App().run(connection);
        } catch (Exception e) {
            System.out.println("System stopped because of an Exception");
            e.getCause();
        }
    }
}