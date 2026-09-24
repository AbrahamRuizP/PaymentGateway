package com.payment.gateway.service;

import com.payment.gateway.controller.DTO.CreateMerchantRequest;
import com.payment.gateway.entity.Merchant;
import com.payment.gateway.entity.enums.MerchantStatus;
import com.payment.gateway.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MerchantService {

    private final MerchantRepository merchantRepository;

    public Optional<Merchant> findById(UUID id) {
        return merchantRepository.findById(id);
    }

    public Merchant create(CreateMerchantRequest request) {
        Merchant merchant = buildMerchant(request);
        return merchantRepository.save(merchant);
    }

    public boolean softDeleteById(UUID id) {
        if (id == null) {
            return false;
        }

        return merchantRepository.softDeleteById(id) > 0;
    }

    private static Merchant buildMerchant(CreateMerchantRequest request) {
        return Merchant.builder()
                .email(request.email())
                .businessName(request.businessName())
                .status(MerchantStatus.CREATED)
                .build();
    }
}
