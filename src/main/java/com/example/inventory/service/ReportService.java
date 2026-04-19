package com.example.inventory.service;

import com.example.inventory.dto.ReportResponse;

public interface ReportService {
    ReportResponse inventoryReport(String category);
}
