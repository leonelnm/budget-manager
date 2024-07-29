package com.codigo04.budget_manager.category.infra;

import com.codigo04.budget_manager.category.domain.Category;
import com.codigo04.budget_manager.category.domain.CategoryRepository;
import com.codigo04.budget_manager.category.domain.CategoryService;
import com.codigo04.budget_manager.commons.exception.EntityNotFoundException;
import com.codigo04.budget_manager.commons.exception.UniqueConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CategoryServiceTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Category1");
        category1.setDescription("Descripcion1");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Category2");
        category2.setDescription("Descripcion2");

        List<Category> categories = List.of(category1, category2);
        Mockito.when(categoryRepository.findAll()).thenReturn(categories);
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));

    }

    @Test
    public void getAllCategories() {
        List<CategoryDTO> categoryDTOs = categoryService.getAllCategories();
        Assertions.assertEquals(2, categoryDTOs.size());
        Assertions.assertEquals(1L, categoryDTOs.getFirst().id());
    }

    @Test
    public void getCategoryByIdOK() {
         CategoryDTO categoryDTO = categoryService.getCategoryById(1L);
         Assertions.assertEquals(1L, categoryDTO.id());
    }

    @Test
    public void getCategoryByIdKO() {
        EntityNotFoundException entityNotFoundException = Assertions.assertThrows(EntityNotFoundException.class, () -> categoryService.getCategoryById(2L));
        Assertions.assertEquals(CategoryErrorMessages.CATEGORY_NOT_FOUND, entityNotFoundException.getMessage());
    }

    @Test
    public void createCategoryOK() {
        Mockito.when(categoryRepository.existsByName("create")).thenReturn(false);
        Mockito.when(categoryRepository.save(Mockito.any())).thenAnswer(invocation -> {
            Category category = invocation.getArgument(0);
            category.setId(1L);
            return category;
        });

        CategoryDTO categoryDTO = categoryService.createCategory(new CategoryDTO(null, "create", "create description"));
        Assertions.assertEquals("create", categoryDTO.name());
        Assertions.assertEquals("create description", categoryDTO.description());
    }

    @Test
    public void createCategoryKO() {
        Mockito.when(categoryRepository.existsByName("create")).thenReturn(true);

        UniqueConstraintViolationException uniqueConstraintViolationException = Assertions.assertThrows(UniqueConstraintViolationException.class, () -> categoryService.createCategory(new CategoryDTO(null, "create", "create description")));
        Assertions.assertEquals(CategoryErrorMessages.CATEGORY_NAME_ALREADY_EXISTS, uniqueConstraintViolationException.getMessage());

    }

    @Test
    public void updateCategoryOK() {
        CategoryDTO categoryDTO = categoryService.updateCategory(1L, new CategoryDTO(1L, "name update", "description update"));
        Assertions.assertEquals("name update", categoryDTO.name());
        Assertions.assertEquals("description update", categoryDTO.description());
    }

    @Test
    public void updateCategoryKO() {
        EntityNotFoundException entityNotFoundException = Assertions.assertThrows(EntityNotFoundException.class, () -> categoryService.updateCategory(2L, new CategoryDTO(null, "name update", "description update")));
        Assertions.assertEquals(CategoryErrorMessages.CATEGORY_NOT_FOUND, entityNotFoundException.getMessage());
    }

}
