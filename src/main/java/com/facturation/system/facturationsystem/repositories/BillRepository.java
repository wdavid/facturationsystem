package com.facturation.system.facturationsystem.repositories;

import com.facturation.system.facturationsystem.models.BillModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<BillModel, Long> {
    @EntityGraph(attributePaths = {"detail", "detail.product"})
    Optional<BillModel> findByBillNumber(String billNumber);
    boolean existsByBillNumber(String billNumber);

    @Query("SELECT b FROM BillModel b WHERE b.costumer.id = :costumerId")
    List<BillModel> findByCostumerId(Long costumerId);
}
