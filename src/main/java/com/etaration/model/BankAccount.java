package com.etaration.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bank account with transactions.
 */
public class BankAccount {
    private final String owner;
    private final String accountNumber;
    private double balance;
    private final List<Transaction> transactions;

    public BankAccount(String owner, String accountNumber) {
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    /**
     * Credit the account with a specified amount.
     * @param transaction the deposit transaction
     */
    public void post(DepositTransaction transaction) {
        this.balance += transaction.getAmount();
        transactions.add(transaction);
    }

    /**
     * Debit the account with a specified amount.
     * @param transaction the withdrawal transaction
     */
    public void post(WithdrawalTransaction transaction) {
        if (transaction.getAmount() > balance) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        this.balance -= transaction.getAmount();
        transactions.add(transaction);
    }

    /**
     * Post a phone bill payment transaction.
     * @param transaction the phone bill payment transaction
     */
    public void post(PhoneBillPaymentTransaction transaction) {
        if (transaction.getAmount() > balance) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        this.balance -= transaction.getAmount();
        transactions.add(transaction);
    }

    public double getBalance() {
        return balance;
    }

    public String getOwner() {
        return owner;
    }

    public String getAccountNumber() {
        return accountNumber;
    }


    /**
     * Credits the account by adding the specified amount to the balance.
     *
     * @param amount the amount to credit
     * @throws IllegalArgumentException if the amount is less than or equal to 0
     */
    public void credit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Credit amount must be positive");
        }
        this.balance += amount;
        transactions.add(new DepositTransaction(amount));
    }

    /**
     * Debits the account by subtracting the specified amount from the balance.
     *
     * @param amount the amount to debit
     * @throws IllegalArgumentException if the amount is less than or equal to 0 or if there are insufficient funds
     */
    public void debit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Debit amount must be positive");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        this.balance -= amount;
        transactions.add(new WithdrawalTransaction(amount));
    }

    /**
     * Gets the list of transactions associated with this account.
     *
     * @return the list of transactions
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

}
