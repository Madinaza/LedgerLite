package com.ledgerlite.entity;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class EntryEmbeddable {

    private String account;
    private BigDecimal amount;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
