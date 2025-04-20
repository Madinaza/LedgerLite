package com.ledgerlite.model;

import java.math.BigDecimal;
import java.util.Objects;

public record Entry(String account, BigDecimal amount) {
    public Entry {
        Objects.requireNonNull(account);
        Objects.requireNonNull(amount);
    }
}
