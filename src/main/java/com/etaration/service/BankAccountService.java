package com.etaration.service;

import com.etaration.entity.BankAccount;
import com.etaration.entity.Transaction;

import java.util.List;
import java.util.Optional;

public interface BankAccountService {

    BankAccount createAccount(BankAccount bankAccount);
    String credit(String accountNumber, double amount);
    String debit(String accountNumber, double amount);
    Optional<BankAccount> getAccountById(Long accountId);
    List<BankAccount> getAllAccounts();
    BankAccount updateAccount(BankAccount bankAccount);
    void deleteAccount(Long accountId);
    List<Transaction> getTransactions();
}
