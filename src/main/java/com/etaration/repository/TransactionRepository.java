package com.etaration.repository;

import com.etaration.entity.BankAccount;
import com.etaration.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for Transaction entities.
 * Extends JpaRepository to provide CRUD operations on Transaction entities.
 */

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

List<Transaction> findAllByBankAccount(BankAccount bankAccount);

}