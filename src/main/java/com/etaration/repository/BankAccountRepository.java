package com.etaration.repository;

import com.etaration.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for BankAccount entities.
 * Extends JpaRepository to provide CRUD operations on BankAccount entities.
 */

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

   Optional<BankAccount> findByAccountNumber(String accountNumber);

}
