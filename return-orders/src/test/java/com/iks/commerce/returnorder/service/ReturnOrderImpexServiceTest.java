package com.iks.commerce.returnorder.service;
import com.iks.commerce.returnorder.entity.ReturnOrder;
import com.iks.commerce.returnorder.exception.ResourceNotFound;
import com.iks.commerce.returnorder.repository.ReturnOrderRepository;
import com.iks.commerce.returnorder.utils.CsvUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReturnOrderImpexServiceTest {

    @Mock
    private ReturnOrderRepository returnOrderRepo;

    @InjectMocks
    private ReturnOrderImpexService returnOrderImpexService;

    private ReturnOrder returnOrder;
    @Mock
    private MultipartFile mockFile;

    @BeforeEach
    void setUp() {
        returnOrder = new ReturnOrder();
        returnOrder.setId("123");
        returnOrder.setPoNumber("PO12345");
        returnOrder.setCustomerId("CUST123");
        returnOrder.setWebStoreId("WS123");
    }

    @Test
    void testGetReturnOrderById_WhenFound() {
        when(returnOrderRepo.findById("123")).thenReturn(Optional.of(returnOrder));
        Optional<ReturnOrder> result = returnOrderImpexService.getReturnOrderById("123");
        assertTrue(result.isPresent());
        assertEquals("123", result.get().getId());
    }

    @Test
    void testGetReturnOrderById_WhenNotFound() {
        when(returnOrderRepo.findById("999")).thenReturn(Optional.empty());
        Optional<ReturnOrder> result = returnOrderImpexService.getReturnOrderById("999");
        assertFalse(result.isPresent());
    }

    @Test
    void testDeleteReturnOrder_WhenExists() {
        when(returnOrderRepo.findById("123")).thenReturn(Optional.of(returnOrder));
        doNothing().when(returnOrderRepo).delete(any());
        assertDoesNotThrow(() -> returnOrderImpexService.deleteReturnOrder("123"));
    }

    @Test
    void testDeleteReturnOrder_WhenNotFound() {
        when(returnOrderRepo.findById("999")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFound.class, () -> returnOrderImpexService.deleteReturnOrder("999"));
    }

    @Test
    void testGetReturnOrdersByCustomerId() {
        when(returnOrderRepo.findByCustomerId("CUST123")).thenReturn(List.of(returnOrder));
        List<ReturnOrder> result = returnOrderImpexService.getReturnOrderByCustomerId("CUST123");
        assertFalse(result.isEmpty());
        assertEquals("CUST123", result.get(0).getCustomerId());
    }

    @Test
    void testProcessReturnOrderFile_Csv() throws Exception {
        // Arrange: Mock CSV content
        String csvData = "orderId,customerId,totalAmount\n123,CUST001,500.00";
        InputStream csvInputStream = new ByteArrayInputStream(csvData.getBytes(StandardCharsets.UTF_8));

        when(mockFile.getOriginalFilename()).thenReturn("test.csv");
        when(mockFile.getInputStream()).thenReturn(csvInputStream);

        // Mock CSV parsing utility
        ReturnOrder mockOrder = new ReturnOrder();
        mockOrder.setId("123");
        mockOrder.setCustomerId("CUST001");
        mockOrder.setTotalAmount(500.00);

        List<ReturnOrder> mockOrders = List.of(mockOrder);

        try (MockedStatic<CsvUtil> csvUtilMockedStatic = mockStatic(CsvUtil.class)) {
            csvUtilMockedStatic.when(() -> CsvUtil.parseCsv(any(InputStream.class)))
                    .thenReturn(mockOrders);

            // Act
            List<ReturnOrder> result = returnOrderImpexService.processReturnOrderFile(mockFile);

            // Assert
            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals("123", result.get(0).getId());
            assertEquals("CUST001", result.get(0).getCustomerId());
            assertEquals(500.00, result.get(0).getTotalAmount());
        }
    }
}
