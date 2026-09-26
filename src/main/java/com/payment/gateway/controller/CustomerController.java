package com.payment.gateway.controller;

import com.payment.gateway.exception.CustomerNotFoundException;
import com.payment.gateway.controller.DTO.CreateCustomerRequest;
import com.payment.gateway.controller.DTO.CustomerResponse;
import com.payment.gateway.entity.Customer;
import com.payment.gateway.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse createCustomer(@RequestBody @Valid CreateCustomerRequest request) {
        return toResponse(customerService.createCustomer(request));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse getCustomer(@PathVariable UUID id) {
        Customer c = customerService.findByIdAndDeletedFalse(id);
        return toResponse(c);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable UUID id) {
        if (!customerService.deleteCustomer(id)) {
            throw new CustomerNotFoundException(id);
        }
    }

    private static CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getCreatedAt(),
                customer.getUpdatedAt()
        );
    }
}
