package com.facturation.system.facturationsystem.controllers;

import com.facturation.system.facturationsystem.models.CategoryModel;
import com.facturation.system.facturationsystem.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")  // Ruta base para todos los endpoints de categorías
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Crear una nueva categoría
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryModel> createCategory(@RequestBody CategoryModel category) {
        CategoryModel savedCategory = categoryService.saveCategory(category);
        return ResponseEntity.ok(savedCategory);
    }

    // Obtener todas las categorías
    @GetMapping
    public ResponseEntity<List<CategoryModel>> getAllCategories() {
        List<CategoryModel> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    // Obtener categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoryModel> getCategoryById(@PathVariable Long id) {
        Optional<CategoryModel> category = categoryService.getCategoryById(id);
        return category.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Actualizar categoría
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryModel> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryModel categoryDetails) {
        Optional<CategoryModel> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            CategoryModel existingCategory = category.get();
            existingCategory.setName(categoryDetails.getName());
            existingCategory.setDescription(categoryDetails.getDescription());
            CategoryModel updatedCategory = categoryService.saveCategory(existingCategory);
            return ResponseEntity.ok(updatedCategory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar categoría
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
