package com.accenture.jive.assignment.isa.commando;

//TODO: Exception
public class ExitCommando implements Commando {

    private final UserInteraction userInteraction;

    public ExitCommando(UserInteraction userInteraction) {
        this.userInteraction = userInteraction;
    }

    @Override
    public boolean execute() {
        userInteraction.goodbye();
        return false;
    }

    @Override
    public boolean shouldExecute(String line) {
        return "exit".equalsIgnoreCase(line);
    }
}