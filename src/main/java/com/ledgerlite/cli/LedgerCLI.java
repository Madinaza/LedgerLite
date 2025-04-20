package com.ledgerlite.cli;

import com.ledgerlite.model.Entry;
import com.ledgerlite.model.Transaction;
import com.ledgerlite.service.LedgerService;
import com.ledgerlite.service.ReportService;
import com.ledgerlite.storage.FlatFileStorage;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public final class LedgerCLI {

    private final Scanner in = new Scanner(System.in);
    private final LedgerService ledger;
    private final ReportService reports = new ReportService();
    private final FlatFileStorage storage;

    public LedgerCLI(Path file) {
        this.storage = new FlatFileStorage(file);
        this.ledger  = new LedgerService(storage.load());
    }

    public void run() {
        banner();
        loop: while (true) {
            System.out.print("> ");
            switch (in.nextLine().trim()) {
                case "n" -> newTxn();
                case "t" -> trial();
                case "b" -> bs();
                case "p" -> pl();
                case "q" -> { break loop; }
                default  -> System.out.println("(n)ew (t)rial (b)sheet (p) P&L (q)uit");
            }
        }
        storage.save(ledger.all());
        System.out.println("Saved. Bye!");
    }

    private void banner() {
        System.out.println("""
                === LedgerLite (no external libs) ===
                (n) New transaction
                (t) Trial balance
                (b) Balance sheet
                (p) Profit & Loss
                (q) Quit
                """);
    }

    private void newTxn() {
        System.out.print("Date (YYYY‑MM‑DD, blank=today): ");
        String d = in.nextLine().trim();
        LocalDate date = d.isBlank() ? LocalDate.now() : LocalDate.parse(d);

        System.out.print("Narration: ");
        String narr = in.nextLine();

        System.out.println("Enter entries: <account> <amount>. Blank line ends.");
        List<Entry> legs = new ArrayList<>();
        while (true) {
            String line = in.nextLine().trim();
            if (line.isBlank()) break;
            String[] p = line.split("\\s+");
            legs.add(new Entry(p[0], new BigDecimal(p[1])));
        }
        ledger.add(new Transaction(date, narr, legs));
        System.out.println("OK ✔");
    }

    private void trial()          { ledger.trialBalance().forEach((a,b) -> System.out.println(a + "\t" + b)); }
    private void bs()             { System.out.println(reports.balanceSheet(ledger.trialBalance())); }
    private void pl()             { System.out.println(reports.incomeStatement(ledger.trialBalance())); }
}
