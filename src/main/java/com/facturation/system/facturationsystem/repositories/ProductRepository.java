package com.facturation.system.facturationsystem.repositories;

import com.facturation.system.facturationsystem.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    Optional<ProductModel> findByCode(String code);
    boolean existsByCode(String code);
    List<ProductModel> findByCategory_Id(Long categoryId);
}
