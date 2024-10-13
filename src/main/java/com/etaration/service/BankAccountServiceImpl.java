package com.etaration.service;

import com.etaration.model.BankAccount;
import com.etaration.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing bank accounts.
 * Handles business logic related to creating, updating, and querying bank accounts.
 */

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * Create a new bank account.
     * @param bankAccount BankAccount object to be saved.
     * @return BankAccount object after saving.
     */
    public BankAccount createAccount(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    /**
     * Credit the bank account by adding the amount.
     *
     * @param accountNumber The account number.
     * @param amount        The amount to credit.
     * @return Approval code after successful credit.
     */
    public String credit(String accountNumber, double amount) {
        Optional<BankAccount> accountOptional = bankAccountRepository.findByAccountNumber(accountNumber);

        if (accountOptional.isPresent()) {
            BankAccount account = accountOptional.get();
            account.credit(amount);
            bankAccountRepository.save(account);
            return generateApprovalCode();
        } else {
            throw new IllegalArgumentException("Account not found");
        }
    }

    /**
     * Debit the bank account by subtracting the amount.
     *
     * @param accountNumber The account number.
     * @param amount        The amount to debit.
     * @return Approval code after successful debit.
     */
    public String debit(String accountNumber, double amount) {
        Optional<BankAccount> accountOptional = bankAccountRepository.findByAccountNumber(accountNumber);

        if (accountOptional.isPresent()) {
            BankAccount account = accountOptional.get();
            account.debit(amount);
            bankAccountRepository.save(account);
            return generateApprovalCode();
        } else {
            throw new IllegalArgumentException("Account not found");
        }
    }

    /**
     * Finds a bank account by its account number.
     *
     * @param accountNumber the account number
     * @return the bank account
     * @throws IllegalArgumentException if the account is not found
     */
    private BankAccount findAccountByNumber(String accountNumber) {
        Optional<BankAccount> accountOptional = bankAccountRepository.findByAccountNumber(accountNumber);
        return accountOptional.orElseThrow(() -> new IllegalArgumentException("Account not found with number: " + accountNumber));
    }

    /**
     * Get the account details by account number.
     *
     * @param accountNumber The account number.
     * @return BankAccount object.
     */
    public BankAccount getAccount(String accountNumber) {
        return bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }


    /**
     * Retrieve all bank accounts.
     * @return List of all bank accounts.
     */
    public List<BankAccount> getAllAccounts() {
        return bankAccountRepository.findAll();
    }

    /**
     * Update an existing bank account.
     * @param bankAccount Updated BankAccount object.
     * @return Updated BankAccount object.
     */
    public BankAccount updateAccount(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    /**
     * Delete a bank account by its ID.
     * @param accountId The ID of the bank account to be deleted.
     */
    public void deleteAccount(Long accountId) {
        bankAccountRepository.deleteById(accountId);
    }

    /**
     * Generates a random approval code for the transaction.
     *
     * @return a random approval code
     */
    private String generateApprovalCode() {
        SecureRandom random = new SecureRandom();
        int code = random.nextInt(999999);  // Generate a random 6-digit number
        return String.format("%06d", code); // Format to 6 digits, including leading zeros
    }
}