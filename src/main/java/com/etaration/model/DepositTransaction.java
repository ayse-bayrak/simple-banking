package com.etaration.model;

/**
 * Represents a deposit transaction.
 */
public class DepositTransaction extends Transaction {
    public DepositTransaction(double amount) {
        super(amount);
    }

    @Override
    public String getType() {
        return "DepositTransaction";
    }
}
