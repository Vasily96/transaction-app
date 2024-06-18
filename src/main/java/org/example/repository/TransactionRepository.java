package org.example.repository;

import org.example.entity.Account;
import org.example.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    public List<Transaction> getAllByAccountAndTimestampAfterAndTimestampBefore(Account account, LocalDateTime after, LocalDateTime before);
}
