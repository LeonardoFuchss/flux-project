package com.leoprojects.flux.services;

import com.leoprojects.flux.domain.transaction.Transaction;
import com.leoprojects.flux.domain.user.User;
import com.leoprojects.flux.dto.TransactionRequestDto;
import com.leoprojects.flux.dto.TransactionResponseDto;
import com.leoprojects.flux.mapper.TransactionMapper;
import com.leoprojects.flux.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final TransactionMapper mapper;
    private final AuthenticatedUserService authUserService;

    @Transactional
    public void registerIncome(TransactionRequestDto dto) {
        Transaction transaction = mapper.dtoRequestToTransaction(dto);
        transaction.setUser(authUserService.getAuthenticatedUser());
        repository.save(transaction);
    }
    public BigDecimal totalIncome() {
        User user = authUserService.getAuthenticatedUser();
        LocalDate start = YearMonth.now().atDay(1);
        LocalDate end = start.plusMonths(1);
        return repository.sumIncomeInPeriodForUser(user.getId(), start, end);
    }
    public BigDecimal totalExpense() {
        User user = authUserService.getAuthenticatedUser();
        LocalDate start = YearMonth.now().atDay(1);
        LocalDate end = start.plusMonths(1);
        return repository.sumExpenseInPeriodForUser(user.getId(), start, end);
    }
    public BigDecimal currentBalance() {
        BigDecimal totalIncome = this.totalIncome();
        BigDecimal totalExpense = this.totalExpense();
        totalIncome = (totalIncome != null) ? totalIncome : BigDecimal.ZERO;
        totalExpense = (totalExpense != null) ? totalExpense : BigDecimal.ZERO;

        return totalIncome.subtract(totalExpense);
    }
    public List<TransactionResponseDto> findAllByUser() {
        List<Transaction> transactions = repository.findAllByUser(authUserService.getAuthenticatedUser());

        return transactions.stream()
                .map(mapper::transactionToDtoResponse)
                .toList();
    }

    @Transactional
    public void updateTransaction(TransactionRequestDto dto, Long id) {
        Optional<Transaction> transaction = repository.findById(id);
        transaction.ifPresent(value -> value.updateTransaction(dto));
    }
}
