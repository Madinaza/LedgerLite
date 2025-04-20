package com.ledgerlite.service;

import java.math.BigDecimal;
import java.util.Map;

/** Produces very simple BS and P/L from trial balance. */
public final class ReportService {

    private static BigDecimal sum(Map<String, BigDecimal> tb, String prefix) {
        return tb.entrySet().stream()
                .filter(e -> e.getKey().startsWith(prefix))
                .map(Map.Entry::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String balanceSheet(Map<String, BigDecimal> tb) {
        BigDecimal assets = sum(tb, "1");
        BigDecimal liab   = sum(tb, "2");
        BigDecimal equity = sum(tb, "3");
        return """
                === Balance Sheet ===
                Assets:      %s
                Liabilities: %s
                Equity:      %s
                -----------------------------
                Assets = Liab + Equity ? %s
                """.formatted(assets, liab, equity,
                assets.compareTo(liab.add(equity)) == 0 ? "YES" : "NO");
    }

    public String incomeStatement(Map<String, BigDecimal> tb) {
        BigDecimal inc = sum(tb, "4");
        BigDecimal exp = sum(tb, "5");
        return """
                === Income Statement ===
                Income:  %s
                Expense: %s
                -----------------------------
                Net Profit: %s
                """.formatted(inc, exp, inc.add(exp));
    }
}
