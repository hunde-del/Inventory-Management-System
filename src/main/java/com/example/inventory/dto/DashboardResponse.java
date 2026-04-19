package com.example.inventory.dto;

import java.util.List;

public class DashboardResponse {
    private long totalProducts;
    private long lowStockItems;
    private List<ProductResponse> recentlyAddedProducts;

    public long getTotalProducts() { return totalProducts; }
    public void setTotalProducts(long totalProducts) { this.totalProducts = totalProducts; }
    public long getLowStockItems() { return lowStockItems; }
    public void setLowStockItems(long lowStockItems) { this.lowStockItems = lowStockItems; }
    public List<ProductResponse> getRecentlyAddedProducts() { return recentlyAddedProducts; }
    public void setRecentlyAddedProducts(List<ProductResponse> recentlyAddedProducts) { this.recentlyAddedProducts = recentlyAddedProducts; }
}
