package com.codigo04.budget_manager.category.domain;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends ListCrudRepository<Category, Long> {

    boolean existsByName(String name);

}
