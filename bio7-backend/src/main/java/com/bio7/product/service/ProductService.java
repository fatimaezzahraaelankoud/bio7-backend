package com.bio7.product.service;

import com.bio7.common.exception.ResourceNotFoundException;
import com.bio7.product.dto.request.ProductRequest;
import com.bio7.product.dto.response.ProductResponse;
import com.bio7.product.entity.Product;
import com.bio7.product.mapper.ProductMapper;
import com.bio7.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository ;
    private final ProductMapper productMapper ;


    public ProductResponse create(ProductRequest request){
        if(productRepository.existsByNameIgnoreCase(request.getName())){
            throw new IllegalArgumentException("Un produit avec ce nom existe déjà");
        }

        Product product = productMapper.toEntity(request);
        Product productSaved= productRepository.save(product);

        return productMapper.toResponseDTO(productSaved);
    }



    public List<ProductResponse> findAll(){
        return productRepository.findByActiveTrue()
                .stream()
                .map(productMapper::toResponseDTO).toList();
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

        Product productUpdated = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .badge(productMapper.parseBadge(request.getBadge()))
                .stock(request.getStock())
                .imageUrl(request.getImageUrl())
                .price(request.getPrice())
                .build();
        Product productSaved=productRepository.save(productUpdated);
        return productMapper.toResponseDTO(productSaved);
    }



    //delete or archive product(make te product availability false)
    public void delete(Long id){

        Product product=productRepository.findById(id)
                .orElseThrow(
                        ()-> new ResourceNotFoundException(
                                "Produit introuvable avec l'id : " + id));

        product.setActive(false);
        productRepository.save(product);
    }

}
