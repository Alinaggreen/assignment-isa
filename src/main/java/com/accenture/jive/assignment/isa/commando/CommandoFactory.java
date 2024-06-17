package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.service.StockService;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandoFactory {

    private final Scanner scanner;
    private final Connection connection;
    private final StockService stockService;

    public CommandoFactory (Scanner scanner, Connection connection, StockService stockService) {
        this.scanner = scanner;
        this.connection = connection;
        this.stockService = stockService;
    }

    public List<Commando> createCommando() {

        List<Commando> commandos = new ArrayList<>();
        Commando importCommando = new ImportCommando(connection, stockService);
        Commando deleteCommando = new DeleteCommando(scanner, connection);
        Commando exitCommando = new ExitCommando();

        commandos.add(importCommando);
        commandos.add(deleteCommando);
        commandos.add(exitCommando);

        return commandos;
    }
}