package com.leoprojects.flux.domain.transaction.expense;

import com.leoprojects.flux.domain.transaction.Transaction;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Expense extends Transaction {

}
