package com.ledgerlite.service;

import com.ledgerlite.dto.TransactionDto;
import com.ledgerlite.entity.*;
import com.ledgerlite.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final TransactionRepository repo;

    public TransactionService(TransactionRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public UUID create(TransactionDto dto) {
        var tx = new TransactionEntity();
        tx.setDate(dto.date());
        tx.setNarration(dto.narration());
        tx.setEntries(dto.entries().stream()
                .map(e -> {
                    var emb = new EntryEmbeddable();
                    emb.setAccount(e.account());
                    emb.setAmount(e.amount());
                    return emb;
                }).collect(Collectors.toList()));
        // ensure balance
        var sum = tx.getEntries().stream()
                .map(EntryEmbeddable::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (sum.compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalArgumentException("Not balanced: " + sum);
        }
        return repo.save(tx).getId();
    }

    public List<TransactionEntity> listAll() {
        return repo.findAll();
    }
}