package com.payment.gateway.service;

import com.payment.gateway.entity.Customer;
import com.payment.gateway.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    /* METHODS */
    public Optional<Customer> findById(UUID id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> findByIdAndDeletedFalse(UUID id) {
        return customerRepository.findByIdAndDeletedFalse(id);
    }

    @Transactional
    public boolean deleteCustomer(UUID id) {
        if (id == null) {
            return false;
        }

        return customerRepository.softDeleteById(id) > 0;
    }

}
