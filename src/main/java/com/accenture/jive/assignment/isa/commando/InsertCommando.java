package com.accenture.jive.assignment.isa.commando;

public class InsertCommando implements Commando{

    @Override
    public boolean execute() throws CommandoException {
        return false;
    }

    @Override
    public boolean shouldExecute(String line) {
        return "insert".equalsIgnoreCase(line);
    }
}