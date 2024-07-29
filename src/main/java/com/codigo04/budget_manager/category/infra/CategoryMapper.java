package com.codigo04.budget_manager.category.infra;

import com.codigo04.budget_manager.category.domain.Category;
import com.codigo04.budget_manager.commons.mapper.SimpleMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends SimpleMapper<CategoryDTO, Category> {

}
