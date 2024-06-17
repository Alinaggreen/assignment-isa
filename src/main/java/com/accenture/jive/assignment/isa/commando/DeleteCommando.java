package com.accenture.jive.assignment.isa.commando;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteCommando implements Commando {

    private final Scanner scanner;
    private final Connection connection;

    public DeleteCommando(Scanner scanner, Connection connection) {
        this.scanner = scanner;
        this.connection = connection;
    }

    @Override
    public boolean execute() {
        System.out.println("Are you sure you want to delete everything from the database?");
        String userCommando = scanner.nextLine();
        if ("yes".equalsIgnoreCase(userCommando)) {
            try {
                // TODO: Muss hier jeder DELETE einzeln ausgeführt werden?
                String sqlStockmarket = "DELETE FROM stockmarket";
                String sqlStock = "DELETE FROM stock";
                String sqlIndustry = "DELETE FROM industry";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlStockmarket);
                preparedStatement.execute();
                preparedStatement = connection.prepareStatement(sqlStock);
                preparedStatement.execute();
                preparedStatement = connection.prepareStatement(sqlIndustry);
                preparedStatement.execute();

                System.out.println("You successfully deleted everything from the database!");
            } catch (SQLException e) {
                System.out.println("SQLException");
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public boolean shouldExecute(String line) {
        return "delete".equalsIgnoreCase(line);
    }
}