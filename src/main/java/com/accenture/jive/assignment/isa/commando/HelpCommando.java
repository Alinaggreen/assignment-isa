package com.accenture.jive.assignment.isa.commando;

/**
 * Shows all callable commandos with a short description.
 */

public class HelpCommando implements Commando {

    private final UserInteraction userInteraction;

    public HelpCommando(UserInteraction userInteraction) {
        this.userInteraction = userInteraction;
    }

    @Override
    public boolean execute() throws CommandoException {
        userInteraction.listCommandos();
        return true;
    }

    @Override
    public boolean shouldExecute(String line) {
        return "help".equalsIgnoreCase(line);
    }
}