package com.etaration.dto;

import com.etaration.entity.Transaction;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class AccountResponse {
    private String accountNumber;
    private String owner;
    private double balance;
    private LocalDateTime createDate;
    private List<TransactionDTO> transactions;
}
