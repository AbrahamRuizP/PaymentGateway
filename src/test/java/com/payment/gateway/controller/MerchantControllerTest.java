package com.payment.gateway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.gateway.config.JacksonConfig;
import com.payment.gateway.controller.DTO.CreateMerchantRequest;
import com.payment.gateway.controller.DTO.MerchantResponse;
import com.payment.gateway.entity.Merchant;
import com.payment.gateway.service.MerchantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MerchantController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(JacksonConfig.class)
public class MerchantControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MerchantService merchantService;

    @Test
    void shouldCreateMerchant() throws Exception {
        UUID id = UUID.randomUUID();

        Merchant merchant = Merchant.builder()
                .id(id)
                .businessName("John")
                .email("johndone@gmail.com")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        CreateMerchantRequest request = new CreateMerchantRequest(
                merchant.getBusinessName(), merchant.getEmail()
        );

        when(merchantService.create(any(CreateMerchantRequest.class)))
                .thenReturn(merchant);

        mockMvc.perform(post("/merchants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.businessName").value("John"))
                .andExpect(jsonPath("$.email").value("johndone@gmail.com"));

        verify(merchantService).create(any(CreateMerchantRequest.class));
    }

    @Test
    void shouldGetMerchant() throws Exception {
        UUID id = UUID.randomUUID();

        Merchant merchant = Merchant.builder()
                .id(id)
                .businessName("John")
                .email("johndone@gmail.com")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        when(merchantService.findById(id))
                .thenReturn(Optional.of(merchant));

        mockMvc.perform(get("/merchants/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.businessName").value("John"))
                .andExpect(jsonPath("$.email").value("johndone@gmail.com"));

        verify(merchantService).findById(id);
    }

    @Test
    void shouldReturn404WhenMerchantDoesNotExist() throws Exception {
        UUID id = UUID.randomUUID();

        when(merchantService.findById(id))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/merchants/{id}", id))
                .andExpect(status().isNotFound());

        verify(merchantService).findById(id);
    }

    @Test
    void shouldDeleteMerchant() throws Exception {
        UUID id = UUID.randomUUID();

        when(merchantService.softDeleteById(id))
                .thenReturn(true);

        mockMvc.perform(delete("/merchants/{id}", id))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(merchantService).softDeleteById(id);
    }

    @Test
    void shouldReturn404WhenDeletingNonExistingMerchant() throws Exception {
        UUID id = UUID.randomUUID();

        when(merchantService.softDeleteById(id))
                .thenReturn(false);

        mockMvc.perform(delete("/merchants/{id}", id))
                .andExpect(status().isNotFound());

        verify(merchantService).softDeleteById(id);
    }

    @Test
    void shouldRejectMerchantWhenBusinessNameBlank() throws Exception {
        CreateMerchantRequest request = new CreateMerchantRequest(
                "", "johndone@gmail.com"
        );

        mockMvc.perform(post("/merchants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(merchantService, never()).create(any(CreateMerchantRequest.class));
    }
}
