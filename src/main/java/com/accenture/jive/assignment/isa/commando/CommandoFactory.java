package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.service.IndustryService;
import com.accenture.jive.assignment.isa.service.StockService;
import com.accenture.jive.assignment.isa.service.StockmarketService;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandoFactory {

    private final Scanner scanner;
    private final Connection connection;
    private final StockService stockService;
    private final IndustryService industryService;
    private final StockmarketService stockmarketService;

    public CommandoFactory (Scanner scanner, Connection connection, StockService stockService, IndustryService industryService, StockmarketService stockmarketService) {
        this.scanner = scanner;
        this.connection = connection;
        this.stockService = stockService;
        this.industryService = industryService;
        this.stockmarketService = stockmarketService;
    }

    public List<Commando> createCommando() {

        List<Commando> commandos = new ArrayList<>();
        Commando importCommando = new ImportCommando(connection, stockService, industryService);
        Commando deleteCommando = new DeleteCommando(scanner, connection);
        Commando exitCommando = new ExitCommando();

        commandos.add(importCommando);
        commandos.add(deleteCommando);
        commandos.add(exitCommando);

        return commandos;
    }
}