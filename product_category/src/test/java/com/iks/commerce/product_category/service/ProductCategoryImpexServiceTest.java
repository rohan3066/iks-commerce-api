package com.iks.commerce.product_category.service;

import com.iks.commerce.product_category.entity.ProductCategory;
import com.iks.commerce.product_category.repository.ProductCategoryRepository;
import com.iks.commerce.product_category.utils.CsvUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductCategoryImpexServiceTest {

    @InjectMocks
    private ProductCategoryImpexService productCategoryImpexService;

    @Mock
    private ProductCategoryRepository productCategoryRepository;
    @BeforeEach
    void setUp() {
        // Create a mock for the repository
        productCategoryRepository = Mockito.mock(ProductCategoryRepository.class);

        // Create an instance of the service class
        productCategoryImpexService = new ProductCategoryImpexService();

        // Use ReflectionTestUtils to inject the mock repository into the private field
        ReflectionTestUtils.setField(productCategoryImpexService, "productCategoryRepository", productCategoryRepository);
    }

    @Test
    void testGetAllCategories() {
        ProductCategory category = new ProductCategory();
        when(productCategoryRepository.findAll()).thenReturn(List.of(category));

        List<ProductCategory> categories = productCategoryImpexService.getAllCategories();

        assertEquals(1, categories.size());
        verify(productCategoryRepository, times(1)).findAll();
    }

    @Test
    void testGetCategoryById_Success() {
        ProductCategory category = new ProductCategory();
        when(productCategoryRepository.findById("123")).thenReturn(Optional.of(category));

        ProductCategory result = productCategoryImpexService.getCategoryById("123");

        assertNotNull(result);
        verify(productCategoryRepository, times(1)).findById("123");
    }

    @Test
    void testGetCategoryById_NotFound() {
        when(productCategoryRepository.findById("123")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                productCategoryImpexService.getCategoryById("123"));

        assertTrue(exception.getMessage().contains("not found"));
        verify(productCategoryRepository, times(1)).findById("123");
    }

    @Test
    void testDeleteCategory_Success() {
        ProductCategory category = new ProductCategory();
        when(productCategoryRepository.findById("123")).thenReturn(Optional.of(category));

        productCategoryImpexService.deleteCategory("123");

        verify(productCategoryRepository, times(1)).delete(category);
    }

    @Test
    void testDeleteCategory_NotFound() {
        when(productCategoryRepository.findById("123")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                productCategoryImpexService.deleteCategory("123"));

        assertTrue(exception.getMessage().contains("not found"));
        verify(productCategoryRepository, times(1)).findById("123");
    }

    @Test
    void testProcessFile_CsvSuccess() throws IOException {
        // Mock the MultipartFile to simulate a CSV file
        MultipartFile file = new MockMultipartFile("test.csv", "test.csv", "text/csv", "name,description\nTest,Category".getBytes());

        // Prepare the mock data for parsing and saving
        ProductCategory category = new ProductCategory();
        List<ProductCategory> categories = List.of(category);

        // Mock the CsvUtil static method to return the parsed categories
        Mockito.mockStatic(CsvUtil.class);
        when(CsvUtil.parseCsv(any())).thenReturn(categories);

        // Mock the repository save method
        when(productCategoryRepository.saveAll(any())).thenReturn(categories);

        // Call the method under test
        List<ProductCategory> result = productCategoryImpexService.processFile(file);

        // Assert that the result contains the expected category
        assertEquals(1, result.size());

        // Verify that the repository's saveAll method was called once
        verify(productCategoryRepository, times(1)).saveAll(categories);
    }


    @Test
    void testProcessFile_UnsupportedFileType() {
        MultipartFile file = new MockMultipartFile("test.xml", "<test></test>".getBytes());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                productCategoryImpexService.processFile(file));

        assertTrue(exception.getMessage().contains("Unsupported file type"));
    }
}
