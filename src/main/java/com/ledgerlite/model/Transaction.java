package com.ledgerlite.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class Transaction {

    private final UUID id;
    private final LocalDate date;
    private final String narration;
    private final List<Entry> entries;

    public Transaction(LocalDate date, String narration, List<Entry> entries) {
        this(UUID.randomUUID(), date, narration, entries);
    }

    private Transaction(UUID id, LocalDate date, String narration, List<Entry> entries) {
        this.id = Objects.requireNonNull(id);
        this.date = Objects.requireNonNull(date);
        this.narration = narration == null ? "" : narration.trim();
        this.entries = List.copyOf(entries);
        ensureBalanced();
    }

    private void ensureBalanced() {
        BigDecimal sum = entries.stream()
                .map(Entry::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (sum.compareTo(BigDecimal.ZERO) != 0)
            throw new IllegalArgumentException("Transaction not balanced: " + sum);
    }

    public UUID id()          { return id; }
    public LocalDate date()   { return date; }
    public String narration() { return narration; }
    public List<Entry> entries() { return entries; }
}
