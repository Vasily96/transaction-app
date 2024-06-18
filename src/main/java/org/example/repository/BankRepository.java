package org.example.repository;

import org.example.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

    @Query("from Bank b join fetch b.accounts where b.id = ?1")
    public Optional<Bank> fetchBankWithAccount(Long id);

    public Optional<Bank> findBankByName(String name);
}
