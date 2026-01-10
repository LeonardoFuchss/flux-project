package com.leoprojects.flux.mapper;

import com.leoprojects.flux.domain.transaction.Category;
import com.leoprojects.flux.domain.transaction.Transaction;
import com.leoprojects.flux.domain.transaction.expense.Expense;
import com.leoprojects.flux.domain.transaction.income.Income;
import com.leoprojects.flux.dto.TransactionRecordDTO;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public Income dtoToIncome(TransactionRecordDTO dto) {

        return Income.builder()
                .title(dto.title())
                .description(dto.description())
                .date(dto.date())
                .category(Category.from(dto.category()))
                .amount(dto.amount())
                .build();
    }

    public TransactionRecordDTO incomeToDto(Income entity) {

        return TransactionRecordDTO.builder()
                .title(entity.getTitle())
                .description(entity.getDescription())
                .amount(entity.getAmount())
                .date(entity.getDate())
                .category(entity.getCategory().name())
                .build();

    }
    public Expense dtoToExpense(TransactionRecordDTO dto) {

        return Expense.builder()
                .title(dto.title())
                .description(dto.description())
                .date(dto.date())
                .category(Category.from(dto.category()))
                .amount(dto.amount())
                .build();
    }
    public TransactionRecordDTO expenseToDto(Expense entity) {

        return TransactionRecordDTO.builder()
                .title(entity.getTitle())
                .description(entity.getDescription())
                .amount(entity.getAmount())
                .date(entity.getDate())
                .category(entity.getCategory().name())
                .build();

    }
}
