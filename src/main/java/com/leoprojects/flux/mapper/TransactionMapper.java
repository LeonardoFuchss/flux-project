package com.leoprojects.flux.mapper;

import com.leoprojects.flux.domain.transaction.Category;
import com.leoprojects.flux.domain.transaction.Transaction;
import com.leoprojects.flux.domain.transaction.TransactionType;
import com.leoprojects.flux.dto.TransactionRecordDTO;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public Transaction dtoToTransaction(TransactionRecordDTO dto) {

        return Transaction.builder()
                .title(dto.title())
                .description(dto.description())
                .date(dto.date())
                .category(Category.from(dto.category()))
                .amount(dto.amount())
                .type(TransactionType.from(dto.type()))
                .build();
    }

    public TransactionRecordDTO incomeToDto(Transaction entity) {

        return TransactionRecordDTO.builder()
                .title(entity.getTitle())
                .description(entity.getDescription())
                .amount(entity.getAmount())
                .date(entity.getDate())
                .category(entity.getCategory().name())
                .type(entity.getType().name())
                .build();

    }
}
