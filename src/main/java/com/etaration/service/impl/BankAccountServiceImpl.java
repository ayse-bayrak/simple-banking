package com.etaration.service.impl;

import com.etaration.dto.AccountResponse;
import com.etaration.dto.BankAccountDTO;
import com.etaration.dto.TransactionDTO;
import com.etaration.entity.BankAccount;
import com.etaration.entity.DepositTransaction;
import com.etaration.entity.PhoneBillPaymentTransaction;
import com.etaration.entity.WithdrawalTransaction;
import com.etaration.exception.BankAccountNotFoundException;
import com.etaration.exception.InsufficientBalanceException;
import com.etaration.repository.BankAccountRepository;
import com.etaration.service.BankAccountService;
import com.etaration.service.TransactionService;
import com.etaration.util.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * Service implementation for handling bank account operations.
 */

@Slf4j
@Service
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final TransactionService transactionService;
    private final MapperUtil mapperUtil;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, @Lazy TransactionService transactionService, MapperUtil mapperUtil) {
        this.bankAccountRepository = bankAccountRepository;
        this.transactionService = transactionService;
        this.mapperUtil = mapperUtil;
    }

    /**
     * Credits a specified amount to the given bank account.
     *
     * @param accountNumber the bank account number
     * @param amount        the amount to be credited
     * @return the approval code for the transaction
     * @throws BankAccountNotFoundException if the account is not found
     */
    @Transactional
    public String credit(String accountNumber, double amount) {
        log.info("Credit process");
        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank Account not found"));

        // Create a DepositTransaction and post it to the account
        DepositTransaction depositTransaction = new DepositTransaction(amount);
        account.post(depositTransaction);

        return transactionService.postTransaction(accountNumber, depositTransaction); // return approvalCode
    }

    /**
     * Debits a specified amount from the given bank account.
     *
     * @param accountNumber the bank account number
     * @param amount        the amount to be debited
     * @return the approval code for the transaction
     * @throws BankAccountNotFoundException if the account is not found
     * @throws InsufficientBalanceException if balance is insufficient
     */
    @Transactional
    public String debit(String accountNumber, double amount) {
        log.info("Debit process");
        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank Account not found"));

        if (account.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance to pay phone bill");
        }

        // Create a WithdrawalTransaction and post it to the account
        WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction(amount);
        account.post(withdrawalTransaction);

        return transactionService.postTransaction(accountNumber, withdrawalTransaction);
    }

    /**
     * Processes a phone bill payment from the given bank account.
     *
     * @param accountNumber the bank account number
     * @param provider      the phone service provider
     * @param phoneNumber   the phone number to be billed
     * @param amount        the amount to be paid
     * @return the approval code for the transaction
     * @throws BankAccountNotFoundException if the account is not found
     * @throws InsufficientBalanceException if balance is insufficient
     */
    @Override
    @Transactional
    public String phoneBillPayment(String accountNumber, String provider, String phoneNumber, Double amount) {
        log.info("Phone Bill Payment process");
        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank Account not found"));

        if (account.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance to pay phone bill");
        }

        // Create a PhoneBillPaymentTransaction and post it to the account
        PhoneBillPaymentTransaction phoneBillPaymentTransaction = new PhoneBillPaymentTransaction(provider, phoneNumber, amount);
        account.post(phoneBillPaymentTransaction);

        return transactionService.postTransaction(accountNumber, phoneBillPaymentTransaction);
    }

    /**
     * Retrieves details of the given bank account, including its transaction history.
     *
     * @param accountNumber the bank account number
     * @return an {@link AccountResponse} containing account details and transactions
     * @throws BankAccountNotFoundException if the account is not found
     */
    @Override
    public AccountResponse getAccountDetails(String accountNumber) {
        log.info("getAccountDetails are retrieving.");
        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank Account not found"));

        List<TransactionDTO> transactionDTOS = transactionService.getTransactionsByAccountNumber(accountNumber);

        return AccountResponse.builder()
                .accountNumber(account.getAccountNumber())
                .owner(account.getOwner())
                .balance(account.getBalance())
                .createDate(account.getCreatedDate())
                .transactions(transactionDTOS)
                .build();
    }

    /**
     * Finds a bank account by its account number.
     *
     * @param accountNumber the bank account number
     * @return the {@link BankAccount} entity
     * @throws BankAccountNotFoundException if the account is not found
     */
    @Override
    public BankAccount findByAccountNumber(String accountNumber) {
        log.info("Account is searching in BankAccountRepository");
        return bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank Account not found"));
    }

    /**
     * Saves a new bank account to the repository.
     *
     * @param bankAccountDTO the {@link BankAccountDTO} to be saved
     */
    @Override
    public void save(BankAccountDTO bankAccountDTO) {
        log.info("Account is saved to BankRepository");
        bankAccountRepository.save(mapperUtil.convert(bankAccountDTO, new BankAccount()));
    }


}

