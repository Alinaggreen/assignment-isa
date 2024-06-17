package com.accenture.jive.assignment.isa.commando;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandoFactory {

    private final Scanner scanner;
    private final Connection connection;

    public CommandoFactory (Scanner scanner, Connection connection) {
        this.scanner = scanner;
        this.connection = connection;
    }

    public List<Commando> createCommando() {

        List<Commando> commandos = new ArrayList<>();
        Commando insertCommando = new ImportCommando(connection);
        Commando exitCommando = new ExitCommando();

        commandos.add(insertCommando);
        commandos.add(exitCommando);

        return commandos;
    }
}