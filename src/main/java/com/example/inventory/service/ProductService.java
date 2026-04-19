package com.example.inventory.service;

import com.example.inventory.dto.ProductRequest;
import com.example.inventory.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse create(ProductRequest request);
    ProductResponse update(Long id, ProductRequest request);
    void delete(Long id);
    ProductResponse getById(Long id);
    List<ProductResponse> getAll();
    List<ProductResponse> getByCategory(String category);
    List<ProductResponse> getLowStockItems();
}
