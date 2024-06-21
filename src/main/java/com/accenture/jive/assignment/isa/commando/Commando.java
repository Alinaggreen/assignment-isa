package com.accenture.jive.assignment.isa.commando;

/**
 * A commando can be executed and runs various tasks based on its implementation.
 */

public interface Commando {

    /**
     * Executes the main logic of the commando.
     *
     * @return true, when the application should continue to run, false otherwise
     * @throws CommandoException when an issue happens
     */
    boolean execute() throws CommandoException;

    /**
     * Determines if the commando should be executed based on the users input.
     *
     * @param userCommando the users input
     * @return true, if the commando should run
     */
    boolean shouldExecute(String userCommando);
}