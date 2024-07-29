package com.codigo04.budget_manager.category.infra;


import com.codigo04.budget_manager.category.domain.Category;
import com.codigo04.budget_manager.category.domain.CategoryRepository;
import com.codigo04.budget_manager.category.domain.CategoryService;
import com.codigo04.budget_manager.commons.exception.EntityNotFoundException;
import com.codigo04.budget_manager.commons.exception.UniqueConstraintViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }


    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(mapper::mapToDto).toList();
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        return mapper.mapToDto(categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CategoryErrorMessages.CATEGORY_NOT_FOUND)));
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        if(existsCategoryByName(categoryDTO.name())){
            throw new UniqueConstraintViolationException(CategoryErrorMessages.CATEGORY_NAME_ALREADY_EXISTS);
        }

        Category category = mapper.mapToEntity(categoryDTO);
        categoryRepository.save(category);
        return mapper.mapToDto(category);
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(categoryDTO.id()).orElseThrow(() -> new EntityNotFoundException(CategoryErrorMessages.CATEGORY_NOT_FOUND));
        mapper.updateEntityFromDto(categoryDTO, category);
        categoryRepository.save(category);

        return mapper.mapToDto(category);

    }

    @Override
    public boolean existsCategoryByName(String name) {
        return categoryRepository.existsByName(name);
    }

    @Override
    public boolean existsCategoryById(Long id) {
        return categoryRepository.existsById(id);
    }
}
