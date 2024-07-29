package com.codigo04.budget_manager.expense.infra;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseDTO(Long id, String description, BigDecimal amount, LocalDate date, long categoryId) {
}
