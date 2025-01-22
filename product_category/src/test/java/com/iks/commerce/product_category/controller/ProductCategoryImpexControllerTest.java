package com.iks.commerce.product_category.controller;

import com.iks.commerce.product_category.controller.v1.ProductCategoryImpexController;
import com.iks.commerce.product_category.entity.ProductCategory;
import com.iks.commerce.product_category.service.ProductCategoryImpexService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ProductCategoryImpexControllerTest {

    @Mock
    private ProductCategoryImpexService productCategoryImpexService;

    @InjectMocks
    private ProductCategoryImpexController productCategoryImpexController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(productCategoryImpexController).build();
    }

    @Test
    public void testUploadFile_Success() throws Exception {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCode("001");
        productCategory.setName("Category 1");

        List<ProductCategory> productCategories = Arrays.asList(productCategory);

        MockMultipartFile file = new MockMultipartFile("file", "categories.csv", "text/csv", "mock data".getBytes());

        when(productCategoryImpexService.processFile(file)).thenReturn(productCategories);

        mockMvc.perform(multipart("/v1/api-product-impex-categories/upload")
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    public void testUploadFile_Failure() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "categories.csv", "text/csv", "mock data".getBytes());

        when(productCategoryImpexService.processFile(file)).thenThrow(new RuntimeException("File processing error"));

        mockMvc.perform(multipart("/v1/api-product-impex-categories/upload")
                        .file(file))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllCategories() throws Exception {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCode("001");
        productCategory.setName("Category 1");

        List<ProductCategory> categories = Arrays.asList(productCategory);

        when(productCategoryImpexService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/v1/api-product-impex-categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    public void testSearchCategories() throws Exception {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCode("001");
        productCategory.setName("Category 1");

        List<ProductCategory> categories = Arrays.asList(productCategory);

        when(productCategoryImpexService.searchCategories("Category")).thenReturn(categories);

        mockMvc.perform(get("/v1/api-product-impex-categories/search")
                        .param("searchText", "Category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    public void testGetCategoryById() throws Exception {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCode("001");
        productCategory.setName("Category 1");

        when(productCategoryImpexService.getCategoryById("001")).thenReturn(productCategory);

        mockMvc.perform(get("/v1/api-product-impex-categories/001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("001"))
                .andExpect(jsonPath("$.name").value("Category 1"));
    }

    @Test
    public void testUpdateProductCategoryById_Success() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "categories.csv", "text/csv", "mock data".getBytes());

        when(productCategoryImpexService.updateProductCategoryById("001", file)).thenReturn(1);

        mockMvc.perform(multipart("/v1/api-product-impex-categories/update/001")
                        .file(file)  // attach the file to the request
                        .contentType("multipart/form-data")  // set content type explicitly
                        .with(request -> {
                            request.setMethod("PUT"); // explicitly set the method to PUT
                            return request;
                        }))
                .andExpect(status().isOk()) // expecting 200 OK
                .andExpect(content().string("1 product category updated successfully."));
    }



    @Test
    public void testDeleteCategory() throws Exception {
        doNothing().when(productCategoryImpexService).deleteCategory("001");

        mockMvc.perform(delete("/v1/api-product-impex-categories/001"))
                .andExpect(status().isOk())
                .andExpect(content().string("Category deleted successfully"));
    }

    @Test
    public void testGetProductIds() throws Exception {
        List<String> productIds = Arrays.asList("prod001", "prod002");

        when(productCategoryImpexService.getProductIdsByCategoryCode("001")).thenReturn(productIds);

        mockMvc.perform(get("/v1/api-product-impex-categories/001/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0]").value("prod001"))
                .andExpect(jsonPath("$[1]").value("prod002"));
    }
}
