package com.etaration.model;

/**
 * Represents a phone bill payment transaction.
 */
public class PhoneBillPaymentTransaction extends Transaction {
    private final String provider;
    private final String phoneNumber;

    public PhoneBillPaymentTransaction(String provider, String phoneNumber, double amount) {
        super(amount);
        this.provider = provider;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getType() {
        return "PhoneBillPaymentTransaction";
    }

    public String getProvider() {
        return provider;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
