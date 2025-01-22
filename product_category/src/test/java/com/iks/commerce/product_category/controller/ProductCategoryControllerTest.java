package com.iks.commerce.product_category.controller;

import com.iks.commerce.product_category.controller.v1.ProductCategoryController;
import com.iks.commerce.product_category.entity.ProductCategory;
import com.iks.commerce.product_category.service.ProductCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ProductCategoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductCategoryService productCategoryService;

    @InjectMocks
    private ProductCategoryController productCategoryController;

    private ProductCategory category;

    @BeforeEach
    void setUp() {
        // Initialize MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(productCategoryController).build();

        // Use setter methods to set the values for the ProductCategory instance
        category = new ProductCategory();
        category.setId("1");
        category.setName("Electronics");
        category.setDescription("All electronics products");

        // Now no need to manually set the mock service as @InjectMocks will handle it
    }

    @Test
    void createCategory() throws Exception {
        // Define a ProductCategory instance with all required fields
        ProductCategory category = new ProductCategory();
        category.setId("1");
        category.setName("Electronics");
        category.setDescription("All electronics products");
        category.setMetaDescription("Electronics meta description");
        category.setSeoKeywords("electronics, gadgets, technology");
        category.setProductIds(Arrays.asList("p1", "p2", "p3"));
        category.setModifiedBy("admin");
        category.setCatalogId("cat123");
        category.setSubcategoryIds(Arrays.asList("subcat1", "subcat2"));
        category.setCode("elec");
        category.setCreatedBy("admin");
        category.setVisibleToUserGroupIds(Arrays.asList("group1", "group2"));  // Add this line to set the visibleToUserGroupIds field

        // Define the behavior for the mock service
        when(productCategoryService.createCategory(any(ProductCategory.class))).thenReturn(category);

        // Perform POST request and validate response
        mockMvc.perform(post("/v1/api-categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"
                                + "\"name\":\"Electronics\","
                                + "\"description\":\"All electronics products\","
                                + "\"metaDescription\":\"Electronics meta description\","
                                + "\"seoKeywords\":\"electronics, gadgets, technology\","
                                + "\"productIds\":[\"p1\",\"p2\",\"p3\"],"
                                + "\"modifiedBy\":\"admin\","
                                + "\"catalogId\":\"cat123\","
                                + "\"subcategoryIds\":[\"subcat1\",\"subcat2\"],"
                                + "\"code\":\"elec\","
                                + "\"createdBy\":\"admin\","
                                + "\"visibleToUserGroupIds\":[\"group1\",\"group2\"]"  // Include visibleToUserGroupIds in request
                                + "}"))
                .andExpect(status().isCreated())  // Check for 201 status
                .andExpect(jsonPath("$.name").value("Electronics"))
                .andExpect(jsonPath("$.description").value("All electronics products"))
                .andExpect(jsonPath("$.metaDescription").value("Electronics meta description"))
                .andExpect(jsonPath("$.seoKeywords").value("electronics, gadgets, technology"))
                .andExpect(jsonPath("$.productIds[0]").value("p1"))
                .andExpect(jsonPath("$.modifiedBy").value("admin"))
                .andExpect(jsonPath("$.catalogId").value("cat123"))
                .andExpect(jsonPath("$.subcategoryIds[0]").value("subcat1"))
                .andExpect(jsonPath("$.code").value("elec"))
                .andExpect(jsonPath("$.createdBy").value("admin"))
                .andExpect(jsonPath("$.visibleToUserGroupIds[0]").value("group1"))  // Assert the value of visibleToUserGroupIds
                .andExpect(jsonPath("$.visibleToUserGroupIds[1]").value("group2"));
    }


    @Test
    void getAllCategories() throws Exception {
        List<ProductCategory> categories = Arrays.asList(category);
        when(productCategoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/v1/api-categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Electronics"))
                .andExpect(jsonPath("$[0].description").value("All electronics products"));
    }

    @Test
    void searchCategories() throws Exception {
        List<ProductCategory> categories = Arrays.asList(category);
        when(productCategoryService.searchCategories("Electronics")).thenReturn(categories);

        mockMvc.perform(get("/v1/api-categories/search")
                        .param("searchText", "Electronics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Electronics"));
    }

    @Test
    void getCategoryById() throws Exception {
        when(productCategoryService.getCategoryById("1")).thenReturn(category);

        mockMvc.perform(get("/v1/api-categories/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Electronics"))
                .andExpect(jsonPath("$.description").value("All electronics products"));
    }

    @Test
    void updateCategory() throws Exception {
        ProductCategory updatedCategory = new ProductCategory();
        updatedCategory.setId("1");
        updatedCategory.setName("Updated Electronics");
        updatedCategory.setDescription("Updated description");
        updatedCategory.setSeoKeywords("updated, electronics, gadgets");
        updatedCategory.setProductIds(Arrays.asList("p1", "p2", "p3"));
        updatedCategory.setCode("updated-elec");
        updatedCategory.setMetaDescription("Updated meta description");
        updatedCategory.setCreatedBy("admin");
        updatedCategory.setModifiedBy("admin");
        updatedCategory.setVisibleToUserGroupIds(Arrays.asList("group1", "group2"));
        updatedCategory.setSubcategoryIds(Arrays.asList("subcat1", "subcat2"));
        updatedCategory.setCatalogId("updated-cat123");

        // Mocking service behavior
        when(productCategoryService.updateCategory(eq("1"), any(ProductCategory.class))).thenReturn(updatedCategory);

        // Perform PUT request and validate response
        mockMvc.perform(put("/v1/api-categories/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"
                                + "\"name\":\"Updated Electronics\","
                                + "\"description\":\"Updated description\","
                                + "\"seoKeywords\":\"updated, electronics, gadgets\","
                                + "\"productIds\":[\"p1\",\"p2\",\"p3\"],"
                                + "\"code\":\"updated-elec\","
                                + "\"metaDescription\":\"Updated meta description\","
                                + "\"createdBy\":\"admin\","
                                + "\"modifiedBy\":\"admin\","
                                + "\"visibleToUserGroupIds\":[\"group1\",\"group2\"],"
                                + "\"subcategoryIds\":[\"subcat1\",\"subcat2\"],"
                                + "\"catalogId\":\"updated-cat123\""
                                + "}"))
                .andExpect(status().isOk())  // Check for 200 OK status
                .andExpect(jsonPath("$.name").value("Updated Electronics"))
                .andExpect(jsonPath("$.description").value("Updated description"))
                .andExpect(jsonPath("$.seoKeywords").value("updated, electronics, gadgets"))
                .andExpect(jsonPath("$.productIds[0]").value("p1"))
                .andExpect(jsonPath("$.code").value("updated-elec"))
                .andExpect(jsonPath("$.metaDescription").value("Updated meta description"))
                .andExpect(jsonPath("$.createdBy").value("admin"))
                .andExpect(jsonPath("$.modifiedBy").value("admin"))
                .andExpect(jsonPath("$.visibleToUserGroupIds[0]").value("group1"))
                .andExpect(jsonPath("$.subcategoryIds[0]").value("subcat1"))
                .andExpect(jsonPath("$.catalogId").value("updated-cat123"));
    }


    @Test
    void deleteCategory() throws Exception {
        doNothing().when(productCategoryService).deleteCategory("1");

        mockMvc.perform(delete("/v1/api-categories/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Category deleted successfully"));
    }

    @Test
    void getProductIdsByCategoryCode() throws Exception {
        List<String> productIds = Arrays.asList("p1", "p2", "p3");
        when(productCategoryService.getProductIdsByCategoryCode("Electronics")).thenReturn(productIds);

        mockMvc.perform(get("/v1/api-categories/{categoryCode}/products", "Electronics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("p1"))
                .andExpect(jsonPath("$[1]").value("p2"))
                .andExpect(jsonPath("$[2]").value("p3"));
    }
}
