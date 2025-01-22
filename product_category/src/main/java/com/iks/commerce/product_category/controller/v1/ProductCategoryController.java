package com.iks.commerce.product_category.controller.v1;

import com.iks.commerce.product_category.entity.ProductCategory;
import com.iks.commerce.product_category.service.ProductCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
/**
 * The type Product category controller.
 */
@RestController
@RequestMapping("/v1/api-categories")
public class ProductCategoryController {
    /**
     * Create category response entity.
     *
     */
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * Create category response entity.
     *
     * @param category the category
     * @return the response entity
     */
// Create Category
    @PostMapping
    public ResponseEntity<ProductCategory>
    createCategory(final @Valid @RequestBody ProductCategory category) {
        ProductCategory createdCategory =
                productCategoryService.createCategory(category);
        return new ResponseEntity<>(
                createdCategory, HttpStatus.CREATED);
    }

    /**
     * Gets all categories.
     *
     * @return the all categories
     */
// Get All Categories
    @GetMapping
    public ResponseEntity<List
            <ProductCategory>> getAllCategories() {
        List<ProductCategory> categories =
                productCategoryService.getAllCategories();
        return new ResponseEntity<>(
                categories, HttpStatus.OK);
    }

    /**
     * Search categories response entity.
     *
     * @param searchText the search text
     * @return the response entity
     */
// Search Categories by name or description
    @GetMapping("/search")
    public ResponseEntity<List
            <ProductCategory>> searchCategories(
                    final @RequestParam String searchText) {
        List<ProductCategory> categories =
                productCategoryService.searchCategories(searchText);
        return new ResponseEntity<>(
                categories, HttpStatus.OK);
    }

    /**
     * Gets category by id.
     *
     * @param id the id
     * @return the category by id
     */
// Get Category by ID
    @GetMapping("/{id}")
    public ResponseEntity
            <ProductCategory> getCategoryById(
                    final @PathVariable String id) {
        ProductCategory category =
                productCategoryService.getCategoryById(id);
        return new ResponseEntity<>(
                category, HttpStatus.OK);
    }

    /**
     * Update category response entity.
     *
     * @param id       the id
     * @param category the category
     * @return the response entity
     */
// Update Category
    @PutMapping("/{id}")
    public ResponseEntity
            <ProductCategory> updateCategory(
                    final @PathVariable String id,
                    final @Valid @RequestBody ProductCategory category) {
        ProductCategory updatedCategory =
                productCategoryService.updateCategory(id, category);
        return new ResponseEntity<>(
                updatedCategory, HttpStatus.OK);
    }

    /**
     * Delete category response entity.
     *
     * @param id the id
     * @return the response entity
     */
// Delete Category
    @DeleteMapping("/{id}")
    public ResponseEntity
            <String> deleteCategory(
                    final @PathVariable String id) {
        productCategoryService.
                deleteCategory(id);
        return new ResponseEntity<>(
                "Category deleted successfully", HttpStatus.OK);
    }

    /**
     * Endpoint to get product IDs for a specific category.
     *
     * @param categoryCode The code of the category.
     * @return List of product IDs.
     */
    @GetMapping("/{categoryCode}/products")
    public ResponseEntity<List
            <String>> getProductIds(
                    final @PathVariable String categoryCode) {
        List<String> productIds =
                productCategoryService.
                        getProductIdsByCategoryCode(categoryCode);
        return ResponseEntity.ok(productIds);
    }
}
