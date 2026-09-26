package com.payment.gateway.controller;

import com.payment.gateway.controller.DTO.CreatePaymentIntentRequest;
import com.payment.gateway.controller.DTO.PaymentIntentResponse;
import com.payment.gateway.entity.PaymentIntent;
import com.payment.gateway.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment-intents")
public class PaymentIntentController {

    private final PaymentIntentService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentIntentResponse createPaymentIntent(
            @RequestBody @Valid CreatePaymentIntentRequest request
    ) {
        PaymentIntent intent = service.create(request);
        return toResponse(intent);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentIntentResponse getPaymentIntent(@PathVariable UUID id) {
        PaymentIntent intent = service.findById(id);
        return toResponse(intent);
    }

    private static PaymentIntentResponse toResponse(PaymentIntent intent) {
        return PaymentIntentResponse.builder()
                .id(intent.getId())
                .amount(intent.getAmount())
                .currency(intent.getCurrency())
                .status(intent.getStatus())
                .description(intent.getDescription())
                .createdAt(intent.getCreatedAt())
                .build();
    }

}
