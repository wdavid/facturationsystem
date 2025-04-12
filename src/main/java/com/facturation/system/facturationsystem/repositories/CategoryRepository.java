package com.facturation.system.facturationsystem.repositories;

import com.facturation.system.facturationsystem.models.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {
    Optional<CategoryModel> findByName(String name);
}
