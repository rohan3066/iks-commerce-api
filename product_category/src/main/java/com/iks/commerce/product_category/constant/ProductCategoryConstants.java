package com.iks.commerce.product_category.constant;

/**
 * The type Product category constants.
 */
public final class ProductCategoryConstants {

    /**
     * Private constructor to prevent instantiation.
     */
    private ProductCategoryConstants() {
        throw new UnsupportedOperationException(
                "This is a utility class and cannot be instantiated");
    }

    /**
     * The constant CATEGORY_NOT_FOUND.
     */
    public static final String CATEGORY_NOT_FOUND =
            "Category not found with ID: ";
    /**
     * The constant DUPLICATE_CATEGORY_NAME.
     */
    public static final String DUPLICATE_CATEGORY_NAME =
            "Category name must be unique";
    /**
     * The constant CATEGORY_CREATED_SUCCESSFULLY.
     */
    public static final String CATEGORY_CREATED_SUCCESSFULLY =
            "Category created successfully with ID: ";
    /**
     * The constant CATEGORY_UPDATED_SUCCESSFULLY.
     */
    public static final String CATEGORY_UPDATED_SUCCESSFULLY =
            "Category updated successfully with ID: ";
    /**
     * The constant CATEGORY_DELETED_SUCCESSFULLY.
     */
    public static final String CATEGORY_DELETED_SUCCESSFULLY =
            "Category deleted successfully with ID: ";
    /**
     * The constant CATEGORY_PRODUCTS_FETCHED.
     */
    public static final String CATEGORY_PRODUCTS_FETCHED =
            "Fetched product IDs for category code: ";

    /**
     * The constant ERROR_PROCESSING_FILE.
     */
    public static final String ERROR_PROCESSING_FILE =
            "Error processing file: ";
    /**
     * The constant UNSUPPORTED_FILE_TYPE.
     */
    public static final String UNSUPPORTED_FILE_TYPE =
            "Unsupported file type: ";
    /**
     * The constant FILE_NAME_NULL.
     */
    public static final String FILE_NAME_NULL =
            "File name is null";

    /**
     * The constant PROCESSING_FILE.
     */
    public static final String PROCESSING_FILE =
            "Started processing file: ";
    /**
     * The constant PARSING_CSV.
     */
    public static final String PARSING_CSV =
            "Parsing CSV file...";
    /**
     * The constant PARSING_JSON.
     */
    public static final String PARSING_JSON =
            "Parsing JSON file...";
    /**
     * The constant SAVING_PRODUCT_CATEGORY.
     */
    public static final String SAVING_PRODUCT_CATEGORY =
            "Saving ProductCategory: ";
    /**
     * The constant SUCCESSFUL_FILE_PROCESSING.
     */
    public static final String SUCCESSFUL_FILE_PROCESSING =
            "Successfully processed and saved {} categories";
    /**
     * The constant FETCHING_ALL_CATEGORIES.
     */
    public static final String FETCHING_ALL_CATEGORIES =
            "Fetching all categories...";
    /**
     * The constant FETCHED_CATEGORIES.
     */
    public static final String FETCHED_CATEGORIES =
            "Fetched {} categories";
    /**
     * The constant SEARCHING_CATEGORIES.
     */
    public static final String SEARCHING_CATEGORIES =
            "Searching categories with text: ";
    /**
     * The constant FOUND_CATEGORIES.
     */
    public static final String FOUND_CATEGORIES =
            "Found {} categories matching search text: ";
    /**
     * The constant FETCHING_CATEGORY_BY_ID.
     */
    public static final String FETCHING_CATEGORY_BY_ID =
            "Fetching category with ID: ";
    /**
     * The constant FETCHED_CATEGORY_BY_ID.
     */
    public static final String FETCHED_CATEGORY_BY_ID =
            "Fetched category with ID: ";
    /**
     * The constant UPDATING_CATEGORIES.
     */
    public static final String UPDATING_CATEGORIES =
            "Started updating product categories from file: ";
    /**
     * The constant SUCCESSFUL_CATEGORY_UPDATE.
     */
    public static final String SUCCESSFUL_CATEGORY_UPDATE =
            "Successfully updated {} categories";
    /**
     * The constant ATTEMPTING_TO_DELETE_CATEGORY.
     */
    public static final String ATTEMPTING_TO_DELETE_CATEGORY =
            "Attempting to delete category with ID: ";
    /**
     * The constant FETCHING_PRODUCT_IDS.
     */
    public static final String FETCHING_PRODUCT_IDS =
            "Fetching product IDs for category code: ";
    /**
     * The constant FETCHED_PRODUCT_IDS.
     */
    public static final String FETCHED_PRODUCT_IDS =
            "Fetched {} product IDs for category code: ";

    /**
     * The constant JSON_CONTENT_TYPE.
     */
// Content type for file processing
    public static final String JSON_CONTENT_TYPE =
            "application/json";
    /**
     * The constant CSV_CONTENT_TYPE.
     */
    public static final String CSV_CONTENT_TYPE =
            "text/csv";

    // Other constants

    /**
     * The constant CSV_CONTENT_TYPE.
     */
    public static final int HTTP_STATUS_INTERNAL_SERVER_ERROR =
            500;

}
