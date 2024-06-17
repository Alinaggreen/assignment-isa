package com.accenture.jive.assignment.isa.commando;

public class CommandoException extends Exception{

    CommandoException (String message) {
        super(message);
    }

    CommandoException (Throwable cause) {
        super(cause);
    }

    public CommandoException(String message, Throwable cause) {
        super(message, cause);
    }

}
