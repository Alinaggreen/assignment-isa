package com.accenture.jive.assignment.isa.commando;

public class ExitCommando implements Commando {

    @Override
    public boolean execute() {
        System.out.println("Thanks and Goodbye!");
        return false;
    }

    @Override
    public boolean shouldExecute(String line) {
        return "exit".equalsIgnoreCase(line);
    }
}