package com.ledgerlite.controller;

import com.ledgerlite.dto.TransactionDto;
import com.ledgerlite.entity.TransactionEntity;
import com.ledgerlite.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService svc;

    public TransactionController(TransactionService svc) {
        this.svc = svc;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Validated TransactionDto dto) {
        var id = svc.create(dto);
        return ResponseEntity.ok(id);
    }

    @GetMapping
    public List<TransactionEntity> list() {
        return svc.listAll();
    }
}