package com.accenture.jive.assignment.isa.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a stockmarket entry in the database.
 */

public class Stockmarket {
    /** primary key */
    private Integer stockId;
    private String stockName;
    private BigDecimal marketPrice;
    private LocalDate marketDate;
    private String industryName;

    /**
     * @return the primary key
     */
    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public LocalDate getMarketDate() {
        return marketDate;
    }

    public void setMarketDate(LocalDate marketDate) {
        this.marketDate = marketDate;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }
}