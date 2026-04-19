package com.example.inventory.service.impl;

import com.example.inventory.dto.ProductResponse;
import com.example.inventory.dto.ReportResponse;
import com.example.inventory.entity.Product;
import com.example.inventory.repository.ProductRepository;
import com.example.inventory.service.ReportService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    private final ProductRepository productRepository;

    @Value("${inventory.low-stock-threshold:10}")
    private int lowStockThreshold;

    public ReportServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ReportResponse inventoryReport(String category) {
        List<Product> products = (category == null || category.isBlank())
                ? productRepository.findAll()
                : productRepository.findByCategoryIgnoreCase(category);

        List<ProductResponse> items = products.stream().map(this::toResponse).toList();

        BigDecimal totalValue = products.stream()
                .map(p -> p.getPrice().multiply(BigDecimal.valueOf(p.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        ReportResponse response = new ReportResponse();
        response.setItems(items);
        response.setTotalItems(products.stream().mapToLong(Product::getQuantity).sum());
        response.setTotalValue(totalValue);
        return response;
    }

    private ProductResponse toResponse(Product product) {
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
