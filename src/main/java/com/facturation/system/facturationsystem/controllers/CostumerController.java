package com.facturation.system.facturationsystem.controllers;

import com.facturation.system.facturationsystem.models.CostumerModel;
import com.facturation.system.facturationsystem.services.CostumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/costumers")
public class CostumerController {

    private final CostumerService costumerService;

    @Autowired
    public CostumerController(CostumerService costumerService) {
        this.costumerService = costumerService;
    }

    @PostMapping
    public ResponseEntity<?> createCostumer(@RequestBody CostumerModel costumer) {
        try {
            CostumerModel savedCostumer = costumerService.saveCostumer(costumer);
            return ResponseEntity.ok(savedCostumer);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<CostumerModel>> getAllCostumers() {
        return ResponseEntity.ok(costumerService.getAllCostumers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CostumerModel> getCostumerById(@PathVariable Long id) {
        return costumerService.getCostumerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCostumer(@PathVariable Long id, @RequestBody CostumerModel costumerDetails) {
        try {
            CostumerModel updatedCostumer = costumerService.updateCostumer(id, costumerDetails);
            return ResponseEntity.ok(updatedCostumer);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCostumer(@PathVariable Long id) {
        costumerService.deleteCostumer(id);
        return ResponseEntity.noContent().build();
    }
}
