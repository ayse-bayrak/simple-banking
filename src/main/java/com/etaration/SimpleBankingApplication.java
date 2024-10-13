package com.etaration;

import com.etaration.entity.BankAccount;
import com.etaration.repository.BankAccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SimpleBankingApplication implements CommandLineRunner {

    private final BankAccountRepository bankAccountRepository;

    public SimpleBankingApplication(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(SimpleBankingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        BankAccount account = new BankAccount("Ayse Gunes Bayrak", "669-7788");
        bankAccountRepository.save(account);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
