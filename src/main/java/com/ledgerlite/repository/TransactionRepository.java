package com.ledgerlite.repository;

import com.ledgerlite.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {
    List<TransactionEntity> findByDateBetween(LocalDate start, LocalDate end);
}