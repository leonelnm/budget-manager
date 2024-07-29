package com.codigo04.budget_manager.expense.infra;

import com.codigo04.budget_manager.category.domain.Category;
import com.codigo04.budget_manager.category.domain.CategoryRepository;
import com.codigo04.budget_manager.category.domain.CategoryService;
import com.codigo04.budget_manager.category.infra.CategoryErrorMessages;
import com.codigo04.budget_manager.commons.exception.EntityNotFoundException;
import com.codigo04.budget_manager.expense.domain.Expense;
import com.codigo04.budget_manager.expense.domain.ExpenseRepository;
import com.codigo04.budget_manager.expense.domain.ExpenseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ExpenseServiceTest {

    @MockBean
    private ExpenseRepository expenseRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private ExpenseService expenseService;

    @BeforeEach
    public void setUp() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Test Category");

        Expense expense1 = new Expense();
        expense1.setId(1L);
        expense1.setDate(LocalDate.of(2024, 7, 16));
        expense1.setDescription("Expense1");
        expense1.setAmount(BigDecimal.valueOf(100));
        expense1.setCategory(category1);

        Expense expense2 = new Expense();
        expense2.setId(2L);
        expense2.setDate(LocalDate.of(2024, 7, 16));
        expense2.setDescription("Expense2");
        expense2.setAmount(BigDecimal.valueOf(200));
        expense2.setCategory(category1);

        List<Expense> expenses = List.of(expense1, expense2);
        Mockito.when(expenseRepository.findAll()).thenReturn(expenses);
        Mockito.when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense1));

    }

    @Test
    public void getAllExpenses() {
        List<ExpenseDTO> expenseDTOS = expenseService.getAllExpenses();
        Assertions.assertEquals(2, expenseDTOS.size());
        Assertions.assertEquals(1L, expenseDTOS.getFirst().id());
    }

    @Test
    public void getExpenseByIdOK() {
        ExpenseDTO expenseDTO = expenseService.getExpenseById(1L);
        Assertions.assertEquals(1L, expenseDTO.id());
    }

    @Test
    public void getExpenseByIdKO() {
        EntityNotFoundException entityNotFoundException = Assertions.assertThrows(EntityNotFoundException.class, () -> expenseService.getExpenseById(2L));
        Assertions.assertEquals(ExpenseErrorMessages.EXPENSE_NOT_FOUND, entityNotFoundException.getMessage());
    }

    @Test
    public void createExpenseOK() {
        Mockito.when(categoryRepository.existsById(1L)).thenReturn(true);
        Mockito.when(expenseRepository.save(Mockito.any())).thenAnswer(invocation -> {
            Expense expense = invocation.getArgument(0);
            expense.setId(1L);
            return expense;
        });

        LocalDate localDate = LocalDate.of(2024, 7, 16);
        ExpenseDTO expenseDTO = new ExpenseDTO(null, "Nuevo Expense", BigDecimal.valueOf(100), localDate, 1L);

        ExpenseDTO newExpenseDTO = expenseService.createExpense(expenseDTO);
        Assertions.assertEquals(1L, newExpenseDTO.id());
        Assertions.assertEquals(expenseDTO.amount(), newExpenseDTO.amount());
        Assertions.assertEquals(expenseDTO.date(), newExpenseDTO.date());

    }

    @Test
    public void createExpenseKO() {
        Mockito.when(categoryRepository.existsById(1L)).thenReturn(false);
        LocalDate localDate = LocalDate.of(2024, 7, 16);
        ExpenseDTO expenseDTO = new ExpenseDTO(null, "Nuevo Expense", BigDecimal.valueOf(100), localDate, 1L);

        EntityNotFoundException entityNotFoundException = Assertions.assertThrows(EntityNotFoundException.class, () -> expenseService.createExpense(expenseDTO));
        Assertions.assertEquals(CategoryErrorMessages.CATEGORY_NOT_FOUND, entityNotFoundException.getMessage());
    }

    @Test
    public void updateExpenseOK() {
        ExpenseDTO expenseDTO = expenseService.updateExpense(1L, new ExpenseDTO(1L, "name update", BigDecimal.valueOf(100), LocalDate.of(2024, 7, 16), 1L));
        Assertions.assertEquals("name update", expenseDTO.description());
    }

    @Test
    public void updateExpenseKO() {
        EntityNotFoundException entityNotFoundException = Assertions.assertThrows(EntityNotFoundException.class, () -> expenseService.updateExpense(2L, new ExpenseDTO(null, "name update", BigDecimal.valueOf(100), LocalDate.of(2024, 7, 16), 1L)));
        Assertions.assertEquals(ExpenseErrorMessages.EXPENSE_NOT_FOUND, entityNotFoundException.getMessage());
    }

}
