package com.facturation.system.facturationsystem.services;

import com.facturation.system.facturationsystem.dtos.BillDetailRequest;
import com.facturation.system.facturationsystem.dtos.BillRequest;
import com.facturation.system.facturationsystem.models.*;
import com.facturation.system.facturationsystem.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final CostumerRepository costumerRepository;
    private final ProductRepository productRepository;
    private final BillDetailRepository billDetailRepository;

    @Autowired
    public BillService(BillRepository billRepository,
                       CostumerRepository costumerRepository,
                       ProductRepository productRepository,
                       BillDetailRepository billDetailRepository) {
        this.billRepository = billRepository;
        this.costumerRepository = costumerRepository;
        this.productRepository = productRepository;
        this.billDetailRepository = billDetailRepository;
    }

    @Transactional
    public BillModel createBill(BillRequest request) {
        // 1. Validar y cargar cliente
        CostumerModel costumer = costumerRepository.findById(request.getCostumerId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // 2. Crear factura base
        BillModel bill = new BillModel();
        bill.setBillNumber(generateBillNumber());
        bill.setDate(LocalDateTime.now());
        bill.setCostumer(costumer);
        bill.setTotal(BigDecimal.ZERO);

        // 3. Guardar factura primero para obtener ID
        BillModel savedBill = billRepository.save(bill);

        // 4. Procesar detalles
        List<BillDetailModel> details = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (BillDetailRequest detailRequest : request.getDetails()) {
            ProductModel product = productRepository.findById(detailRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + detailRequest.getProductId()));

            // Validar stock
            if (product.getStock() < detailRequest.getQuantity()) {
                throw new RuntimeException("Stock insuficiente para: " + product.getName());
            }

            // Crear detalle
            BillDetailModel detail = new BillDetailModel();
            detail.setBill(savedBill);
            detail.setProduct(product);
            detail.setQuantity(detailRequest.getQuantity());
            detail.setUnitPrice(product.getPrice());
            detail.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(detailRequest.getQuantity())));

            // Guardar detalle
            BillDetailModel savedDetail = billDetailRepository.save(detail);
            details.add(savedDetail);

            // Actualizar total
            total = total.add(detail.getSubtotal());

            // Actualizar stock
            product.setStock(product.getStock() - detailRequest.getQuantity());
            productRepository.save(product);
        }

        // 5. Actualizar y guardar factura con detalles y total
        savedBill.setDetail(details);
        savedBill.setTotal(total);
        return billRepository.save(savedBill);
    }

    public List<BillModel> getAllBills() {
        return billRepository.findAll();
    }

    public Optional<BillModel> getBillById(Long id) {
        return billRepository.findById(id);
    }

    public List<BillModel> getBillsByCostumer(Long costumerId) {
        return billRepository.findByCostumerId(costumerId);
    }

    private String generateBillNumber() {
        return "FAC-" + LocalDateTime.now().getYear() + "-" +
                UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
