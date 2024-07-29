package com.codigo04.budget_manager.expense.infra;

import com.codigo04.budget_manager.commons.mapper.SimpleMapper;
import com.codigo04.budget_manager.expense.domain.Expense;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExpenseMapper extends SimpleMapper<ExpenseDTO, Expense> {


}
