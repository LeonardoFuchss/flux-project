package com.leoprojects.flux.domain.transaction.income;

import com.leoprojects.flux.domain.transaction.Transaction;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Income extends Transaction {

}
