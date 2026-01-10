package com.leoprojects.flux.services;

import com.leoprojects.flux.domain.transaction.income.Income;
import com.leoprojects.flux.domain.user.User;
import com.leoprojects.flux.dto.TransactionRecordDTO;
import com.leoprojects.flux.mapper.TransactionMapper;
import com.leoprojects.flux.repository.IncomeRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class IncomeService {
    private final IncomeRepository incomeRepository;
    private final TransactionMapper mapper;

    public void registerIncome(TransactionRecordDTO dto) {
        Income income = mapper.dtoToIncome(dto);
        User user  = (User) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        income.setUser(user);
        incomeRepository.save(income);
    }
}
