package com.facturation.system.facturationsystem.services;

import com.facturation.system.facturationsystem.models.CategoryModel;
import com.facturation.system.facturationsystem.models.ProductModel;
import com.facturation.system.facturationsystem.repositories.CategoryRepository;
import com.facturation.system.facturationsystem.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public ProductModel saveProduct(ProductModel product) {
        // Validar código único
        if (productRepository.existsByCode(product.getCode())) {
            throw new RuntimeException("El código de producto ya existe");
        }

        // Validar categoría
        if (product.getCategory() == null || product.getCategory().getId() == null) {
            throw new RuntimeException("La categoría es requerida");
        }

        // Verificar existencia de categoría
        CategoryModel category = categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        product.setCategory(category);
        return productRepository.save(product);
    }

    public List<ProductModel> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<ProductModel> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<ProductModel> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategory_Id(categoryId);
    }

    @Transactional
    public ProductModel updateProduct(Long id, ProductModel productDetails) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    // No permitir modificación de código
                    if (!existingProduct.getCode().equals(productDetails.getCode())) {
                        throw new RuntimeException("El código del producto no puede ser modificado");
                    }

                    existingProduct.setName(productDetails.getName());
                    existingProduct.setDescription(productDetails.getDescription());
                    existingProduct.setPrice(productDetails.getPrice());
                    existingProduct.setStock(productDetails.getStock());

                    // Actualizar categoría si es diferente
                    if (productDetails.getCategory() != null &&
                            !existingProduct.getCategory().getId().equals(productDetails.getCategory().getId())) {

                        CategoryModel newCategory = categoryRepository.findById(productDetails.getCategory().getId())
                                .orElseThrow(() -> new RuntimeException("Nueva categoría no encontrada"));
                        existingProduct.setCategory(newCategory);
                    }

                    return productRepository.save(existingProduct);
                })
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @Transactional
    public void updateStock(Long productId, Integer quantity) {
        ProductModel product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        int newStock = product.getStock() + quantity;
        if (newStock < 0) {
            throw new RuntimeException("Stock no puede ser negativo");
        }

        product.setStock(newStock);
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
