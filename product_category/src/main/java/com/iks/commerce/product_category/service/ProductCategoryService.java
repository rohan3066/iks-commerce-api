package com.iks.commerce.product_category.service;

import com.iks.commerce.product_category.constant.ProductCategoryConstants;
import com.iks.commerce.product_category.entity.ProductCategory;
import com.iks.commerce.product_category.repository.ProductCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * The type Product category service.
 */
@Service
public class ProductCategoryService {
    /**
     * The type Product category service.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ProductCategoryService.class);
    /**
     * The type Product category service.
     */
    @Autowired
    private ProductCategoryRepository
            productCategoryRepository;

    /**
     * Create category product category.
     *
     * @param category the category
     * @return the product category
     */
// Create Category
    public ProductCategory createCategory(
            final ProductCategory category) {
        LOGGER.info(
                "Attempting to create a new category with name: {}",
                category.getName());

        // Check if the category name is unique (basic validation)
        Optional<ProductCategory> existingCategory =
                productCategoryRepository.findByName(
                        category.getName());
        if (existingCategory.isPresent()) {
            LOGGER.error(ProductCategoryConstants.
                    DUPLICATE_CATEGORY_NAME);
            throw new IllegalArgumentException(ProductCategoryConstants.
                    DUPLICATE_CATEGORY_NAME);
        }

        // Assign creation/modification timestamps
        category.setCreatedOn(new Date());
        category.setModifiedOn(new Date());

        ProductCategory savedCategory =
                productCategoryRepository.save(category);
        LOGGER.info(ProductCategoryConstants.
                CATEGORY_CREATED_SUCCESSFULLY + savedCategory.getCode());
        return savedCategory;
    }

    /**
     * Gets all categories.
     *
     * @return the all categories
     */
// Read Category (Get all categories)
    public List<ProductCategory> getAllCategories() {
        LOGGER.info("Fetching all categories...");
        return productCategoryRepository
                .findAll();
    }

    /**
     * Search categories list.
     *
     * @param searchText the search text
     * @return the list
     */
// Search for categories by name or description
    public List<ProductCategory> searchCategories(final String searchText) {
        LOGGER.info(
                "Searching categories with text: {}", searchText);
        return productCategoryRepository.
                findByNameContainingOrDescriptionContaining(
                        searchText, searchText);
    }

    /**
     * Gets category by id.
     *
     * @param id the id
     * @return the category by id
     */
// View a single category by ID
    public ProductCategory getCategoryById(
            final String id) {
        LOGGER.info(
                "Fetching category with ID: {}", id);
        return productCategoryRepository.
                findById(id)
                .orElseThrow(() -> {
                    LOGGER.error(ProductCategoryConstants.
                            CATEGORY_NOT_FOUND + id);
                    return new IllegalArgumentException(
                            ProductCategoryConstants.
                                    CATEGORY_NOT_FOUND + id);
                });
    }

    /**
     * Update category product category.
     *
     * @param id              the id
     * @param categoryDetails the category details
     * @return the product category
     */
// Update Category
    public ProductCategory updateCategory(final String id,
                                          final ProductCategory
                                                  categoryDetails) {
        LOGGER.info("Attempting to update category with ID: {}", id);

        ProductCategory category =
                productCategoryRepository.findById(id)
                        .orElseThrow(() -> {
                            LOGGER.error(ProductCategoryConstants.
                                    CATEGORY_NOT_FOUND + id);
                            return new IllegalArgumentException(
                                    ProductCategoryConstants.
                                            CATEGORY_NOT_FOUND + id);
                        });

        // Update fields
        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.
                getDescription());
        category.setSeoKeywords(categoryDetails.
                getSeoKeywords());
        category.setMetaDescription(categoryDetails.
                getMetaDescription());
        category.setVisibleToUserGroupIds(categoryDetails.
                getVisibleToUserGroupIds());
        category.setSubcategoryIds(categoryDetails.
                getSubcategoryIds());
        category.setProductIds(categoryDetails.
                getProductIds());
        category.setModifiedOn(new Date());

        ProductCategory updatedCategory =
                productCategoryRepository.save(category);
        LOGGER.info(ProductCategoryConstants.
                CATEGORY_UPDATED_SUCCESSFULLY + updatedCategory.getCode());
        return updatedCategory;
    }

    /**
     * Delete category.
     *
     * @param id the id
     */
// Delete Category
    public void deleteCategory(final String id) {
        LOGGER.info(
                "Attempting to delete category with ID: {}", id);

        ProductCategory category =
                productCategoryRepository.
                        findById(id)
                        .orElseThrow(() -> {
                            LOGGER.error(ProductCategoryConstants.
                                    CATEGORY_NOT_FOUND + id);
                            return new IllegalArgumentException(
                                    ProductCategoryConstants.
                                            CATEGORY_NOT_FOUND + id);
                        });

        productCategoryRepository.delete(category);
        LOGGER.info(ProductCategoryConstants.
                CATEGORY_DELETED_SUCCESSFULLY + id);
    }

    /**
     * Gets product ids by category code.
     *
     * @param categoryCode the category code
     * @return the product ids by category code
     */
// Get the product IDs for a specific category
    public List<String>
    getProductIdsByCategoryCode(
            final String categoryCode) {
        LOGGER.info(
                "Fetching product IDs for category code: {}", categoryCode);

        ProductCategory category = productCategoryRepository.
                findById(categoryCode)
                .orElseThrow(() -> {
                    LOGGER.error(ProductCategoryConstants.
                            CATEGORY_NOT_FOUND + categoryCode);
                    return new RuntimeException(ProductCategoryConstants.
                            CATEGORY_NOT_FOUND + categoryCode);
                });

        List<String> productIds = category.getProductIds()
                == null ? List.of() : category.getProductIds();
        LOGGER.info(ProductCategoryConstants.
                CATEGORY_PRODUCTS_FETCHED + categoryCode);
        return productIds;
    }
}
