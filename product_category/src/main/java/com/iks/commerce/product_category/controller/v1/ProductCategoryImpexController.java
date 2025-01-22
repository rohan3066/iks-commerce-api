package com.iks.commerce.product_category.controller.v1;

import com.iks.commerce.product_category.entity.ProductCategory;
import com.iks.commerce.product_category.service.ProductCategoryImpexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The type Product category impex controller.
 */
@RestController
@RequestMapping("/v1/api-product-impex-categories")
public class ProductCategoryImpexController {
    /**
     * Create category response entity.
     *
     */
    @Autowired
    private ProductCategoryImpexService productCategoryService;

    /**
     * Create category response entity.
     *
     * @param file the category
     * @return the response entity
     */
// Create Category
    @PostMapping("/upload")
    public ResponseEntity<List<ProductCategory>> uploadFile(
            @RequestParam("file") final MultipartFile file) {
        try {
            List<ProductCategory> productCategories =
                    productCategoryService.processFile(file);
            return ResponseEntity.ok(productCategories);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest().body(null);
        }
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
     * Updates product categories using a CSV or JSON file.
     *
     * @param id   the id
     * @param file the uploaded file (CSV or JSON)
     * @return ResponseEntity with the status of the update operation
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProductCategoryById(
            @PathVariable("id") final String id,
            @RequestParam("file") final MultipartFile file) {
        try {
            int updatedCount =
                    productCategoryService
                            .updateProductCategoryById(id, file);
            return ResponseEntity.ok(
                    updatedCount + " product category updated successfully.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.
                    status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.
                    badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.
                    INTERNAL_SERVER_ERROR).
                    body("An error occurred: " + e.getMessage());
        }
    }


    /**
     * Delete category response entity .
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
