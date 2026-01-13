package com.leoprojects.flux.mapper;

import com.leoprojects.flux.domain.transaction.Category;
import com.leoprojects.flux.domain.transaction.Transaction;
import com.leoprojects.flux.domain.transaction.TransactionType;
import com.leoprojects.flux.dto.TransactionRequestDto;
import com.leoprojects.flux.dto.TransactionResponseDto;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public Transaction dtoRequestToTransaction(TransactionRequestDto dto) {

        return Transaction.builder()
                .description(dto.description())
                .date(dto.date())
                .category(Category.from(dto.category()))
                .amount(dto.amount())
                .type(TransactionType.from(dto.type()))
                .build();
    }

    public TransactionRequestDto transactionToDtoRequest(Transaction entity) {

        return TransactionRequestDto.builder()
                .description(entity.getDescription())
                .amount(entity.getAmount())
                .date(entity.getDate())
                .category(entity.getCategory().name())
                .type(entity.getType().name())
                .build();

    }
    public TransactionResponseDto transactionToDtoResponse(Transaction entity) {

        return TransactionResponseDto.builder()
                .description(entity.getDescription())
                .amount(entity.getAmount())
                .date(entity.getDate())
                .type(entity.getType().name())
                .category(entity.getCategory().name())
                .build();
    }
}
