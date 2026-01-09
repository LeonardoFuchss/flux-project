package com.leoprojects.flux.domain.transaction.income;

import com.leoprojects.flux.domain.transaction.Transaction;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Income extends Transaction {

}
