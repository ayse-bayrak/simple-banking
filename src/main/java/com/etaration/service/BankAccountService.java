package com.etaration.service;

import com.etaration.dto.AccountResponse;
import com.etaration.dto.BankAccountDTO;
import com.etaration.entity.BankAccount;

public interface BankAccountService {

    String credit(String accountNumber, double amount);
    String debit(String accountNumber, double amount);
    AccountResponse getAccountDetails(String accountNumber);
    BankAccount findByAccountNumber(String accountNumber);
    void save(BankAccountDTO bankAccountDTO);
    String phoneBillPayment(String accountNumber, String provider, String phoneNumber, Double amount);
}
