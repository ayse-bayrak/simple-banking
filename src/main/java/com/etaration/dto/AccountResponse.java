package com.etaration.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResponse {
    private String accountNumber;
    private String owner;
    private double balance;
    private LocalDateTime createDate;
    private List<TransactionDTO> transactions;
}
