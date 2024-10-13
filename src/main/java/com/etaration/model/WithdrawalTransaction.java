package com.etaration.model;

/**
 * Represents a withdrawal transaction.
 */
public class WithdrawalTransaction extends Transaction {
    public WithdrawalTransaction(double amount) {
        super(amount);
    }

    @Override
    public String getType() {
        return "WithdrawalTransaction";
    }
}
