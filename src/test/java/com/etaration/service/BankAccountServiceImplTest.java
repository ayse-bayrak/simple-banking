package com.etaration.service;

import com.etaration.dto.AccountResponse;
import com.etaration.entity.BankAccount;
import com.etaration.entity.DepositTransaction;
import com.etaration.entity.PhoneBillPaymentTransaction;
import com.etaration.entity.WithdrawalTransaction;
import com.etaration.exception.BankAccountNotFoundException;
import com.etaration.exception.InsufficientBalanceException;
import com.etaration.repository.BankAccountRepository;
import com.etaration.service.impl.BankAccountServiceImpl;
import com.etaration.util.MapperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BankAccountServiceImplTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private TransactionService transactionService;

    @Mock
    private MapperUtil mapperUtil;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    private BankAccount account;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up a bank account for tests
        account = new BankAccount("Jim", "12345", 500.0); // Initial balance is 500
    }

    @Test
    public void testCredit() {
        // Arrange
        when(bankAccountRepository.findByAccountNumber("12345")).thenReturn(Optional.of(account));
        when(transactionService.postTransaction(anyString(), any(DepositTransaction.class)))
                .thenReturn("approval-code-123"); // Mock return value

        // Act
        String approvalCode = bankAccountService.credit("12345", 1000.0);

        // Assert
        assertNotNull(approvalCode); // Make sure this isn't null
        assertEquals(1500.0, account.getBalance(), 0.0001);
    }


    @Test
    public void testDebitSuccess() {
        // Arrange
        when(bankAccountRepository.findByAccountNumber("12345")).thenReturn(Optional.of(account));
        when(transactionService.postTransaction(anyString(), any(WithdrawalTransaction.class)))
                .thenReturn("approval-code-123"); // Mock return value
        // Act
        String approvalCode = bankAccountService.debit("12345", 200.0);

        // Assert
        assertNotNull(approvalCode);
        assertEquals(300.0, account.getBalance(), 0.0001);
        verify(transactionService, times(1)).postTransaction(anyString(), any(WithdrawalTransaction.class));
    }

    @Test
    public void testDebitInsufficientBalance() {
        // Arrange
        when(bankAccountRepository.findByAccountNumber("12345")).thenReturn(Optional.of(account));

        // Act & Assert
        assertThrows(InsufficientBalanceException.class, () -> bankAccountService.debit("12345", 600.0));
    }

    @Test
    public void testPhoneBillPaymentSuccess() {
        // Arrange
        when(bankAccountRepository.findByAccountNumber("12345")).thenReturn(Optional.of(account));
        when(transactionService.postTransaction(anyString(), any(PhoneBillPaymentTransaction.class)))
                .thenReturn("approval-code-123"); // Mock return value
        // Act
        String approvalCode = bankAccountService.phoneBillPayment("12345", "Vodafone", "5423345566", 96.50);

        // Assert
        assertNotNull(approvalCode);
        assertEquals(403.50, account.getBalance(), 0.0001);
        verify(transactionService, times(1)).postTransaction(anyString(), any(PhoneBillPaymentTransaction.class));
    }

    @Test
    public void testGetAccountDetails() {
        // Arrange
        when(bankAccountRepository.findByAccountNumber("12345")).thenReturn(Optional.of(account));

        // Act
        AccountResponse response = bankAccountService.getAccountDetails("12345");

        // Assert
        assertNotNull(response);
        assertEquals("12345", response.getAccountNumber());
        assertEquals("Jim", response.getOwner());
        assertEquals(500.0, response.getBalance(), 0.0001);
    }

    @Test
    public void testFindByAccountNumberNotFound() {
        // Arrange
        when(bankAccountRepository.findByAccountNumber("12345")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BankAccountNotFoundException.class, () -> bankAccountService.findByAccountNumber("12345"));
    }
}
