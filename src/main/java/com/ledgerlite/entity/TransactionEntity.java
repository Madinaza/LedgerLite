package com.ledgerlite.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private LocalDate date;
    private String narration;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "transaction_entries",
            joinColumns = @JoinColumn(name = "transaction_id")
    )
    private List<EntryEmbeddable> entries = new ArrayList<>();


    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNarration() {
        return narration;
    }
    public void setNarration(String narration) {
        this.narration = narration;
    }

    public List<EntryEmbeddable> getEntries() {
        return entries;
    }
    public void setEntries(List<EntryEmbeddable> entries) {
        this.entries = entries;
    }
}
