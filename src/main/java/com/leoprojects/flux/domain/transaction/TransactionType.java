package com.leoprojects.flux.domain.transaction;

public enum TransactionType {
    INCOME,
    EXPENSE;

    public static TransactionType from(String value) {
        for (TransactionType type : values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown transaction type" + value);
    }
}
