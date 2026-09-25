package com.payment.gateway.repository;

import com.payment.gateway.entity.Merchant;
import com.payment.gateway.entity.enums.MerchantStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, UUID> {
    @Modifying
    @Query("""
        UPDATE Merchant m
           SET m.status = 'DISABLED'
         WHERE m.id = :id
           AND m.status <> 'DISABLED'
    """)
    int disableById(@Param("id") UUID id);

    boolean existsByIdAndStatusActive(UUID id);
}
