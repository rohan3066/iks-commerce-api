package com.iks.commerce.product_category.service;
import com.iks.commerce.product_category.constant.ProductCategoryConstants;
import com.iks.commerce.product_category.entity.ProductCategory;
import com.iks.commerce.product_category.repository.ProductCategoryRepository;
import com.iks.commerce.product_category.utils.CsvUtil;
import com.iks.commerce.product_category.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


import org.springframework.web.multipart.MultipartFile;

/**
 * The type Product category impex service.
 */
@Service
public class ProductCategoryImpexService {
    /**
     * The type Product category service.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ProductCategoryImpexService.class);
    /**
     * The type Product category service.
     */
    @Autowired
   private ProductCategoryRepository productCategoryRepository;

    /**
     * Create category product category.
     *
     * @param file the category
     * @return the product category
     */
// Create Category
    public List<ProductCategory> processFile(
            final MultipartFile file) {
        LOGGER.info(ProductCategoryConstants
                .PROCESSING_FILE + file.getOriginalFilename());

        try {
            String fileName = file.getOriginalFilename();
            if (fileName == null) {
                LOGGER.error(ProductCategoryConstants
                        .FILE_NAME_NULL);
                throw new IllegalArgumentException(
                        ProductCategoryConstants.FILE_NAME_NULL);
            }

            List<ProductCategory> productCategories;
            if (fileName.endsWith(".csv")) {
                LOGGER.info(ProductCategoryConstants
                        .PARSING_CSV);
                productCategories = CsvUtil
                        .parseCsv(file.getInputStream());
            } else if (fileName.endsWith(".json")) {
                LOGGER.info(ProductCategoryConstants
                        .PARSING_JSON);
                productCategories = JsonUtil
                        .parseJson(file.getInputStream());
            } else {
                LOGGER.error(ProductCategoryConstants
                        .UNSUPPORTED_FILE_TYPE + fileName);
                throw new IllegalArgumentException(
                        ProductCategoryConstants
                                .UNSUPPORTED_FILE_TYPE + fileName);
            }

            // Debug parsed product categories
            productCategories.forEach(productCategory -> {
                LOGGER.debug(ProductCategoryConstants
                        .SAVING_PRODUCT_CATEGORY + productCategory);
            });

            // Save to MongoDB
            productCategoryRepository.saveAll(productCategories);
            LOGGER.info(ProductCategoryConstants
                    .SUCCESSFUL_FILE_PROCESSING, productCategories.size());

            return productCategories;
        } catch (IOException e) {
            LOGGER.error(ProductCategoryConstants
                    .ERROR_PROCESSING_FILE + e.getMessage(), e);
            throw new RuntimeException(ProductCategoryConstants
                    .ERROR_PROCESSING_FILE + e.getMessage(), e);
        }
    }

    /**
     * Gets all categories.
     *
     * @return the all categories
     */
// Read Category (Get all categories)
    public List<ProductCategory> getAllCategories() {
        LOGGER.info(ProductCategoryConstants
                .FETCHING_ALL_CATEGORIES);
        List<ProductCategory> categories =
                productCategoryRepository.findAll();
        LOGGER.info(ProductCategoryConstants
                .FETCHED_CATEGORIES, categories.size());
        return categories;
    }

    /**
     * Search categories list.
     *
     * @param searchText the search text
     * @return the list
     */
// Search for categories by name or description
    public List<ProductCategory> searchCategories(
            final String searchText) {
        LOGGER.info(ProductCategoryConstants.
                SEARCHING_CATEGORIES + searchText);
        List<ProductCategory> categories = productCategoryRepository.
                findByNameContainingOrDescriptionContaining(
                        searchText, searchText);
        LOGGER.info(ProductCategoryConstants.
                FOUND_CATEGORIES, categories.size(), searchText);
        return categories;
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
        LOGGER.info(ProductCategoryConstants.
                FETCHING_CATEGORY_BY_ID + id);
        ProductCategory category =
                productCategoryRepository.findById(id)
                        .orElseThrow(() -> {
                            LOGGER.error(ProductCategoryConstants
                                    .CATEGORY_NOT_FOUND + id);
                            return new IllegalArgumentException(
                                    ProductCategoryConstants.
                                            CATEGORY_NOT_FOUND + id);
                        });
        LOGGER.info(ProductCategoryConstants
                .FETCHED_CATEGORY_BY_ID + id);
        return category;
    }


    /**
     * Updates product categories from a CSV or JSON file.
     *
     * @param id   the id
     * @param file the uploaded file (CSV or JSON)
     * @return the count of successfully updated categories
     * @throws Exception if file processing fails
     */
    public int updateProductCategoryById(
            final String id,
            final MultipartFile file) throws Exception {
        LOGGER.info("Updating product category with ID: " + id);

        // Retrieve the existing product category
        Optional<ProductCategory> optionalCategory =
                productCategoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new NoSuchElementException(
                    "Product category with ID " + id + " not found.");
        }

