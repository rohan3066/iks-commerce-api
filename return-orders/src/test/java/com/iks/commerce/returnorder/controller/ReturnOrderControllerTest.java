package com.iks.commerce.returnorder.controller;

import com.iks.commerce.returnorder.controller.v1.ReturnOrderController;
import com.iks.commerce.returnorder.entity.ReturnOrder;
import com.iks.commerce.returnorder.exception.CustomMethodArgumentNotValidException;
import com.iks.commerce.returnorder.exception.ResourceNotFound;
import com.iks.commerce.returnorder.service.ReturnOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

public class ReturnOrderControllerTest {

    @InjectMocks
    private ReturnOrderController returnOrderController;

    @Mock
    private ReturnOrderService returnOrderService;

    @Mock
    private BindingResult bindingResult;

    private ReturnOrder returnOrder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        returnOrder = new ReturnOrder();
        returnOrder.setId("1");
        returnOrder.setCustomerId("customer1");
        returnOrder.setPoNumber("PO123");
    }

    @Test
    public void testAddReturnOrder_Success() throws CustomMethodArgumentNotValidException {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(returnOrderService.addReturnOrder(any(ReturnOrder.class))).thenReturn(returnOrder);

        ResponseEntity<?> response = returnOrderController.addReturnOrder(returnOrder, bindingResult);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(returnOrderService, times(1)).addReturnOrder(any(ReturnOrder.class));
    }

    @Test
    public void testAddReturnOrder_BindingResultError() throws CustomMethodArgumentNotValidException {
        when(bindingResult.hasErrors()).thenReturn(true);

        assertThrows(CustomMethodArgumentNotValidException.class, () -> {
            returnOrderController.addReturnOrder(returnOrder, bindingResult);
        });
    }

    @Test
    public void testGetReturnOrderById_Found() throws ResourceNotFound {
        when(returnOrderService.getReturnOrderById("1")).thenReturn(Optional.of(returnOrder));

        ResponseEntity<?> response = returnOrderController.getReturnOrderById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(returnOrderService, times(1)).getReturnOrderById("1");
    }

    @Test
    public void testGetReturnOrderById_NotFound() throws ResourceNotFound {
        when(returnOrderService.getReturnOrderById("1")).thenReturn(Optional.empty());

        ResourceNotFound exception = assertThrows(ResourceNotFound.class, () -> {
            returnOrderController.getReturnOrderById("1");
        });

        assertEquals("Return order with ID 1 not found", exception.getMessage());
    }

    @Test
    public void testUpdateReturnOrder_Success() throws CustomMethodArgumentNotValidException, ResourceNotFound {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(returnOrderService.updateReturnOrder(eq("1"), any(ReturnOrder.class))).thenReturn(returnOrder);

        ResponseEntity<?> response = returnOrderController.updateReturnOrder("1", returnOrder, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(returnOrderService, times(1)).updateReturnOrder(eq("1"), any(ReturnOrder.class));
    }

    @Test
    public void testUpdateReturnOrder_BindingResultError() throws CustomMethodArgumentNotValidException {
        when(bindingResult.hasErrors()).thenReturn(true);

        assertThrows(CustomMethodArgumentNotValidException.class, () -> {
            returnOrderController.updateReturnOrder("1", returnOrder, bindingResult);
        });
    }

    @Test
    public void testDeleteReturnOrder_Success() {
        doNothing().when(returnOrderService).deleteReturnOrder("1");

        ResponseEntity<?> response = returnOrderController.deleteReturnOrder("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(returnOrderService, times(1)).deleteReturnOrder("1");
    }

    @Test
    public void testDeleteReturnOrder_NotFound() {
        doThrow(new ResourceNotFound("Order item is not found with ID: 1")).when(returnOrderService).deleteReturnOrder("1");

        ResponseEntity<?> response = returnOrderController.deleteReturnOrder("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Error: Order item is not found with ID: 1"));
    }

    @Test
    public void testGetReturnOrdersByCustomerId_Found() throws ResourceNotFound {
        when(returnOrderService.getReturnOrderByCustomerId("customer1")).thenReturn(List.of(returnOrder));

        ResponseEntity<?> response = returnOrderController.getReturnOrdersByCustomerId("customer1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(returnOrderService, times(1)).getReturnOrderByCustomerId("customer1");
    }

    @Test
    public void testGetReturnOrdersByCustomerId_NotFound() throws ResourceNotFound {
        when(returnOrderService.getReturnOrderByCustomerId("customer1")).thenReturn(List.of());

        ResourceNotFound exception = assertThrows(ResourceNotFound.class, () -> {
            returnOrderController.getReturnOrdersByCustomerId("customer1");
        });

        assertEquals("Return order with customerId customer1 not found", exception.getMessage());
    }

    @Test
    public void testGetReturnOrdersByPoNumber_Found() throws ResourceNotFound {
        when(returnOrderService.getReturnOrdersByPoNumber("PO123")).thenReturn(List.of(returnOrder));

        ResponseEntity<?> response = returnOrderController.getReturnOrdersByPoNumber("PO123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(returnOrderService, times(1)).getReturnOrdersByPoNumber("PO123");
    }

    @Test
    public void testGetReturnOrdersByPoNumber_NotFound() throws ResourceNotFound {
        when(returnOrderService.getReturnOrdersByPoNumber("PO123")).thenReturn(List.of());

        ResourceNotFound exception = assertThrows(ResourceNotFound.class, () -> {
            returnOrderController.getReturnOrdersByPoNumber("PO123");
        });

        assertEquals("Return order with poNumber PO123 not found", exception.getMessage());
    }

    @Test
    public void testGetReturnOrdersByWebStoreId_Found() {
        when(returnOrderService.getReturnOrdersByWebStoreId("store1")).thenReturn(List.of(returnOrder));

        ResponseEntity<?> response = returnOrderController.getReturnOrdersByWebStoreId("store1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(returnOrderService, times(1)).getReturnOrdersByWebStoreId("store1");
    }

    @Test
    public void testGetReturnOrdersByWebStoreId_NotFound() {
        when(returnOrderService.getReturnOrdersByWebStoreId("store1")).thenReturn(List.of());

        ResourceNotFound exception = assertThrows(ResourceNotFound.class, () -> {
            returnOrderController.getReturnOrdersByWebStoreId("store1");
        });

        assertEquals("Return order with webStoreId store1 not found", exception.getMessage());
    }
}
