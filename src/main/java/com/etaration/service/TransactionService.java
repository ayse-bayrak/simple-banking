package com.etaration.service;

import com.etaration.dto.TransactionDTO;
import com.etaration.entity.BankAccount;
import com.etaration.entity.Transaction;

import java.util.List;

public interface TransactionService {

    List<TransactionDTO> getAllTransactions();
    void postTransaction(String accountNumber, Transaction transaction);
    List<TransactionDTO> getTransactionsByBankAccount(BankAccount bankAccount);
}
