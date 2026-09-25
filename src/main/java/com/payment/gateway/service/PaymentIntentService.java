package com.payment.gateway.service;

import com.payment.gateway.controller.DTO.CreatePaymentIntentRequest;
import com.payment.gateway.entity.Customer;
import com.payment.gateway.entity.Merchant;
import com.payment.gateway.entity.PaymentIntent;
import com.payment.gateway.entity.enums.PaymentIntentStatus;
import com.payment.gateway.exception.CustomerNotFoundException;
import com.payment.gateway.exception.MerchantNotFoundException;
import com.payment.gateway.repository.CustomerRepository;
import com.payment.gateway.repository.MerchantRepository;
import com.payment.gateway.repository.PaymentIntentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentIntentService {

    private final PaymentIntentRepository paymentIntentRepository;
    private final CustomerRepository customerRepository;
    private final MerchantRepository merchantRepository;

    public Optional<PaymentIntent> findById(UUID id) {
        return paymentIntentRepository.findById(id);
    }

    @Transactional
    public PaymentIntent create(CreatePaymentIntentRequest request) {
        if (!customerRepository.existsByIdAndDeletedFalse(request.customerId())) {
            throw new CustomerNotFoundException(request.customerId());

        } else if (!merchantRepository.existsByIdAndStatusActive(request.merchantId())) {
            throw new MerchantNotFoundException(request.merchantId());

        }

        PaymentIntent paymentIntent = buildIntent(request);
        return paymentIntentRepository.save(paymentIntent);
    }

    private static PaymentIntent buildIntent(CreatePaymentIntentRequest request) {
        return PaymentIntent.builder()
                .merchant(Merchant.builder().id(request.merchantId()).build())
                .customer(Customer.builder().id(request.customerId()).build())
                .amount(request.amount())
                .currency(request.currency())
                .description(request.description())
                .status(PaymentIntentStatus.REQUIRES_PAYMENT_METHOD)
                .build();
    }
}
