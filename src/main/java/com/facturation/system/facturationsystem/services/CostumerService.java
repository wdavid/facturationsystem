package com.facturation.system.facturationsystem.services;

import com.facturation.system.facturationsystem.models.CostumerModel;
import com.facturation.system.facturationsystem.repositories.CostumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CostumerService {

    private final CostumerRepository costumerRepository;

    @Autowired
    public CostumerService(CostumerRepository costumerRepository) {
        this.costumerRepository = costumerRepository;
    }

    public CostumerModel saveCostumer(CostumerModel costumer) {
        // Validación de datos únicos
        if (costumerRepository.existsByDui(costumer.getDui())) {
            throw new RuntimeException("El DUI ya está registrado");
        }
        if (costumerRepository.existsByEmail(costumer.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        return costumerRepository.save(costumer);
    }

    public List<CostumerModel> getAllCostumers() {
        return costumerRepository.findAll();
    }

    public Optional<CostumerModel> getCostumerById(Long id) {
        return costumerRepository.findById(id);
    }

    public CostumerModel updateCostumer(Long id, CostumerModel costumerDetails) {
        return costumerRepository.findById(id)
                .map(existingCostumer -> {
                    existingCostumer.setName(costumerDetails.getName());
                    existingCostumer.setLastName(costumerDetails.getLastName());
                    existingCostumer.setAddress(costumerDetails.getAddress());
                    existingCostumer.setPhoneNumber(costumerDetails.getPhoneNumber());
                    existingCostumer.setEmail(costumerDetails.getEmail());
                    // Nota: DUI no debería ser modificable
                    return costumerRepository.save(existingCostumer);
                })
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    public void deleteCostumer(Long id) {
        costumerRepository.deleteById(id);
    }
}
