package com.leoprojects.flux.domain.transaction;

import com.leoprojects.flux.domain.user.User;
import com.leoprojects.flux.dto.TransactionUpdateDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String description;
    private BigDecimal amount;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private TransactionType type;


    public void updateTransaction(TransactionUpdateDto dto) {
        if (dto.description() != null) {
            this.description = dto.description();
        }
        if (dto.amount() != null) {
            this.amount = dto.amount();
        }
        if (dto.date() != null) {
            this.date = dto.date();
        }
        if (dto.category() != null) {
            this.category = Category.from(dto.category());
        }
        if (dto.type() != null) {
            this.type = TransactionType.from(dto.type());
        }
    }
}
