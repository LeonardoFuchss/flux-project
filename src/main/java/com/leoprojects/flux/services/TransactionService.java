package com.leoprojects.flux.services;

import com.leoprojects.flux.domain.transaction.Transaction;
import com.leoprojects.flux.domain.user.User;
import com.leoprojects.flux.dto.TransactionRequestDto;
import com.leoprojects.flux.dto.TransactionResponseDto;
import com.leoprojects.flux.dto.TransactionUpdateDto;
import com.leoprojects.flux.exceptions.FluxException;
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

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final TransactionMapper mapper;
    private final AuthenticatedUserService authUserService;

    @Transactional
    public void registerTransaction(TransactionRequestDto dto) {
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
    public void updateTransaction(TransactionUpdateDto dto, Long id) {
        Transaction transaction = getValidatedTransaction(id);
        transaction.updateTransaction(dto);
    }
    @Transactional
    public void deleteTransaction(Long id) {
        Transaction transaction = getValidatedTransaction(id);
        repository.delete(transaction);
    }

    private Transaction getValidatedTransaction(Long id) {
        Transaction transaction = repository.findById(id).orElseThrow(() -> new FluxException("Transaction not found."));
        if (!transaction.getUser().getId().equals(authUserService.getAuthenticatedUser().getId())) {
            throw new FluxException("Authenticated user does not match transaction user");
        }
        return transaction;
    }
}
