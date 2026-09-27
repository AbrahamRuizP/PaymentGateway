package com.payment.gateway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.gateway.config.JacksonConfig;
import com.payment.gateway.controller.advice.GlobalExceptionHandler;
import com.payment.gateway.controller.DTO.CreatePaymentMethodRequest;
import com.payment.gateway.entity.Customer;
import com.payment.gateway.entity.PaymentMethod;
import com.payment.gateway.entity.enums.PaymentMethodType;
import com.payment.gateway.exception.PaymentMethodNotFoundException;
import com.payment.gateway.service.PaymentMethodService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PaymentMethodController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({JacksonConfig.class, GlobalExceptionHandler.class})
class PaymentMethodControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PaymentMethodService paymentMethodService;

    @Test
    void shouldCreatePaymentMethod() throws Exception {
        UUID id = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        Instant createdAt = Instant.now();

        PaymentMethod paymentMethod = PaymentMethod.builder()
                .id(id)
                .customer(Customer.builder().id(customerId).build())
                .type(PaymentMethodType.CARD)
                .provider("stripe")
                .createdAt(createdAt)
                .updatedAt(createdAt)
                .build();

        CreatePaymentMethodRequest request = new CreatePaymentMethodRequest(
                PaymentMethodType.CARD, "stripe", "visa", "1234", "pm_1234", customerId
        );

        when(paymentMethodService.create(any(CreatePaymentMethodRequest.class)))
                .thenReturn(paymentMethod);

        mockMvc.perform(post("/payment-methods")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.type").value("CARD"))
                .andExpect(jsonPath("$.provider").value("stripe"))
                .andExpect(jsonPath("$.createdAt").value(createdAt.toString()));

        verify(paymentMethodService).create(any(CreatePaymentMethodRequest.class));
    }

    @Test
    void shouldGetPaymentMethod() throws Exception {
        UUID id = UUID.randomUUID();
        Instant createdAt = Instant.now();

        PaymentMethod paymentMethod = PaymentMethod.builder()
                .id(id)
                .type(PaymentMethodType.CARD)
                .provider("stripe")
                .createdAt(createdAt)
                .updatedAt(createdAt)
                .build();

        when(paymentMethodService.findById(id)).thenReturn(paymentMethod);

        mockMvc.perform(get("/payment-methods/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.type").value("CARD"))
                .andExpect(jsonPath("$.provider").value("stripe"))
                .andExpect(jsonPath("$.createdAt").value(createdAt.toString()));

        verify(paymentMethodService).findById(id);
    }

    @Test
    void shouldReturn404WhenPaymentMethodDoesNotExist() throws Exception {
        UUID id = UUID.randomUUID();
        when(paymentMethodService.findById(id)).thenThrow(new PaymentMethodNotFoundException(id));

        mockMvc.perform(get("/payment-methods/{id}", id))
                .andExpect(status().isNotFound());

        verify(paymentMethodService).findById(id);
    }

    @Test
    void shouldRejectPaymentMethodWhenRequiredFieldsAreInvalid() throws Exception {
        UUID customerId = UUID.randomUUID();
        CreatePaymentMethodRequest request = new CreatePaymentMethodRequest(
                null, "", "", "", "", customerId
        );

        mockMvc.perform(post("/payment-methods")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(paymentMethodService, never()).create(any(CreatePaymentMethodRequest.class));
    }
}
