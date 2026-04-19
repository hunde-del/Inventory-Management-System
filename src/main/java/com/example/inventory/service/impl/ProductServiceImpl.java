package com.example.inventory.service.impl;

import com.example.inventory.dto.ProductRequest;
import com.example.inventory.dto.ProductResponse;
import com.example.inventory.entity.Product;
import com.example.inventory.exception.BadRequestException;
import com.example.inventory.exception.ResourceNotFoundException;
import com.example.inventory.repository.ProductRepository;
import com.example.inventory.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Value("${inventory.low-stock-threshold:10}")
    private int lowStockThreshold;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse create(ProductRequest request) {
        Product product = mapToEntity(request, new Product());
        return mapToResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse update(Long id, ProductRequest request) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        mapToEntity(request, existing);
        return mapToResponse(productRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(existing);
    }

    @Override
    public ProductResponse getById(Long id) {
        return productRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Override
    public List<ProductResponse> getAll() {
        return productRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<ProductResponse> getByCategory(String category) {
        return productRepository.findByCategoryIgnoreCase(category).stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<ProductResponse> getLowStockItems() {
        return productRepository.findByQuantityLessThan(lowStockThreshold).stream().map(this::mapToResponse).toList();
    }

    private Product mapToEntity(ProductRequest req, Product product) {
        if (req.getQuantity() < 0) {
            throw new BadRequestException("Quantity cannot be negative");
        }
        product.setName(req.getName().trim());
        product.setCategory(req.getCategory().trim());
        product.setQuantity(req.getQuantity());
        product.setPrice(req.getPrice());
        return product;
    }

    private ProductResponse mapToResponse(Product product) {
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
