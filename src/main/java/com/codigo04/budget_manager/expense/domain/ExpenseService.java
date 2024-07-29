package com.codigo04.budget_manager.expense.domain;

import com.codigo04.budget_manager.expense.infra.ExpenseDTO;

import java.util.List;

public interface ExpenseService {
    List<ExpenseDTO> getAllExpenses();

    ExpenseDTO getExpenseById(Long id);

    ExpenseDTO createExpense(ExpenseDTO expenseDto);

    ExpenseDTO updateExpense(Long id, ExpenseDTO expenseDto);
}
