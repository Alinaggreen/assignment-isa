package com.accenture.jive.assignment.isa;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {

    public void run() {
        Scanner scanner = new Scanner(System.in);
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
        new App().run();
    }
}