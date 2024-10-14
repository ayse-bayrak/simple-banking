package com.etaration;

import com.etaration.controller.BankAccountController;
import com.etaration.dto.AccountResponse;
import com.etaration.dto.TransactionDTO;
import com.etaration.dto.TransactionRequest;
import com.etaration.dto.wrapper.ResponseWrapper;
import com.etaration.entity.DepositTransaction;
import com.etaration.entity.PhoneBillPaymentTransaction;
import com.etaration.entity.WithdrawalTransaction;
import com.etaration.service.BankAccountService;
import com.etaration.service.TransactionService;
import com.etaration.util.MapperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BankAccountControllerTest {

    @Mock
    private BankAccountService bankAccountService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private MapperUtil mapperUtil;

    @InjectMocks
    private BankAccountController bankAccountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreditAccount() {
        String accountNumber = "12345";
        TransactionRequest request = new TransactionRequest(1000.0);

        // Mock the behavior of the service and mapper
        when(bankAccountService.credit(accountNumber, request.getAmount())).thenReturn("approval-code-123");
        DepositTransaction depositTransaction = new DepositTransaction();
        when(mapperUtil.convert(any(TransactionDTO.class), any(DepositTransaction.class))).thenReturn(depositTransaction);

        // Call the controller method
        ResponseEntity<ResponseWrapper> response = bankAccountController.creditAccount(accountNumber, request);

        // Verify the results
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().isSuccess());
        assertEquals("Credit transaction successful", response.getBody().getMessage());

        // Verify the service and mapper were called
        verify(bankAccountService).credit(accountNumber, request.getAmount());
        verify(transactionService).save(depositTransaction);
    }

    @Test
    void testDebitAccount() {
        String accountNumber = "12345";
        TransactionRequest request = new TransactionRequest(200.0);

        // Mock the behavior of the service and mapper
        when(bankAccountService.debit(accountNumber, request.getAmount())).thenReturn("approval-code-456");
        WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction();
        when(mapperUtil.convert(any(TransactionDTO.class), any(WithdrawalTransaction.class))).thenReturn(withdrawalTransaction);

        // Call the controller method
        ResponseEntity<ResponseWrapper> response = bankAccountController.debitAccount(accountNumber, request);

        // Verify the results
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().isSuccess());
        assertEquals("Debit transaction successful", response.getBody().getMessage());

        // Verify the service and mapper were called
        verify(bankAccountService).debit(accountNumber, request.getAmount());
        verify(transactionService).save(withdrawalTransaction);
    }

    @Test
    void testPhoneBillPayment() {
        String accountNumber = "12345";
        String provider = "Vodafone";
        String phoneNumber = "5423345566";
        TransactionRequest request = new TransactionRequest(96.50);

        // Mock the behavior of the service and mapper
        when(bankAccountService.phoneBillPayment(accountNumber, provider, phoneNumber, request.getAmount())).thenReturn("approval-code-789");
        PhoneBillPaymentTransaction phoneBillPaymentTransaction = new PhoneBillPaymentTransaction();
        when(mapperUtil.convert(any(TransactionDTO.class), any(PhoneBillPaymentTransaction.class))).thenReturn(phoneBillPaymentTransaction);

        // Call the controller method
        ResponseEntity<ResponseWrapper> response = bankAccountController.phoneBillPayment(accountNumber, provider, phoneNumber, request);

        // Verify the results
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().isSuccess());
        assertEquals("Phone Bill Payment Transaction successful", response.getBody().getMessage());

        // Verify the service and mapper were called
        verify(bankAccountService).phoneBillPayment(accountNumber, provider, phoneNumber, request.getAmount());
        verify(transactionService).save(phoneBillPaymentTransaction);
    }

    @Test
    void testGetAccountDetails() {
        String accountNumber = "12345";

        // Mock the behavior of the service
        AccountResponse accountResponse = AccountResponse.builder()
                .accountNumber(accountNumber)
                .owner("Jim")
                .balance(1000.0)
                .createDate(LocalDateTime.now())
                .build();
        when(bankAccountService.getAccountDetails(accountNumber)).thenReturn(accountResponse);

        // Call the controller method
        ResponseEntity<ResponseWrapper> response = bankAccountController.getAccountDetails(accountNumber);

        // Verify the results
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().isSuccess());
        assertEquals("Account details retrieved successfully", response.getBody().getMessage());

        // Verify the service was called
        verify(bankAccountService).getAccountDetails(accountNumber);
    }
}
