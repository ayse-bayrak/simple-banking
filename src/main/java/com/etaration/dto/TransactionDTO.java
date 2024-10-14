package com.etaration.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private LocalDateTime date;
    private double amount;
    private String type;
    private String approvalCode;

}
