package com.bio7.category.service;

import com.bio7.category.dto.request.CategoryRequest;

import com.bio7.category.dto.response.CategoryResponse;
import com.bio7.category.entity.Category;
import com.bio7.category.mapper.CategoryMapper;
import com.bio7.category.repository.CategoryRepository;
import com.bio7.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryResponse create(CategoryRequest request) {

        if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new IllegalArgumentException(
                    "Une catégorie avec ce nom existe déjà"
            );
        }

        Category category = categoryMapper.toEntity(request);

        return categoryMapper.toResponse(
                categoryRepository.save(category)
        );
    }

    public List<CategoryResponse> findAll() {

        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    public CategoryResponse findById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Catégorie introuvable avec l'id : " + id
                        )
                );

        return categoryMapper.toResponse(category);
    }

    public CategoryResponse update(
            Long id,
            CategoryRequest request) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Catégorie introuvable avec l'id : " + id
                        )
                );

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        return categoryMapper.toResponse(
                categoryRepository.save(category)
        );
    }

    public void delete(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Catégorie introuvable avec l'id : " + id
                        )
                );

        category.setActive(false);

        categoryRepository.save(category);
    }
}
