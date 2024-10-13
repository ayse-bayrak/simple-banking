package com.etaration.repository;

import com.etaration.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository interface for BankAccount entities.
 * Extends JpaRepository to provide CRUD operations on BankAccount entities.
 */
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

   Optional<BankAccount> findByAccountNumber(String accountNumber);
}
