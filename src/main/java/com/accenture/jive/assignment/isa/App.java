package com.accenture.jive.assignment.isa;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {

    public void run() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Connector connector = new Connector();
        Connection connection = connector.getConnection();

        System.out.println("Hello there!");

        boolean shouldRun = true;
        while (shouldRun) {
            System.out.println("What do you want to do?");
            String userCommando = scanner.nextLine();
            System.out.println(userCommando);
            if ("exit".equalsIgnoreCase(userCommando)) {
                shouldRun = false;
            } else if ("add".equalsIgnoreCase(userCommando)) {

            } else if ("remove".equalsIgnoreCase(userCommando)) {

            } else if ("show".equalsIgnoreCase(userCommando)) {

            }
        }
    }

    public static void main(String[] args) {
        try {
            new App().run();
        } catch (SQLException cause) {
            System.out.println("system stopped because of an exception.");
            cause.printStackTrace();
        }
    }
}