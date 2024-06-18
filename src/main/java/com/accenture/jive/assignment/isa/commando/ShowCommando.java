package com.accenture.jive.assignment.isa.commando;

import java.util.Scanner;

public class ShowCommando implements Commando {

    private final Scanner scanner;

    public ShowCommando(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public boolean execute() throws CommandoException {

        //TODO: Search company id if unknown
        System.out.println("Please enter the company id you want to see the last ten prices of:");
        String id = scanner.nextLine();
        int stockId = Integer.parseInt(id);







        return true;
    }

    @Override
    public boolean shouldExecute(String line) {
        return "search".equalsIgnoreCase(line);
    }
}