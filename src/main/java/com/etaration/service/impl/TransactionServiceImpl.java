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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public List<TransactionDTO> getAllTransactions() {
        log.info("getAllTransactions() first index' type is "+transactionRepository.findAll().get(0).getType());
        return transactionRepository.findAll().stream()
                .map(transaction-> mapperUtil.convert(transaction, new TransactionDTO()))
                .toList();
    }

    @Override
    @Transactional
    public void postTransaction(String accountNumber, Transaction transaction) {
        log.info("postTransaction process is starting");
        BankAccount account = bankAccountService.findByAccountNumber(accountNumber);

        //account.postTransaction(transaction);
        bankAccountService.save(account);

        transaction.setTransactionDate(LocalDateTime.now()); // Set the current date and time
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
        transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionDTO> getTransactionsByBankAccount(BankAccount bankAccount) {
        log.info("all transaction"+ transactionRepository.findAllByBankAccount(bankAccount));
        return transactionRepository.findAllByBankAccount(bankAccount)
                .stream().map(transaction -> mapperUtil.convert(transaction, new TransactionDTO()))
                .toList();

    }
}
