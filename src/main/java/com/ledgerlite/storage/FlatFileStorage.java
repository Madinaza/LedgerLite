package com.ledgerlite.storage;

import com.ledgerlite.model.Entry;
import com.ledgerlite.model.Transaction;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public final class FlatFileStorage {

    private final Path file;

    public FlatFileStorage(Path file) { this.file = file; }

    public List<Transaction> load() {
        if (Files.notExists(file)) return List.of();
        try {
            List<Transaction> out = new ArrayList<>();
            for (String line : Files.readAllLines(file)) {
                if (line.isBlank()) continue;
                String[] head = line.split("\\|", 3);
                LocalDate date = LocalDate.parse(head[0]);
                String narr = head[1];
                List<Entry> legs = new ArrayList<>();
                for (String part : head[2].split("\\|")) {
                    String[] kv = part.split(":");
                    legs.add(new Entry(kv[0].trim(),
                            new BigDecimal(kv[1].trim())));
                }
                out.add(new Transaction(date, narr, legs));
            }
            return out;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void save(List<Transaction> txs) {
        List<String> lines = new ArrayList<>();
        for (Transaction t : txs) {
            StringBuilder sb = new StringBuilder();
            sb.append(t.date()).append('|').append(t.narration()).append('|');
            t.entries().forEach(e ->
                    sb.append(e.account()).append(": ").append(e.amount()).append('|'));
            lines.add(sb.toString());
        }
        try {
            Files.createDirectories(file.getParent());
            Files.write(file, lines);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
