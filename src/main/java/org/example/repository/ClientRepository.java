package org.example.repository;

import org.example.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("from Client c join fetch c.accounts where c.id = ?1")
    public Optional<Client> fetchClientWithAccount(Long id);

    public Optional<Client> findClientByName(String name);
}
