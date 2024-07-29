package com.codigo04.budget_manager.expense.app;

import com.codigo04.budget_manager.category.infra.CategoryDTO;
import com.codigo04.budget_manager.expense.domain.ExpenseService;
import com.codigo04.budget_manager.expense.infra.ExpenseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getAllCategories(){
        return ResponseEntity.ok(expenseService.getAllExpenses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDTO> getCategoryById(@PathVariable("id") Long id){
        return ResponseEntity.ok(expenseService.getExpenseById(id));
    }

    @PostMapping
    public ResponseEntity<ExpenseDTO> createCategory(@RequestBody ExpenseDTO expense){
        return ResponseEntity.ok(expenseService.createExpense(expense));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDTO> updateCategory(@PathVariable Long id, @RequestBody ExpenseDTO expense){
        return ResponseEntity.ok(expenseService.updateExpense(id,expense));
    }

}
