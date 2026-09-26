package com.payment.gateway.service;

import com.payment.gateway.controller.DTO.CreateCustomerRequest;
//import com.payment.gateway.controller.DTO.CustomerResponse;
import com.payment.gateway.entity.Customer;
import com.payment.gateway.exception.CustomerNotFoundException;
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
    public Customer findByIdAndDeletedFalse(UUID id) {
        return customerRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Transactional
    public boolean deleteCustomer(UUID id) {
        if (id == null) {
            return false;
        }

        return customerRepository.softDeleteById(id) > 0;
    }

    @Transactional
    public Customer createCustomer(CreateCustomerRequest request) {
        Customer customer = buildCustomer(request);
        return customerRepository.save(customer);
    }

    private static Customer buildCustomer(CreateCustomerRequest event) {
        return Customer.builder()
                .description(event.description())
                .firstName(event.firstName())
                .lastName(event.lastName())
                .build();
    }
}
