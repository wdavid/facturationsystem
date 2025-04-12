package com.facturation.system.facturationsystem.repositories;

import com.facturation.system.facturationsystem.models.CostumerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CostumerRepository extends JpaRepository<CostumerModel, Long> {

    Optional<CostumerModel> findByDui(String dui);
    Optional<CostumerModel> findByEmail(String email);
    boolean existsByDui(String dui);
    boolean existsByEmail(String email);
}
