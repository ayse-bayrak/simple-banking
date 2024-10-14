package com.etaration.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("PhoneBillPayment")
public class PhoneBillPaymentTransaction extends Transaction {

    private String provider;
    private String phoneNumber;

    public PhoneBillPaymentTransaction(String provider, String phoneNumber, double amount) {
        super(amount);  // Call to the parent Transaction constructor
        this.provider = provider;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void apply(BankAccount account) {
        // This method will subtract the amount from the account balance
        if (account.getBalance() < this.getAmount()) {
            throw new IllegalArgumentException("Insufficient balance to pay phone bill");
        }
        account.setBalance(account.getBalance() - this.getAmount());
    }

    @Override
    public String toString() {
        return "PhoneBillPaymentTransaction{" +
               "provider='" + provider + '\'' +
               ", phoneNumber='" + phoneNumber + '\'' +
               ", amount=" + getAmount() +
               ", date=" + getDate() +
               '}';
    }
}
