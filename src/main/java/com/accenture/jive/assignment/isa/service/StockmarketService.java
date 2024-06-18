package com.accenture.jive.assignment.isa.service;

import java.sql.Connection;

public class StockmarketService {

    private final Connection connection;

    public StockmarketService(Connection connection) {
        this.connection = connection;
    }
}
