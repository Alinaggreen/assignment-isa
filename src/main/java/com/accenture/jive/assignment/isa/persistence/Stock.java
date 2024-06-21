package com.accenture.jive.assignment.isa.persistence;

/**
 * Represents a stock in the database.
 */

public class Stock {
    /** primary key */
    private Integer id;
    private String name;

    /**
     * @return the primary key
     */
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
}