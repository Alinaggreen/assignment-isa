package com.accenture.jive.assignment.isa.commando;

public interface Commando {

    boolean execute() throws CommandoException;

    boolean shouldExecute(String line);

}