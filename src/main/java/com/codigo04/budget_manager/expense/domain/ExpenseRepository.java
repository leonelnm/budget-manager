package com.codigo04.budget_manager.expense.domain;

import org.springframework.data.repository.ListCrudRepository;

public interface ExpenseRepository extends ListCrudRepository<Expense, Long> {

}
