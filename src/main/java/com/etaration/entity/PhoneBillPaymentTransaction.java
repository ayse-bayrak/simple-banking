package com.etaration.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("PhoneBillPayment")
public class PhoneBillPaymentTransaction extends Transaction {

    private String provider;
    private String phoneNumber;

    public PhoneBillPaymentTransaction(String provider,
                                       String phoneNumber,
                                       double amount) {
        super(amount);
        this.provider = provider;
        this.phoneNumber = phoneNumber;
    }

}
