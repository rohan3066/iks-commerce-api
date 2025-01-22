package com.iks.commerce.returnorder.service;

import com.iks.commerce.returnorder.exception.ResourceNotFound;
import com.iks.commerce.returnorder.entity.ReturnOrder;
import com.iks.commerce.returnorder.repository.ReturnOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The type Return order service test.
 */
class ReturnOrderServiceTest {

    @Mock
    private ReturnOrderRepository returnOrderRepo;

    @InjectMocks
    private ReturnOrderService returnOrderService;

    private ReturnOrder returnOrder;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        returnOrder = new ReturnOrder();
        returnOrder.setId("1");
        returnOrder.setCustomerId("CUST001");
        returnOrder.setPoNumber("PO12345");
        returnOrder.setBillingAddress("123 Street, City");
        returnOrder.setTotalAmount(500);
        returnOrder.setCreatedOn(new Date());
        returnOrder.setLastModifiedOn(new Date());
    }

    /**
     * Test add return order.
     */
    @Test
    void testAddReturnOrder() {
        when(returnOrderRepo.save(any(ReturnOrder.class))).thenReturn(returnOrder);

        ReturnOrder addedOrder = returnOrderService.addReturnOrder(returnOrder);
        assertNotNull(addedOrder);
        assertEquals("1", addedOrder.getId());

        verify(returnOrderRepo, times(1)).save(returnOrder);
    }

    /**
     * Test get return order by id success.
     */
    @Test
    void testGetReturnOrderById_Success() {
        when(returnOrderRepo.findById("1")).thenReturn(Optional.of(returnOrder));

        Optional<ReturnOrder> foundOrder = returnOrderService.getReturnOrderById("1");
        assertTrue(foundOrder.isPresent());
        assertEquals("1", foundOrder.get().getId());

        verify(returnOrderRepo, times(1)).findById("1");
    }

    /**
     * Test get return order by id not found.
     */
    @Test
    void testGetReturnOrderById_NotFound() {
        when(returnOrderRepo.findById("1")).thenReturn(Optional.empty());

        Optional<ReturnOrder> foundOrder = returnOrderService.getReturnOrderById("1");
        assertFalse(foundOrder.isPresent());

        verify(returnOrderRepo, times(1)).findById("1");
    }

    /**
     * Test update return order success.
     */
    @Test
    void testUpdateReturnOrder_Success() {
        when(returnOrderRepo.findById("1")).thenReturn(Optional.of(returnOrder));
        when(returnOrderRepo.save(any(ReturnOrder.class))).thenReturn(returnOrder);

        ReturnOrder updatedOrder = returnOrderService.updateReturnOrder("1", returnOrder);
        assertNotNull(updatedOrder);
        assertEquals("1", updatedOrder.getId());

        verify(returnOrderRepo, times(1)).findById("1");
        verify(returnOrderRepo, times(1)).save(returnOrder);
    }

    /**
     * Test update return order not found.
     */
    @Test
    void testUpdateReturnOrder_NotFound() {
        when(returnOrderRepo.findById("1")).thenReturn(Optional.empty());

        ResourceNotFound exception = assertThrows(ResourceNotFound.class, () ->
                returnOrderService.updateReturnOrder("1", returnOrder));

        assertEquals("Return order not found with ID: 1", exception.getMessage());
        verify(returnOrderRepo, times(1)).findById("1");
        verify(returnOrderRepo, times(0)).save(any(ReturnOrder.class));
    }

    /**
     * Test delete return order success.
     */
    @Test
    void testDeleteReturnOrder_Success() {
        when(returnOrderRepo.findById("1")).thenReturn(Optional.of(returnOrder));

        returnOrderService.deleteReturnOrder("1");

        verify(returnOrderRepo, times(1)).findById("1");
        verify(returnOrderRepo, times(1)).delete(returnOrder);
    }

    /**
     * Test delete return order not found.
     */
    @Test
    void testDeleteReturnOrder_NotFound() {
        when(returnOrderRepo.findById("1")).thenReturn(Optional.empty());

        ResourceNotFound exception = assertThrows(ResourceNotFound.class, () ->
                returnOrderService.deleteReturnOrder("1"));

        assertEquals("Order item is not found with ID: 1", exception.getMessage());
        verify(returnOrderRepo, times(1)).findById("1");
        verify(returnOrderRepo, times(0)).delete(any(ReturnOrder.class));
    }

    /**
     * Test get return order by customer id success.
     */
    @Test
    void testGetReturnOrderByCustomerId_Success() {
        List<ReturnOrder> orders = new ArrayList<>();
        orders.add(returnOrder);
        when(returnOrderRepo.findByCustomerId("CUST001")).thenReturn(orders);

        List<ReturnOrder> result = returnOrderService.getReturnOrderByCustomerId("CUST001");
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("CUST001", result.get(0).getCustomerId());

        verify(returnOrderRepo, times(1)).findByCustomerId("CUST001");
    }

    /**
     * Test get return orders by po number success.
     */
    @Test
    void testGetReturnOrdersByPoNumber_Success() {
        List<ReturnOrder> orders = new ArrayList<>();
        orders.add(returnOrder);
        when(returnOrderRepo.findByPoNumber("PO12345")).thenReturn(orders);

        List<ReturnOrder> result = returnOrderService.getReturnOrdersByPoNumber("PO12345");
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("PO12345", result.get(0).getPoNumber());

        verify(returnOrderRepo, times(1)).findByPoNumber("PO12345");
    }

    /**
     * Test get return orders by web store id success.
     */
    @Test
    void testGetReturnOrdersByWebStoreId_Success() {
        List<ReturnOrder> orders = new ArrayList<>();
        orders.add(returnOrder);
        when(returnOrderRepo.findByWebStoreId("WEB001")).thenReturn(orders);

        List<ReturnOrder> result = returnOrderService.getReturnOrdersByWebStoreId("WEB001");
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        verify(returnOrderRepo, times(1)).findByWebStoreId("WEB001");
    }
}
