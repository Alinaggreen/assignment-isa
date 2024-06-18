package com.accenture.jive.assignment.isa.commando;

import com.accenture.jive.assignment.isa.service.DateService;
import com.accenture.jive.assignment.isa.service.IndustryService;
import com.accenture.jive.assignment.isa.service.StockService;
import com.accenture.jive.assignment.isa.service.StockmarketService;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandoFactory {

    private final Scanner scanner;
    private final StockService stockService;
    private final IndustryService industryService;
    private final StockmarketService stockmarketService;
    private final DateService dateService;

    public CommandoFactory (Scanner scanner, StockService stockService, IndustryService industryService, StockmarketService stockmarketService, DateService dateService) {
        this.scanner = scanner;
        this.stockService = stockService;
        this.industryService = industryService;
        this.stockmarketService = stockmarketService;
        this.dateService = dateService;
    }

    public List<Commando> createCommando() {

        List<Commando> commandos = new ArrayList<>();
        Commando importCommando = new ImportCommando(stockService, industryService, stockmarketService, dateService);
        Commando deleteCommando = new DeleteCommando(scanner, stockService, industryService, stockmarketService);
        Commando searchCommando = new SearchCommando(scanner, stockService);
        Commando exitCommando = new ExitCommando();

        commandos.add(importCommando);
        commandos.add(deleteCommando);
        commandos.add(searchCommando);
        commandos.add(exitCommando);

        return commandos;
    }
}