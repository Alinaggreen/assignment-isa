package com.accenture.jive.assignment.isa.persistence;

import java.sql.Date;

public class Stockmarket {

    private Integer stockId;
    private float marketPrice;
    private Date marketDate;

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public float getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(float marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Date getMarketDate() {
        return marketDate;
    }

    public void setMarketDate(Date marketDate) {
        this.marketDate = marketDate;
    }
}