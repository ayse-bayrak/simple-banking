package com.etaration.service.impl;

import com.etaration.dto.AccountResponse;
import com.etaration.dto.TransactionDTO;
import com.etaration.entity.BankAccount;
import com.etaration.entity.DepositTransaction;
import com.etaration.entity.WithdrawalTransaction;
import com.etaration.repository.BankAccountRepository;
import com.etaration.service.BankAccountService;
import com.etaration.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final TransactionService transactionService;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, @Lazy TransactionService transactionService) {
        this.bankAccountRepository = bankAccountRepository;
        this.transactionService = transactionService;
    }

    @Transactional
    public String credit(String accountNumber, double amount) {
        log.info("Credit process");
        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        // Create a DepositTransaction and post it to the account
        DepositTransaction depositTransaction = new DepositTransaction(amount);
       transactionService.postTransaction(accountNumber, depositTransaction);

        // Save the updated account
        bankAccountRepository.save(account);

        return UUID.randomUUID().toString();
    }

    @Transactional
    public String debit(String accountNumber, double amount) {
        log.info("Debit process");
        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        // Create a WithdrawalTransaction and post it to the account
        WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction(amount);
        transactionService.postTransaction(accountNumber, withdrawalTransaction);

        // Save the updated account
        bankAccountRepository.save(account);

        return UUID.randomUUID().toString();
    }

    @Override
    public AccountResponse getAccountDetails(String accountNumber) {
        log.info("getAccountDetails are retrieving.");
        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        List<TransactionDTO> transactionDTOS = transactionService.getTransactionsByBankAccount(account);

        return AccountResponse.builder()
                .accountNumber(account.getAccountNumber())
                .owner(account.getOwner())
                .balance(account.getBalance())
                .createDate(account.getCreatedDate())
                .transactions(transactionDTOS) // Set populated transaction list
                .build();
    }

    @Override
    public BankAccount findByAccountNumber(String accountNumber) {
        log.info("Account is searching in BankAccountRepository");
        return bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    @Override
    public void save(BankAccount account) {
        log.info("Account is saved to BankRepository");
        bankAccountRepository.save(account);
    }


}

