package com.leoprojects.flux.services;

import com.leoprojects.flux.domain.transaction.Transaction;
import com.leoprojects.flux.dto.TransactionRecordDTO;
import com.leoprojects.flux.mapper.TransactionMapper;
import com.leoprojects.flux.repository.TransactionRepository;
import com.leoprojects.flux.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository incomeRepository;
    private final TransactionMapper mapper;
    private final UserRepository userRepository;

    public void registerIncome(TransactionRecordDTO dto) {
        Transaction transaction = mapper.dtoToTransaction(dto);
        transaction.setUser(Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()), userRepository);
        incomeRepository.save(transaction);
    }
}
