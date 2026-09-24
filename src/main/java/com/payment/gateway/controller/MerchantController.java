package com.payment.gateway.controller;

import com.payment.gateway.controller.DTO.CreateMerchantRequest;
import com.payment.gateway.controller.DTO.MerchantResponse;
import com.payment.gateway.entity.Merchant;
import com.payment.gateway.exception.MerchantNotFoundException;
import com.payment.gateway.service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/merchants")
public class MerchantController {

    private final MerchantService merchantService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MerchantResponse createMerchant(@RequestBody @Valid CreateMerchantRequest request) {
        Merchant m = merchantService.createMerchant(request);

        return toResponse(m);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MerchantResponse getMerchant(@PathVariable UUID id) {
        Merchant m = merchantService.findById(id)
                .orElseThrow(() -> new MerchantNotFoundException(id));

        return toResponse(m);
    }

    private static MerchantResponse toResponse(Merchant m) {
        return new MerchantResponse(
                m.getId(), m.getBusinessName(), m.getEmail(), m.getStatus(), m.getCreatedAt()
        );
    }

}
