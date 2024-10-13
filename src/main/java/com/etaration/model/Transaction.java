package com.etaration.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Abstract class representing a transaction.
 */
@Data
public abstract class Transaction {

    private final double amount;
    private final LocalDateTime date;

    public Transaction(double amount) {
        this.amount = amount;
        this.date = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + getAmount() +
                ", date=" + getDate() +
                '}';
    }
}
