package com.etaration.service;

import com.etaration.dto.AccountResponse;
import com.etaration.entity.BankAccount;
import com.etaration.entity.Transaction;

import java.util.List;
import java.util.Optional;

public interface BankAccountService {

    String credit(String accountNumber, double amount);
    String debit(String accountNumber, double amount);
    AccountResponse getAccountDetails(String accountNumber);

   BankAccount findByAccountNumber(String accountNumber);

    void save(BankAccount account);
}
