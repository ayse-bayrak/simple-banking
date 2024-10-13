package com.etaration.service.impl;

import com.etaration.entity.BankAccount;
import com.etaration.entity.DepositTransaction;
import com.etaration.entity.Transaction;
import com.etaration.entity.WithdrawalTransaction;
import com.etaration.repository.BankAccountRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class BankAccountServiceImpl {

    private final BankAccountRepository bankAccountRepository;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Transactional
    public void postTransaction(Long accountId, Transaction transaction) {
        BankAccount account = bankAccountRepository.findById(accountId)
            .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.postTransaction(transaction);
        bankAccountRepository.save(account);
    }
    @Transactional
    public void credit(Long accountId, double amount) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        // Create a DepositTransaction and post it to the account
        DepositTransaction depositTransaction = new DepositTransaction(amount);
        account.postTransaction(depositTransaction);

        // Save the updated account
        bankAccountRepository.save(account);
    }

    @Transactional
    public void debit(Long accountId, double amount) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        // Create a WithdrawalTransaction and post it to the account
        WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction(amount);
        account.postTransaction(withdrawalTransaction);

        // Save the updated account
        bankAccountRepository.save(account);
    }

}
