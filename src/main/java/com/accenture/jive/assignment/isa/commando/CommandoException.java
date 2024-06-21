package com.accenture.jive.assignment.isa.commando;

/**
 * Exception subclass for handling exceptions within the commandos.
 */

public class CommandoException extends Exception{

    public CommandoException(String message, Throwable cause) {
        super(message, cause);
    }
}