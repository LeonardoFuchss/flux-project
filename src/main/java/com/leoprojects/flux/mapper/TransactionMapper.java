package com.leoprojects.flux.mapper;

import com.leoprojects.flux.domain.transaction.Category;
import com.leoprojects.flux.domain.transaction.Transaction;
import com.leoprojects.flux.domain.transaction.TransactionType;
import com.leoprojects.flux.dto.transaction.TransactionRequestDto;
import com.leoprojects.flux.dto.transaction.TransactionResponseDto;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public Transaction toEntity(TransactionRequestDto dto) {

        return Transaction.builder()
                .description(dto.description())
                .date(dto.date())
                .category(Category.from(dto.category()))
                .amount(dto.amount())
                .type(TransactionType.from(dto.type()))
                .build();
    }
    public TransactionResponseDto toDto(Transaction entity) {

        return TransactionResponseDto.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .amount(entity.getAmount())
                .date(entity.getDate())
                .type(entity.getType().name())
                .category(entity.getCategory().name())
                .build();
    }
}
