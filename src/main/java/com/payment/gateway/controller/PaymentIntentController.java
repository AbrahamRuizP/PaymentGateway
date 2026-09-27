package com.payment.gateway.controller;

import com.payment.gateway.controller.DTO.AttachPaymentMethodRequest;
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

    @PostMapping("/{id}/payment-method") // id reference to payment intent id
    @ResponseStatus(HttpStatus.OK)
    public PaymentIntentResponse attachPaymentMethod(
            @PathVariable UUID id,
            @RequestBody @Valid AttachPaymentMethodRequest request
    ) {
        PaymentIntent intent = service.attachPaymentMethod(id, request);

        return toResponse(intent);
    }

    @PostMapping("/{id}/confirm")
    @ResponseStatus(HttpStatus.OK)
    public PaymentIntentResponse confirmPaymentMethod(@PathVariable UUID id) {
        PaymentIntent intent = service.confirmPaymentIntent(id);

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
