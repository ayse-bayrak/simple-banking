package com.etaration.service.impl;

import com.etaration.dto.BankAccountDTO;
import com.etaration.dto.TransactionDTO;
import com.etaration.entity.BankAccount;
import com.etaration.entity.Transaction;
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

/**
 * Service implementation for handling transactions related to bank accounts.
 */
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

    /**
     * Posts a transaction to the specified bank account and generates an approval code.
     *
     * @param accountNumber the bank account number
     * @param transaction   the transaction object (e.g., Deposit, Withdrawal)
     * @return the approval code for the transaction
     */
    @Override
    @Transactional
    public String postTransaction(String accountNumber, Transaction transaction) {
        log.info("postTransaction process is starting");
        BankAccount account = bankAccountService.findByAccountNumber(accountNumber);
        String approvalCode = UUID.randomUUID().toString();
        transaction.setDate(LocalDateTime.now()); // Set the current date and time
        transaction.setBankAccount(account);
        transaction.setType(transaction.getType());
        transaction.setApprovalCode(approvalCode);
        transactionRepository.save(transaction);
        bankAccountService.save(mapperUtil.convert(account, new BankAccountDTO()));
        return approvalCode;
    }

    /**
     * Retrieves the list of transactions for a specific bank account by account number.
     *
     * @param accountNumber the bank account number
     * @return a list of {@link TransactionDTO} representing the transactions for the account
     */
    @Override
    public List<TransactionDTO> getTransactionsByAccountNumber(String accountNumber) {
        BankAccount account = bankAccountService.findByAccountNumber(accountNumber);
        log.info("all transaction "+ transactionRepository.findAllByBankAccount(account));
        return transactionRepository.findAllByBankAccount(account)
                .stream().map(transaction -> mapperUtil.convert(transaction, new TransactionDTO()))
                .toList();
    }

    /**
     * Saves the provided transaction to the repository.
     *
     * @param transaction the transaction entity to be saved
     */
    @Override
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