        ProductCategory existingCategory =
                optionalCategory.get();
        InputStream inputStream =
                file.getInputStream();
        String fileType = file
                .getContentType();

        List<ProductCategory> productCategories;

        if (ProductCategoryConstants.
                JSON_CONTENT_TYPE.equals(fileType) || file.getOriginalFilename()
                .endsWith(".json")) {
            LOGGER.info("Parsing JSON file");
            productCategories = JsonUtil
                    .parseJson(inputStream);
        } else if (ProductCategoryConstants.CSV_CONTENT_TYPE
                .equals(fileType) || file.getOriginalFilename()
                .endsWith(".csv")) {
            LOGGER.info("Parsing CSV file");
            productCategories = CsvUtil.parseCsv(inputStream);
        } else {
            LOGGER.error(
                    "Unsupported file type: " + fileType);
            throw new IllegalArgumentException(
                    "Unsupported file type: " + fileType);
        }

        if (productCategories.isEmpty()) {
            throw new IllegalArgumentException(
                    "No valid product categories found in the file.");
        }

        ProductCategory updatedCategory = productCategories.get(0);


        // Update fields only if the new value is not null
        if (updatedCategory.getCode() != null) {
            existingCategory
                .setCode(updatedCategory.getCode());
        }
        if (updatedCategory.getName() != null) {
            existingCategory
                .setName(updatedCategory.getName());
        }
        if (updatedCategory.getDescription() != null) {
            existingCategory
                .setDescription(updatedCategory.getDescription());
        }
        if (updatedCategory.getCatalogId() != null) {
            existingCategory
                .setCatalogId(updatedCategory.getCatalogId());
        }
        if (updatedCategory.getVisibleToUserGroupIds() != null) {
            existingCategory
                .setVisibleToUserGroupIds(updatedCategory
                        .getVisibleToUserGroupIds());
        }
        if (updatedCategory.getSubcategoryIds() != null) {
            existingCategory
                .setSubcategoryIds(updatedCategory.getSubcategoryIds());
        }
        if (updatedCategory.getProductIds() != null) {
            existingCategory
                .setProductIds(updatedCategory.getProductIds());
        }
        if (updatedCategory.getSeoKeywords() != null) {
            existingCategory
                .setSeoKeywords(updatedCategory.getSeoKeywords());
        }
        if (updatedCategory.getMetaDescription() != null) {
            existingCategory
                .setMetaDescription(updatedCategory.getMetaDescription());
        }
        if (updatedCategory.getModifiedBy() != null) {
            existingCategory
                .setModifiedBy(updatedCategory.getModifiedBy());
        }
        if (updatedCategory.getCreatedBy() != null) {
            existingCategory
                .setCreatedBy(updatedCategory.getCreatedBy());
        }

        existingCategory.
                setModifiedOn(new Date());

        productCategoryRepository.save(existingCategory);
        LOGGER.info(
                "Product category with ID " + id + " updated successfully.");

        return 1;
    }


    /**
     * Delete category.
     *
     * @param id the id
     */
// Delete Category
    public void deleteCategory(final String id) {
        LOGGER.info(ProductCategoryConstants
                .ATTEMPTING_TO_DELETE_CATEGORY + id);

        ProductCategory category =
                productCategoryRepository.findById(id)
                        .orElseThrow(() -> {
                            LOGGER.error(ProductCategoryConstants
                                    .CATEGORY_NOT_FOUND + id);
                            return new IllegalArgumentException(
                                    ProductCategoryConstants.
                                            CATEGORY_NOT_FOUND + id);

                        });

        productCategoryRepository.delete(category);
        LOGGER.info(ProductCategoryConstants
                .CATEGORY_DELETED_SUCCESSFULLY + id);
    }

    /**
     * Gets product ids by category code.
     *
     * @param categoryCode the category code
     * @return the product ids by category code
     */
// Get the product IDs for a specific category
    public List<String> getProductIdsByCategoryCode(
            final String categoryCode) {
        LOGGER.info(ProductCategoryConstants
                .FETCHING_PRODUCT_IDS + categoryCode);

        ProductCategory category = productCategoryRepository
                .findById(categoryCode)
                .orElseThrow(() -> {
                    LOGGER.error(ProductCategoryConstants
                            .CATEGORY_NOT_FOUND + categoryCode);
                    return new RuntimeException(ProductCategoryConstants
                            .CATEGORY_NOT_FOUND + categoryCode);
                });

        List<String> productIds = category
                .getProductIds() == null ? List.of() : category.getProductIds();
        LOGGER.info(ProductCategoryConstants
                .FETCHED_PRODUCT_IDS, productIds.size(), categoryCode);
        return productIds;
    }
}
