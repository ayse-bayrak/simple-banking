package com.etaration.entity;

import com.etaration.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BankAccount extends BaseEntity {

    private String owner;
    private String accountNumber;
    private double balance;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();


    public BankAccount(String owner,
                       String accountNumber) {
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.balance = 0;
    }

    public BankAccount(String owner,
                       String accountNumber, double balance) {
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void post(Transaction transaction) {
        if (transaction instanceof DepositTransaction) {
            this.balance += transaction.getAmount();
        } else if (transaction instanceof WithdrawalTransaction) {
            this.balance -= transaction.getAmount();
        } else if (transaction instanceof PhoneBillPaymentTransaction) {
            this.balance -= transaction.getAmount();
        }
        transactions.add(transaction);
    }

}
