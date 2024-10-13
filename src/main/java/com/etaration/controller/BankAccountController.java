package com.etaration.controller;

import com.etaration.model.BankAccount;
import com.etaration.service.BankAccountService;
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
    public ResponseEntity<String> credit(@PathVariable String accountNumber, @RequestBody TransactionRequest request) {
        String approvalCode = bankAccountService.credit(accountNumber, request.getAmount());
        return ResponseEntity.ok("{\"status\": \"OK\", \"approvalCode\": \"" + approvalCode + "\"}");
    }

    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<String> debit(@PathVariable String accountNumber, @RequestBody TransactionRequest request) {
        String approvalCode = bankAccountService.debit(accountNumber, request.getAmount());
        return ResponseEntity.ok("{\"status\": \"OK\", \"approvalCode\": \"" + approvalCode + "\"}");
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<BankAccount> getAccount(@PathVariable String accountNumber) {
        BankAccount account = bankAccountService.getAccount(accountNumber);
        return ResponseEntity.ok(account);
    }
}
