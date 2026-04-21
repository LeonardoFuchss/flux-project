package com.leoprojects.flux.services;

import com.leoprojects.flux.domain.transaction.Transaction;
import com.leoprojects.flux.domain.user.User;
import com.leoprojects.flux.dto.transaction.TransactionRequestDto;
import com.leoprojects.flux.dto.transaction.TransactionResponseDto;
import com.leoprojects.flux.dto.transaction.TransactionUpdateDto;
import com.leoprojects.flux.exceptions.FluxException;
import com.leoprojects.flux.mapper.TransactionMapper;
import com.leoprojects.flux.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final TransactionMapper mapper;
    private final AuthenticatedUserService authUserService;

    @Transactional
    public void registerTransaction(TransactionRequestDto dto) {
        log.debug("Persisting new transaction...");
        Transaction transaction = mapper.toEntity(dto);
        log.debug("Defining Transaction User...");
        transaction.setUser(authUserService.getAuthenticatedUser());
        transaction.setCreatedAt(LocalDateTime.now());
        Transaction saved = repository.save(transaction);
        log.info("Transfer saved successfully! transactionId={} userId={}", saved.getId(), saved.getUser().getId());
    }
    public BigDecimal totalIncome(int month, int year) {
        log.debug("Seeking total user income...");
        User user = authUserService.getAuthenticatedUser();
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.plusMonths(1).atDay(1);
        return repository.sumIncomeInPeriodForUser(user.getId(), start, end);
    }
    public BigDecimal totalExpense(int month, int year) {
        log.debug("Seeking total user expense...");
        User user = authUserService.getAuthenticatedUser();
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.plusMonths(1).atDay(1);
        return repository.sumExpenseInPeriodForUser(user.getId(), start, end);
    }
    public BigDecimal currentBalance(int month, int year) {
        log.debug("Seeking total user Balance...");
        BigDecimal totalIncome = this.totalIncome(month, year);
        BigDecimal totalExpense = this.totalExpense(month, year);
        totalIncome = (totalIncome != null) ? totalIncome : BigDecimal.ZERO;
        totalExpense = (totalExpense != null) ? totalExpense : BigDecimal.ZERO;

        return totalIncome.subtract(totalExpense);
    }

    public List<TransactionResponseDto> findAllByUser(int month, int year) {
        log.debug("Fetching all user transactions...");
        User user = authUserService.getAuthenticatedUser();
        YearMonth yearMonth = YearMonth.of(year, month);
        List<Transaction> transactions = repository.findAllByUserAndPeriod(user.getId(), yearMonth.atDay(1), yearMonth.plusMonths(1).atDay(1));

        return transactions.stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    public void updateTransaction(TransactionUpdateDto dto, Long id) {
        log.debug("Updating User Data...");
        Transaction transaction = getValidatedTransaction(id);
        transaction.updateTransaction(dto);
        log.info("User data updated successfully!");
    }
    @Transactional
    public void deleteTransaction(Long id) {
        log.debug("Deleting transaction...");
        Transaction transaction = getValidatedTransaction(id);
        repository.delete(transaction);
        log.info("Transaction deleted successfully!");
    }

    private Transaction getValidatedTransaction(Long id) {
        Transaction transaction = repository.findById(id).orElseThrow(() -> new FluxException("Transaction not found."));
        if (!transaction.getUser().getId().equals(authUserService.getAuthenticatedUser().getId())) {
            throw new FluxException("You are not allowed to perform this operation.");
        }
        return transaction;
    }
}
