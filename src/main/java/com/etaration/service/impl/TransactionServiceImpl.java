package com.etaration.service.impl;

import com.etaration.dto.TransactionDTO;
import com.etaration.entity.BankAccount;
import com.etaration.entity.DepositTransaction;
import com.etaration.entity.Transaction;
import com.etaration.entity.WithdrawalTransaction;
import com.etaration.repository.TransactionRepository;
import com.etaration.service.BankAccountService;
import com.etaration.service.TransactionService;
import com.etaration.util.MapperUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final MapperUtil mapperUtil;
    private final BankAccountService bankAccountService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, MapperUtil mapperUtil, BankAccountService bankAccountService) {
        this.transactionRepository = transactionRepository;
        this.mapperUtil = mapperUtil;
        this.bankAccountService = bankAccountService;
    }

    @Override
    @Transactional
    public String postTransaction(String accountNumber, Transaction transaction) {
        log.info("postTransaction process is starting");
        BankAccount account = bankAccountService.findByAccountNumber(accountNumber);
        String approvalCode = UUID.randomUUID().toString();
        transaction.setDate(LocalDateTime.now()); // Set the current date and time
        transaction.setBankAccountNumber(accountNumber);
        transaction.setApprovalCode(approvalCode);
        transaction.setStatus("OK");
        double balance = account.getBalance();
        if (transaction instanceof DepositTransaction) {
            balance += transaction.getAmount();
        } else if (transaction instanceof WithdrawalTransaction) {
            if (balance >= transaction.getAmount()) {
                balance -= transaction.getAmount();
            } else {
                throw new IllegalArgumentException("Insufficient funds for withdrawal");
            }
        }
        account.setBalance(balance);
        transactionRepository.save(transaction);
        bankAccountService.save(account);
        return approvalCode;
    }

    @Override
    public List<TransactionDTO> getTransactionsByAccountNumber(String accountNumber) {
        log.info("all transaction "+ transactionRepository.findAllByBankAccountNumber(accountNumber));
        return transactionRepository.findAllByBankAccountNumber(accountNumber)
                .stream().map(transaction -> mapperUtil.convert(transaction, new TransactionDTO()))
                .toList();

    }

    @Override
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
