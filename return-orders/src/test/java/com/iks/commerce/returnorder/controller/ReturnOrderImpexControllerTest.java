package com.iks.commerce.returnorder.controller;

import com.iks.commerce.returnorder.controller.v1.ReturnOrderImpexController;
import com.iks.commerce.returnorder.entity.ReturnOrder;
import com.iks.commerce.returnorder.service.ReturnOrderImpexService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ReturnOrderImpexControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReturnOrderImpexService returnOrderService;

    @InjectMocks
    private ReturnOrderImpexController returnOrderController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(returnOrderController).build();
    }

    @Test
    void testAddReturnOrder_Success() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "content".getBytes());
        ReturnOrder mockOrder = new ReturnOrder();
        mockOrder.setId("123");

        when(returnOrderService.processReturnOrderFile(any())).thenReturn(List.of(mockOrder));

        mockMvc.perform(multipart("/v1/impex-api-return-order/upload").file(file))
                .andExpect(status().isOk());
    }

    @Test
    void testGetReturnOrderById_Success() throws Exception {
        ReturnOrder mockOrder = new ReturnOrder();
        mockOrder.setId("123");

        when(returnOrderService.getReturnOrderById("123")).thenReturn(Optional.of(mockOrder));

        mockMvc.perform(get("/v1/impex-api-return-order/123"))
                .andExpect(status().isOk());
    }


    @Test
    void testUpdateReturnOrder_Success() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "content".getBytes());
        ReturnOrder mockOrder = new ReturnOrder();
        mockOrder.setId("123");

        when(returnOrderService.updateReturnOrderFromFile(anyString(), any())).thenReturn(mockOrder);

        mockMvc.perform(multipart(HttpMethod.PUT, "/v1/impex-api-return-order/update/123")
                        .file(file))
                .andExpect(status().isOk()); // Expecting 200 OK
    }


    @Test
    void testDeleteReturnOrder_Success() throws Exception {
        doNothing().when(returnOrderService).deleteReturnOrder("123");

        mockMvc.perform(delete("/v1/impex-api-return-order/123"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetReturnOrdersByCustomerId_Success() throws Exception {
        ReturnOrder mockOrder = new ReturnOrder();
        mockOrder.setCustomerId("CUST123");

        when(returnOrderService.getReturnOrderByCustomerId("CUST123")).thenReturn(List.of(mockOrder));

        mockMvc.perform(get("/v1/impex-api-return-order/customerId/CUST123"))
                .andExpect(status().isOk());
    }
}
