package com.payment.gateway.service;

import com.payment.gateway.controller.DTO.CreatePaymentIntentRequest;
import com.payment.gateway.entity.Customer;
import com.payment.gateway.entity.Merchant;
import com.payment.gateway.entity.PaymentIntent;
import com.payment.gateway.repository.PaymentIntentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentIntentService {

    private final PaymentIntentRepository repository;

    public Optional<PaymentIntent> findByid(UUID id) {
        return repository.findById(id);
    }

    public PaymentIntent save(CreatePaymentIntentRequest request) {
        PaymentIntent p = buildIntent(request);

        return null;
    }

    private static PaymentIntent buildIntent(CreatePaymentIntentRequest request) {
        return PaymentIntent.builder()
                .merchant(Merchant.builder().id(request.merchantId()).build())
                .customer(Customer.builder().id(request.customerId()).build())
                .amount(request.amount())
                .currency(request.currency())
                .description(request.description())
                .build();
    }
}
