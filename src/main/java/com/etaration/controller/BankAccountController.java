package com.etaration.controller;

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
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/account/v1")
public class BankAccountController {


    private final BankAccountService bankAccountService;
    private final TransactionService transactionService;
    private final MapperUtil mapperUtil;

    public BankAccountController(BankAccountService bankAccountService, @Lazy TransactionService transactionService, MapperUtil mapperUtil) {
        this.bankAccountService = bankAccountService;
        this.transactionService = transactionService;
        this.mapperUtil = mapperUtil;
    }

    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<ResponseWrapper> creditAccount(@PathVariable String accountNumber, @RequestBody TransactionRequest request) {
      String approvalCode = bankAccountService.credit(accountNumber, request.getAmount());

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .date(LocalDateTime.now())
                .amount(request.getAmount())
                .type("DepositTransaction")
                .approvalCode(approvalCode)
                .build();
        DepositTransaction depositTransaction = mapperUtil.convert(transactionDTO, new DepositTransaction());
        transactionService.save(depositTransaction);

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
                .date(LocalDateTime.now())
                .amount(request.getAmount())
                .type("WithdrawalTransaction")
                .approvalCode(approvalCode)
                .build();

        WithdrawalTransaction withdrawalTransaction = mapperUtil.convert(transactionDTO, new WithdrawalTransaction());
        transactionService.save(withdrawalTransaction);

        ResponseWrapper responseWrapper = ResponseWrapper.builder()
                .success(true)
                .statusCode(HttpStatus.OK)
                .message("Debit transaction successful")
                .data(transactionDTO)
                .build();

        return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
    }

    @PostMapping("/phoneBill/{accountNumber}/{provider}/{phoneNumber}")
    public ResponseEntity<ResponseWrapper> phoneBillPayment(@PathVariable String accountNumber,
                                                            @PathVariable String provider,
                                                            @PathVariable String phoneNumber,
                                                            @RequestBody TransactionRequest request) {
        String approvalCode = bankAccountService.phoneBillPayment(accountNumber, provider, phoneNumber, request.getAmount());

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .date(LocalDateTime.now())
                .amount(request.getAmount())
                .type("PhoneBillPaymentTransaction")
                .approvalCode(approvalCode)
                .build();
        PhoneBillPaymentTransaction phoneBillPaymentTransaction = mapperUtil.convert(transactionDTO, new PhoneBillPaymentTransaction());
        transactionService.save(phoneBillPaymentTransaction);

        ResponseWrapper responseWrapper = ResponseWrapper.builder()
                .success(true)
                .statusCode(HttpStatus.OK)
                .message("Phone Bill Payment Transaction successful")
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
