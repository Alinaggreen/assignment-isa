package com.accenture.jive.assignment.isa.persistence;

/**
 * Represents an industry entry in the database.
 */

public class Industry {
    /** primary key */
    private Integer id;
    private String name;
    private Integer stockCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }
}