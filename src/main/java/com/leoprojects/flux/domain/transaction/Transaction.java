package com.leoprojects.flux.domain.transaction;

import com.leoprojects.flux.domain.user.User;
import com.leoprojects.flux.dto.TransactionRecordDTO;
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
    private User user;
    private String title;
    private String description;
    private BigDecimal amount;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    public void setUser(Authentication authentication, UserRepository userRepository) {
        String login = authentication.getName();
        this.user = (User) userRepository.findByLogin(login);
    }
}
