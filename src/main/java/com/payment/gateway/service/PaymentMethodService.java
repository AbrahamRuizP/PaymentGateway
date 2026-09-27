package com.payment.gateway.service;

import com.payment.gateway.controller.DTO.CreatePaymentMethodRequest;
import com.payment.gateway.entity.Customer;
import com.payment.gateway.entity.PaymentMethod;
import com.payment.gateway.exception.CustomerNotFoundException;
import com.payment.gateway.exception.PaymentIntentNotFoundException;
import com.payment.gateway.exception.PaymentMethodNotFoundException;
import com.payment.gateway.repository.CustomerRepository;
import com.payment.gateway.repository.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final CustomerRepository customerRepository;

    /* METHODS */
    public PaymentMethod findById(UUID id) {
        return paymentMethodRepository.findById(id)
                .orElseThrow(() -> new PaymentMethodNotFoundException(id));
    }

    public PaymentMethod create(CreatePaymentMethodRequest request) {
        if (!customerRepository.existsByIdAndDeletedFalse(request.customerId())) {
            throw new CustomerNotFoundException(request.customerId());
        }
        if (request.last4() != null && request.last4().length() != 4) {
            // throw new exception
            return null;
        }

        PaymentMethod paymentMethod = buildPaymentMethod(request);
        return paymentMethodRepository.save(paymentMethod);
    }

    private static PaymentMethod buildPaymentMethod(
        CreatePaymentMethodRequest request
    ) {
        return PaymentMethod.builder()
                .type(request.type())
                .provider(request.provider())
                .brand(request.brand())
                .last4(request.last4())
                .providerPaymentMethodId(request.providerPaymentMethodId())
                .customer(Customer.builder().id(request.customerId()).build())
                .build();
    }
}
