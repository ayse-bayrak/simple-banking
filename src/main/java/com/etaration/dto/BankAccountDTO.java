package com.etaration.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDTO {
    private String owner;
    private String accountNumber;
    private double balance;
    private List<TransactionDTO> transactions;
}
