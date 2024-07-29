package com.codigo04.budget_manager.category.domain;

import com.codigo04.budget_manager.category.infra.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryById(Long id);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

    boolean existsCategoryByName(String name);

    boolean existsCategoryById(Long id);
}
