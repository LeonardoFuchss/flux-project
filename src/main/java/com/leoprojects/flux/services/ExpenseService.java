package com.leoprojects.flux.services;

import com.leoprojects.flux.domain.transaction.expense.Expense;
import com.leoprojects.flux.domain.user.User;
import com.leoprojects.flux.dto.TransactionRecordDTO;
import com.leoprojects.flux.mapper.TransactionMapper;
import com.leoprojects.flux.repository.ExpenseRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final TransactionMapper mapper;

    public void registerExpense(TransactionRecordDTO dto) {
        Expense expense = mapper.dtoToExpense(dto);
        User user  = (User) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        expense.setUser(user);
        expenseRepository.save(expense);
    }
}