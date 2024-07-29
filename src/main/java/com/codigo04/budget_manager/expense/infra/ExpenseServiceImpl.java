package com.codigo04.budget_manager.expense.infra;

import com.codigo04.budget_manager.category.domain.CategoryService;
import com.codigo04.budget_manager.category.infra.CategoryErrorMessages;
import com.codigo04.budget_manager.commons.exception.EntityNotFoundException;
import com.codigo04.budget_manager.expense.domain.Expense;
import com.codigo04.budget_manager.expense.domain.ExpenseRepository;
import com.codigo04.budget_manager.expense.domain.ExpenseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper mapper;
    private final CategoryService categoryService;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, ExpenseMapper mapper, CategoryService categoryService) {
        this.expenseRepository = expenseRepository;
        this.mapper = mapper;
        this.categoryService = categoryService;
    }

    @Override
    public List<ExpenseDTO> getAllExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        return expenses.stream().map(mapper::mapToDto).toList();
    }

    @Override
    public ExpenseDTO getExpenseById(Long id) {
        return mapper.mapToDto(expenseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ExpenseErrorMessages.EXPENSE_NOT_FOUND)));
    }

    @Override
    public ExpenseDTO createExpense(ExpenseDTO expenseDto) {

        if(!categoryService.existsCategoryById(expenseDto.categoryId())){
            throw new EntityNotFoundException(CategoryErrorMessages.CATEGORY_NOT_FOUND);
        }

        Expense expenseEntity = mapper.mapToEntity(expenseDto);
        expenseRepository.save(expenseEntity);

        return mapper.mapToDto(expenseEntity);
    }

    @Override
    public ExpenseDTO updateExpense(Long id, ExpenseDTO expenseDto) {
        Expense expenseEntity = expenseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ExpenseErrorMessages.EXPENSE_NOT_FOUND));
        mapper.updateEntityFromDto(expenseDto, expenseEntity);
        expenseRepository.save(expenseEntity);

        return mapper.mapToDto(expenseEntity);
    }
}
