package com.facturation.system.facturationsystem.controllers;

import com.facturation.system.facturationsystem.dtos.BillDetailResponse;
import com.facturation.system.facturationsystem.dtos.BillRequest;
import com.facturation.system.facturationsystem.dtos.BillResponse;
import com.facturation.system.facturationsystem.models.BillModel;
import com.facturation.system.facturationsystem.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping
    public ResponseEntity<?> createBill(@RequestBody BillRequest request) {
        try {
            BillModel bill = billService.createBill(request);
            return ResponseEntity.ok(mapToResponse(bill));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<BillResponse>> getAllBills() {
        List<BillResponse> bills = billService.getAllBills().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bills);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillResponse> getBillById(@PathVariable Long id) {
        return billService.getBillById(id)
                .map(this::mapToResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/costumer/{costumerId}")
    public ResponseEntity<List<BillResponse>> getBillsByCostumer(@PathVariable Long costumerId) {
        List<BillResponse> bills = billService.getBillsByCostumer(costumerId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bills);
    }

    private BillResponse mapToResponse(BillModel bill) {
        BillResponse response = new BillResponse();
        response.setId(bill.getId());
        response.setBillNumber(bill.getBillNumber());
        response.setDate(bill.getDate());
        response.setCostumerId(bill.getCostumer().getId());
        response.setCostumerName(bill.getCostumer().getName() + " " + bill.getCostumer().getLastName());
        response.setTotal(bill.getTotal());

        // Mapear detalles si existen
        if (bill.getDetail() != null && !bill.getDetail().isEmpty()) {
            response.setDetails(bill.getDetail().stream()
                    .map(detail -> {
                        BillDetailResponse detailResponse = new BillDetailResponse();
                        detailResponse.setId(detail.getId());
                        detailResponse.setProductId(detail.getProduct().getId());
                        detailResponse.setProductCode(detail.getProduct().getCode());
                        detailResponse.setProductName(detail.getProduct().getName());
                        detailResponse.setQuantity(detail.getQuantity());
                        detailResponse.setUnitPrice(detail.getUnitPrice());
                        detailResponse.setSubtotal(detail.getSubtotal());
                        return detailResponse;
                    })
                    .collect(Collectors.toList()));
        }

        return response;
    }
}
