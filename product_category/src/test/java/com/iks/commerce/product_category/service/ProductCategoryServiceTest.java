package com.iks.commerce.product_category.service;

import com.iks.commerce.product_category.constant.ProductCategoryConstants;
import com.iks.commerce.product_category.entity.ProductCategory;
import com.iks.commerce.product_category.repository.ProductCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The type Product category service test.
 */
class ProductCategoryServiceTest {

    @InjectMocks
    private ProductCategoryService productCategoryService;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    private ProductCategory sampleCategory;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Sample category
        sampleCategory = new ProductCategory();
        sampleCategory.setCode("C001");
        sampleCategory.setName("Electronics");
        sampleCategory.setDescription("Category for electronic items");
        sampleCategory.setProductIds(List.of("P001", "P002"));
        sampleCategory.setCreatedOn(new Date());
        sampleCategory.setModifiedOn(new Date());
    }

    /**
     * Create category should save and return category.
     */
    @Test
    void createCategory_ShouldSaveAndReturnCategory() {
        when(productCategoryRepository.findByName(sampleCategory.getName())).thenReturn(Optional.empty());
        when(productCategoryRepository.save(any(ProductCategory.class))).thenReturn(sampleCategory);

        ProductCategory createdCategory = productCategoryService.createCategory(sampleCategory);

        assertNotNull(createdCategory);
        assertEquals("Electronics", createdCategory.getName());
        verify(productCategoryRepository, times(1)).save(sampleCategory);
    }

    /**
     * Create category should throw exception when name is duplicate.
     */
    @Test
    void createCategory_ShouldThrowException_WhenNameIsDuplicate() {
        when(productCategoryRepository.findByName(sampleCategory.getName())).thenReturn(Optional.of(sampleCategory));

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                productCategoryService.createCategory(sampleCategory));

        assertEquals(ProductCategoryConstants.DUPLICATE_CATEGORY_NAME, exception.getMessage());
        verify(productCategoryRepository, never()).save(sampleCategory);
    }

    /**
     * Gets all categories should return list of categories.
     */
    @Test
    void getAllCategories_ShouldReturnListOfCategories() {
        when(productCategoryRepository.findAll()).thenReturn(List.of(sampleCategory));

        List<ProductCategory> categories = productCategoryService.getAllCategories();

        assertNotNull(categories);
        assertEquals(1, categories.size());
        assertEquals("Electronics", categories.get(0).getName());
        verify(productCategoryRepository, times(1)).findAll();
    }

    /**
     * Gets category by id should return category.
     */
    @Test
    void getCategoryById_ShouldReturnCategory() {
        when(productCategoryRepository.findById("C001")).thenReturn(Optional.of(sampleCategory));

        ProductCategory category = productCategoryService.getCategoryById("C001");

        assertNotNull(category);
        assertEquals("Electronics", category.getName());
        verify(productCategoryRepository, times(1)).findById("C001");
    }

    /**
     * Gets category by id should throw exception when category not found.
     */
    @Test
    void getCategoryById_ShouldThrowException_WhenCategoryNotFound() {
        when(productCategoryRepository.findById("C002")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                productCategoryService.getCategoryById("C002"));

        assertEquals(ProductCategoryConstants.CATEGORY_NOT_FOUND + "C002", exception.getMessage());
        verify(productCategoryRepository, times(1)).findById("C002");
    }

    /**
     * Update category should update and return category.
     */
    @Test
    void updateCategory_ShouldUpdateAndReturnCategory() {
        ProductCategory updatedCategoryDetails = new ProductCategory();
        updatedCategoryDetails.setName("Updated Electronics");
        updatedCategoryDetails.setDescription("Updated description");

        when(productCategoryRepository.findById("C001")).thenReturn(Optional.of(sampleCategory));
        when(productCategoryRepository.save(any(ProductCategory.class))).thenReturn(sampleCategory);

        ProductCategory updatedCategory = productCategoryService.updateCategory("C001", updatedCategoryDetails);

        assertNotNull(updatedCategory);
        assertEquals("Updated Electronics", updatedCategory.getName());
        verify(productCategoryRepository, times(1)).save(sampleCategory);
    }

    /**
     * Update category should throw exception when category not found.
     */
    @Test
    void updateCategory_ShouldThrowException_WhenCategoryNotFound() {
        when(productCategoryRepository.findById("C002")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                productCategoryService.updateCategory("C002", sampleCategory));

        assertEquals(ProductCategoryConstants.CATEGORY_NOT_FOUND + "C002", exception.getMessage());
        verify(productCategoryRepository, never()).save(any(ProductCategory.class));
    }

    /**
     * Delete category should delete category.
     */
    @Test
    void deleteCategory_ShouldDeleteCategory() {
        when(productCategoryRepository.findById("C001")).thenReturn(Optional.of(sampleCategory));
        doNothing().when(productCategoryRepository).delete(sampleCategory);

        productCategoryService.deleteCategory("C001");

        verify(productCategoryRepository, times(1)).delete(sampleCategory);
    }

    /**
     * Delete category should throw exception when category not found.
     */
    @Test
    void deleteCategory_ShouldThrowException_WhenCategoryNotFound() {
        when(productCategoryRepository.findById("C002")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                productCategoryService.deleteCategory("C002"));

        assertEquals(ProductCategoryConstants.CATEGORY_NOT_FOUND + "C002", exception.getMessage());
        verify(productCategoryRepository, never()).delete(any(ProductCategory.class));
    }

    /**
     * Gets product ids by category code should return product ids.
     */
    @Test
    void getProductIdsByCategoryCode_ShouldReturnProductIds() {
        when(productCategoryRepository.findById("C001")).thenReturn(Optional.of(sampleCategory));

        List<String> productIds = productCategoryService.getProductIdsByCategoryCode("C001");

        assertNotNull(productIds);
        assertEquals(2, productIds.size());
        assertEquals("P001", productIds.get(0));
        verify(productCategoryRepository, times(1)).findById("C001");
    }

    /**
     * Gets product ids by category code should throw exception when category not found.
     */
    @Test
    void getProductIdsByCategoryCode_ShouldThrowException_WhenCategoryNotFound() {
        when(productCategoryRepository.findById("C002")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                productCategoryService.getProductIdsByCategoryCode("C002"));

        assertEquals(ProductCategoryConstants.CATEGORY_NOT_FOUND + "C002", exception.getMessage());
        verify(productCategoryRepository, times(1)).findById("C002");
    }
}
