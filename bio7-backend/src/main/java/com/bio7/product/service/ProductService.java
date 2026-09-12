package com.bio7.product.service;

import com.bio7.category.entity.Category;
import com.bio7.category.repository.CategoryRepository;
import com.bio7.common.exception.ResourceNotFoundException;
import com.bio7.product.dto.request.ProductRequest;
import com.bio7.product.dto.response.ProductResponse;
import com.bio7.product.entity.Product;
import com.bio7.product.mapper.ProductMapper;
import com.bio7.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository ;
    private final ProductMapper productMapper ;
    private final CategoryRepository categoryRepository ;


    public ProductResponse create(ProductRequest request){
        if(productRepository.existsByNameIgnoreCase(request.getName())){
            throw new IllegalArgumentException("Un produit avec ce nom existe déjà");
        }
        Category category = categoryRepository.findById(
                request.getCategoryId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Catégorie introuvable avec l'id : "
                                + request.getCategoryId()
                )
        );
        Product product = productMapper.toEntity(request,category);
        Product productSaved= productRepository.save(product);

        return productMapper.toResponseDTO(productSaved);
    }



    public Page<ProductResponse> findAll(String search ,
                                         Long categoryId,
                                         Pageable pageable){

        String normalizedSearch =
                (search == null || search.isBlank())
                        ? null
                        : search.trim();
        return productRepository.search(normalizedSearch,categoryId,pageable)
                .map(productMapper::toResponseDTO);
    }



    public ProductResponse findById(Long id){
        Product product=productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Produit introuvable avec l'id : " + id));

        return productMapper.toResponseDTO(product);

    }



    public ProductResponse update(Long id ,ProductRequest request){
        Product product=productRepository.findById(id)
                .orElseThrow(
                        ()-> new ResourceNotFoundException(
                                "Produit introuvable avec l'id : " + id));

        Category category = categoryRepository.findById(
                request.getCategoryId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Catégorie introuvable avec l'id : "
                                + request.getCategoryId()
                )
        );

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCategory(category);
        product.setBadge(productMapper.parseBadge(request.getBadge()));
        product.setStock(request.getStock());
        product.setImageUrl(request.getImageUrl());
        product.setPrice(request.getPrice());

        Product productSaved=productRepository.save(product);
        return productMapper.toResponseDTO(productSaved);
    }



    //delete : archive product(make the product availability false)
    public void delete(Long id){

        Product product=productRepository.findById(id)
                .orElseThrow(
                        ()-> new ResourceNotFoundException(
                                "Produit introuvable avec l'id : " + id));

        product.setActive(false);
        productRepository.save(product);
    }

}
