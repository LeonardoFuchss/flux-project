package com.leoprojects.flux.domain.transaction.expense;

import com.leoprojects.flux.domain.transaction.Transaction;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Expense extends Transaction {

}
