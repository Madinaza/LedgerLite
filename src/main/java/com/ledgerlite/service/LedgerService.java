package com.ledgerlite.service;

import com.ledgerlite.model.Entry;
import com.ledgerlite.model.Transaction;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/** Keeps all transactions in memory and offers queries. */
public final class LedgerService {

    private final List<Transaction> txs;

    public LedgerService(List<Transaction> initial) {
        this.txs = new ArrayList<>(initial);
    }

    public void add(Transaction t) { txs.add(Objects.requireNonNull(t)); }
    public List<Transaction> all() { return List.copyOf(txs); }

    /** account → net balance map */
    public Map<String, BigDecimal> trialBalance() {
        return txs.stream()
                .flatMap(t -> t.entries().stream())
                .collect(Collectors.groupingBy(
                        Entry::account,
                        Collectors.reducing(BigDecimal.ZERO, Entry::amount, BigDecimal::add)));
    }
}
