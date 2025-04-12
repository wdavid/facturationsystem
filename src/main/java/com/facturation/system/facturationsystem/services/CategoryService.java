package com.facturation.system.facturationsystem.services;

import com.facturation.system.facturationsystem.models.CategoryModel;
import com.facturation.system.facturationsystem.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Crear o actualizar categoría
    public CategoryModel saveCategory(CategoryModel category) {
        return categoryRepository.save(category);
    }

    // Obtener todas las categorías
    public List<CategoryModel> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Obtener categoría por ID
    public Optional<CategoryModel> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    // Eliminar categoría
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
