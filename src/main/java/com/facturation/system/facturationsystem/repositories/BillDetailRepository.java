package com.facturation.system.facturationsystem.repositories;

import com.facturation.system.facturationsystem.models.BillDetailModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetailModel, Long> {
    List<BillDetailModel> findByBill_Id(Long billId);
}
