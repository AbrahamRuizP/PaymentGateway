package com.payment.gateway.repository;

import com.payment.gateway.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    public Optional<Customer> findByIdAndDeletedFalse(UUID id);

    @Modifying
    @Query("""
    UPDATE Customer c
    SET c.deleted = true
    WHERE c.id = :id
      AND c.deleted = false
""")
    int softDeleteById(UUID id);
}
