package com.etaration.entity;

import com.etaration.entity.common.BaseEntity;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * I use single-table inheritance
 * all subclasses are stored in a single database table, and JPA uses
 * a discriminator column to identify which subclass a particular row represents.
 * The @DiscriminatorValue specifies the value that will be stored in the discriminator column
 * to indicate which entity subclass (transaction type, in your case) the row corresponds to.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "transaction_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Transaction extends BaseEntity {

    private double amount;
    private LocalDateTime date;
    private String type;
    private String approvalCode;

    @ManyToOne
    @JoinColumn(name = "account_number")
    private BankAccount bankAccount;

    public Transaction(double amount) {
        this.amount = amount;
        this.date = LocalDateTime.now();
    }

    public String getType() {
        return this.getClass().getSimpleName();
    }

}
