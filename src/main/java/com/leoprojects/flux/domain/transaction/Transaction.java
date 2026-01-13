package com.leoprojects.flux.domain.transaction;

import com.leoprojects.flux.domain.user.User;
import com.leoprojects.flux.dto.TransactionRequestDto;
import com.leoprojects.flux.repository.UserRepository;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.Authentication;

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


    public void updateTransaction(TransactionRequestDto dto) {
        this.description = dto.description();
        this.amount = dto.amount();
        this.date = dto.date();
        this.category = Category.from(dto.category());
        this.type = TransactionType.from(dto.type());
    }
}
