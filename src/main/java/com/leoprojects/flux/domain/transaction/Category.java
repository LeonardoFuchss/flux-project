package com.leoprojects.flux.domain.transaction;

public enum Category {
    FOOD,
    TRANSPORTATION,
    HOUSING,
    HEALTH,
    EDUCATION,
    LEISURE,
    SHOPPING,
    UTILITIES,
    SALARY,
    INVESTMENTS,
    GIFT,
    OTHERS;


    public static Category from(String value) {
        for (Category category : values()) {
            if (category.name().equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown category" + value);
    }
}
