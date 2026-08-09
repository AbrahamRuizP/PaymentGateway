package com.payment.gateway.service;

import com.payment.gateway.config.properties.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;

    public String issuer() {
        return jwtProperties.issuer();
    }
}
