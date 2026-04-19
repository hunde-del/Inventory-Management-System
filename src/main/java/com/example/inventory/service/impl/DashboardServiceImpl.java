package com.example.inventory.service.impl;

import com.example.inventory.dto.DashboardResponse;
import com.example.inventory.dto.ProductResponse;
import com.example.inventory.entity.Product;
import com.example.inventory.repository.ProductRepository;
import com.example.inventory.service.DashboardService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final ProductRepository productRepository;

    @Value("${inventory.low-stock-threshold:10}")
    private int lowStockThreshold;

    public DashboardServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public DashboardResponse getDashboard() {
        DashboardResponse response = new DashboardResponse();
        response.setTotalProducts(productRepository.count());
        response.setLowStockItems(productRepository.findByQuantityLessThan(lowStockThreshold).size());
        List<ProductResponse> recent = productRepository.findTop5ByOrderByCreatedAtDesc()
                .stream()
                .map(this::toProductResponse)
                .toList();
        response.setRecentlyAddedProducts(recent);
        return response;
    }

    private ProductResponse toProductResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setCategory(product.getCategory());
        response.setQuantity(product.getQuantity());
        response.setPrice(product.getPrice());
        response.setCreatedAt(product.getCreatedAt());
        response.setLowStock(product.getQuantity() < lowStockThreshold);
        return response;
    }
}
