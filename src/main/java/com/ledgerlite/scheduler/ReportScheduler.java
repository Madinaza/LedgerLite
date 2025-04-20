package com.ledgerlite.scheduler;

import com.ledgerlite.repository.TransactionRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Component
public class ReportScheduler {

    private final TransactionRepository txRepo;
    private final JavaMailSender mail;

    public ReportScheduler(TransactionRepository txRepo, JavaMailSender mail) {
        this.txRepo = txRepo;
        this.mail = mail;
    }

    @Scheduled(cron = "0 0 7 * * *") // every day at 07:00
    public void dailyPnlReport() {
        var today = LocalDate.now();
        var txs = txRepo.findByDateBetween(today, today);
        var body = txs.stream()
                .map(t -> t.getNarration() + ": " +
                        t.getEntries().stream()
                                .map(e -> e.getAccount() + "=" + e.getAmount())
                                .collect(Collectors.joining(", ")))
                .collect(Collectors.joining("\n"));

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("finance@yourcompany.com");
        msg.setSubject("Daily Transactions Report - " + today);
        msg.setText(body.isEmpty() ? "No transactions today." : body);
        mail.send(msg);
    }
}