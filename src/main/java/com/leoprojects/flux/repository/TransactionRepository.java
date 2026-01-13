package com.leoprojects.flux.repository;

import com.leoprojects.flux.domain.transaction.Transaction;
import com.leoprojects.flux.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("""
        select coalesce(sum(t.amount), 0)
        from Transaction t
        where t.type = com.leoprojects.flux.domain.transaction.TransactionType.INCOME
          and t.user.id = :userId
          and t.date >= :start
          and t.date < :end
    """)
    BigDecimal sumIncomeInPeriodForUser(@Param("userId") Long userId,
                                        @Param("start") LocalDate start,
                                        @Param("end") LocalDate end);

    @Query("""
        select coalesce(sum(t.amount), 0)
        from Transaction t
        where t.type = com.leoprojects.flux.domain.transaction.TransactionType.EXPENSE
          and t.user.id = :userId
          and t.date >= :start
          and t.date < :end
    """)
    BigDecimal sumExpenseInPeriodForUser(@Param("userId") Long userId,
                                        @Param("start") LocalDate start,
                                        @Param("end") LocalDate end);

    List<Transaction> findAllByUser(User user);
}
