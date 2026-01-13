package com.leoprojects.flux.domain.transaction;

import com.leoprojects.flux.exceptions.FluxException;

public enum TransactionType {
    INCOME,
    EXPENSE;

    public static TransactionType from(String value) {
        for (TransactionType type : values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new FluxException("Unknown transaction type: " + value);
    }
}
