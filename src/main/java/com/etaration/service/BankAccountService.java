package com.etaration.service;

import com.etaration.dto.AccountResponse;
import com.etaration.entity.BankAccount;

public interface BankAccountService {

    String credit(String accountNumber, double amount);
    String debit(String accountNumber, double amount);
    AccountResponse getAccountDetails(String accountNumber);

   BankAccount findByAccountNumber(String accountNumber);

    void save(BankAccount account);
}
