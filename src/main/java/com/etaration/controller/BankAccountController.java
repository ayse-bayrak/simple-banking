package com.etaration.controller;

import com.etaration.dto.AccountResponse;
import com.etaration.dto.TransactionDTO;
import com.etaration.dto.TransactionRequest;
import com.etaration.dto.wrapper.ResponseWrapper;
import com.etaration.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account/v1")
public class BankAccountController {


    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<ResponseWrapper> creditAccount(@PathVariable String accountNumber, @RequestBody TransactionRequest request) {
      String approvalCode = bankAccountService.credit(accountNumber, request.getAmount());

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .status("OK")
                .approvalCode(approvalCode)
                .build();

        ResponseWrapper responseWrapper = ResponseWrapper.builder()
                .success(true)
                .statusCode(HttpStatus.OK)
                .message("Credit transaction successful")
                .data(transactionDTO)
                .build();

        return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
    }

    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<ResponseWrapper> debitAccount(@PathVariable String accountNumber, @RequestBody TransactionRequest request) {
        String approvalCode = bankAccountService.debit(accountNumber, request.getAmount());

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .status("OK")
                .approvalCode(approvalCode)
                .build();

        ResponseWrapper responseWrapper = ResponseWrapper.builder()
                .success(true)
                .statusCode(HttpStatus.OK)
                .message("Debit transaction successful")
                .data(transactionDTO)
                .build();

        return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<ResponseWrapper> getAccountDetails(@PathVariable String accountNumber) {
        AccountResponse accountResponse = bankAccountService.getAccountDetails(accountNumber);

        ResponseWrapper responseWrapper = ResponseWrapper.builder()
                .success(true)
                .statusCode(HttpStatus.OK)
                .message("Account details retrieved successfully")
                .data(accountResponse)
                .build();

        return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
    }

}
