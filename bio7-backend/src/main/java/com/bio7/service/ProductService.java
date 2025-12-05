package com.bio7.service;
import com.bio7.model.Product;
import com.bio7.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository ;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product newProduct) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        existing.setName(newProduct.getName());
        existing.setDescription(newProduct.getDescription());
        existing.setPrice(newProduct.getPrice());
        existing.setImageUrl(newProduct.getImageUrl());
        existing.setCategory(newProduct.getCategory());
        existing.setBadge(newProduct.getBadge());
        existing.setStock(newProduct.getStock());

        return productRepository.save(existing);
    }

    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id)) {
            throw new RuntimeException("Produit introuvable");
        }
        productRepository.deleteById(id);
    }

}
