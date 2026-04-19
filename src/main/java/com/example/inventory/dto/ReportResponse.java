package com.example.inventory.dto;

import java.math.BigDecimal;
import java.util.List;

public class ReportResponse {
    private List<ProductResponse> items;
    private long totalItems;
    private BigDecimal totalValue;

    public List<ProductResponse> getItems() { return items; }
    public void setItems(List<ProductResponse> items) { this.items = items; }
    public long getTotalItems() { return totalItems; }
    public void setTotalItems(long totalItems) { this.totalItems = totalItems; }
    public BigDecimal getTotalValue() { return totalValue; }
    public void setTotalValue(BigDecimal totalValue) { this.totalValue = totalValue; }
}
