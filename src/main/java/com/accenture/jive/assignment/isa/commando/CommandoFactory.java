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
    private final UserInteraction userInteraction;

    public CommandoFactory (Scanner scanner, StockService stockService, IndustryService industryService, StockmarketService stockmarketService, DateService dateService, UserInteraction userInteraction) {
        this.scanner = scanner;
        this.stockService = stockService;
        this.industryService = industryService;
        this.stockmarketService = stockmarketService;
        this.dateService = dateService;
        this.userInteraction = userInteraction;
    }

    public List<Commando> createCommando() {

        List<Commando> commandos = new ArrayList<>();
        Commando importCommando = new ImportCommando(stockService, industryService, stockmarketService, dateService);
        Commando deleteCommando = new DeleteCommando(scanner, stockService, industryService, stockmarketService);
        Commando searchCommando = new SearchCommando(stockService, userInteraction);
        Commando addCommando = new AddCommando(stockService, stockmarketService, userInteraction);
        Commando showCommando = new ShowCommando(scanner, stockService, stockmarketService, userInteraction);
        Commando maxCommando = new MaxCommando(stockService, stockmarketService, userInteraction);
        Commando minCommando = new MinCommando(stockService, stockmarketService, userInteraction);
        Commando gapCommando = new GapCommando(stockService, stockmarketService, userInteraction);
        Commando updateCommando = new UpdateCommando(scanner, stockService, industryService, userInteraction);
        Commando listCommando = new ListCommando(industryService, userInteraction);
        Commando exportCommando = new ExportCommando(stockmarketService, dateService);
        Commando exitCommando = new ExitCommando(userInteraction);

        commandos.add(importCommando);
        commandos.add(deleteCommando);
        commandos.add(searchCommando);
        commandos.add(addCommando);
        commandos.add(showCommando);
        commandos.add(maxCommando);
        commandos.add(minCommando);
        commandos.add(gapCommando);
        commandos.add(updateCommando);
        commandos.add(listCommando);
        commandos.add(exportCommando);
        commandos.add(exitCommando);

        return commandos;
    }
}