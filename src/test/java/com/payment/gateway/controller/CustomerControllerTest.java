package com.payment.gateway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.gateway.controller.DTO.CreateCustomerRequest;
import com.payment.gateway.controller.DTO.CustomerResponse;
import com.payment.gateway.entity.Customer;
import com.payment.gateway.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
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

@WebMvcTest(controllers = CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CustomerControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    @Test
    void shouldCreateCustomer() throws Exception {
        UUID id = UUID.randomUUID();

        Customer customer = Customer.builder()
                .id(id)
                .firstName("John")
                .lastName("Doe")
                .description("Testing Customer Controller")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        CreateCustomerRequest request = new CreateCustomerRequest(
                customer.getFirstName(), customer.getLastName(), customer.getDescription()
        );

        when(customerService.createCustomer(any(CreateCustomerRequest.class)))
                .thenReturn(customer);

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));

        verify(customerService).createCustomer(any(CreateCustomerRequest.class));
    }

    @Test
    void shouldGetCustomer() throws Exception {
        UUID id = UUID.randomUUID();

        Customer customer = Customer.builder()
                .id(id)
                .firstName("John")
                .lastName("Doe")
                .description("Testing Customer Controller")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        CustomerResponse response = new CustomerResponse(
                id, "John", "Doe", customer.getCreatedAt(), customer.getUpdatedAt()
        );

        when(customerService.findByIdAndDeletedFalse(id))
                .thenReturn(Optional.of(customer));

        mockMvc.perform(get("/customers/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));

        verify(customerService).findByIdAndDeletedFalse(id);
    }

    @Test
    void shouldReturn404WhenCustomerDoesNotExists() throws Exception {
        UUID id = UUID.randomUUID();

        when(customerService.findByIdAndDeletedFalse(id))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/customers/{id}", id))
                .andExpect(status().isNotFound());

        verify(customerService).findByIdAndDeletedFalse(id);
    }

    @Test
    void shouldDeleteCustomer() throws Exception {
        UUID id = UUID.randomUUID();

        when(customerService.deleteCustomer(id))
                .thenReturn(true);

        mockMvc.perform(delete("/customers/{id}", id))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(customerService).deleteCustomer(id);
    }

    @Test
    void shouldReturn404WhenDeletingNonExistingCustomer() throws Exception {
        UUID id = UUID.randomUUID();

        when(customerService.deleteCustomer(id))
                .thenReturn(false);

        mockMvc.perform(delete("/customers/{id}", id))
                .andExpect(status().isNotFound());

        verify(customerService).deleteCustomer(id);
    }

    @Test
    void shouldRejectCustomerWhenFistNameBlank() throws Exception {
        CreateCustomerRequest request = new CreateCustomerRequest(
                "", "Done", ""
        );

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(customerService, never()).createCustomer(any(CreateCustomerRequest.class));
    }
}
