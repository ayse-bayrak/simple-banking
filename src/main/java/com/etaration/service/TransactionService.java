package com.etaration.service;

import com.etaration.dto.TransactionDTO;
import com.etaration.entity.Transaction;
import java.util.List;

public interface TransactionService {

    String postTransaction(String accountNumber, Transaction transaction);
    List<TransactionDTO> getTransactionsByAccountNumber(String accountNumber);
    void save(Transaction transaction);
}
