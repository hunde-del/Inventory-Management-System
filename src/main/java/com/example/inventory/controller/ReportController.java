package com.example.inventory.controller;

import com.example.inventory.dto.ReportResponse;
import com.example.inventory.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/inventory")
    public ResponseEntity<ReportResponse> inventory(@RequestParam(required = false) String category) {
        return ResponseEntity.ok(reportService.inventoryReport(category));
    }
}
