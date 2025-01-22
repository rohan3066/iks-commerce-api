package com.iks.commerce.product_category.utils;

import com.iks.commerce.product_category.entity.ProductCategory;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The type Csv util.
 */
public final class CsvUtil {

    private CsvUtil() {
        throw new UnsupportedOperationException(
                "Utility class cannot be instantiated");
    }
    /**
     * The type Csv util.
     */
    public static final SimpleDateFormat DATE_FORMAT
            = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * The type Csv util.
     */
    private static final Validator VALIDATOR; // Jakarta Validator instance

    static {
        // Initialize the validator (
        // assumes dependency injection or manual setup)
        VALIDATOR = jakarta.validation.Validation.
                buildDefaultValidatorFactory().getValidator();
    }

    /**
     * Parses the CSV input stream and ma
     * ps the data to a list of ProductCategory objects.
     *
     * @param inputStream the InputStream of the CSV file
     * @return a List of ProductCategory objects
     * @throws IOException if an I/O error occurs while reading the file
     */
    public static List<ProductCategory> parseCsv(
            final InputStream inputStream) throws IOException {
        List<ProductCategory>
                productCategories = new ArrayList<>();
        try (BufferedReader reader
                     = new BufferedReader(
                new InputStreamReader(inputStream))) {
            String line;
            Map<String, Integer> headerMap
                    = new HashMap<>();

            // Read the header row to
            // map field names to column indices
            line = reader.readLine();

            if (line != null) {
                String[] headers = line.split(",");
                for (int i = 0; i < headers.length; i++) {
                    headerMap.put(headers[i].trim(), i);
                }
            }


            // Process each data row
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                ProductCategory productCategory = new ProductCategory();

                // Map fields dynamically using the header map
                productCategory.setCode(getFieldValue(
                        fields, headerMap, "code"));
                productCategory.setName(getFieldValue(
                        fields, headerMap, "name"));
                productCategory.setDescription(getFieldValue(
                        fields, headerMap, "description"));
                productCategory.setCatalogId(getFieldValue(
                        fields, headerMap, "catalogId"));
                productCategory.setSeoKeywords(getFieldValue(
                        fields, headerMap, "seoKeywords"));
                productCategory.setMetaDescription(getFieldValue(
                        fields, headerMap, "metaDescription"));

                // Parse date fields
                productCategory.setCreatedOn(parseDate(
                        getFieldValue(fields, headerMap, "createdOn")));
                productCategory.setModifiedOn(parseDate(
                        getFieldValue(fields, headerMap, "modifiedOn")));

                // Parse list fields (e.g.,
                // user groups, subcategories, products)
                productCategory.
                        setVisibleToUserGroupIds(
                                parseCsvList(fields, headerMap,
                                        "visibleToUserGroupIds"));
                productCategory.setSubcategoryIds(
                        parseCsvList(fields, headerMap,
                                "subcategoryIds"));
                productCategory.setProductIds(
                        parseCsvList(fields, headerMap, "productIds"));
                productCategory.setCreatedBy(
                        getFieldValue(fields, headerMap, "createdBy"));
                productCategory.setModifiedBy(
                        getFieldValue(fields, headerMap, "modifiedBy"));

                // Validate the product category
                if (isValidProductCategory(productCategory)) {
                    productCategories.add(productCategory);
                } else {
                    // Optionally log skipped
                    // entries for debugging
                    System.err.println(
                            "Invalid ProductCategory skipped: "
                                    + productCategory);
                }
            }
        }
        return productCategories;
    }

    /**
     * Helper method to retrieve a field value from
     * the CSV data based on the header mapping.
     *
     * @param fields the array of fields (columns) from the CSV row
     * @param headerMap the map of header names to column indices
     * @param fieldName the name of the field to retrieve
     * @return the field value or null if not found
     */
    static String getFieldValue(
            final String[] fields, final Map<String, Integer>
            headerMap, final String fieldName) {
        // Ensure the headerMap contains
        // the fieldName and the index is valid within fields array
        if (headerMap.containsKey(fieldName)) {
            Integer index = headerMap.get(fieldName);
            if (index != null && index < fields.length) {
                return fields[index].trim();  // safely return the value
            }
        }
        // Return an empty string or a default value if not found
        return "";
    }

    /**
     * Helper method to parse a date from a string.
     *
     * @param dateString the date string to parse
     * @return the parsed Date or null if parsing fails
     */
    static java.util.Date parseDate(
            final String dateString) {
        try {
            if (dateString != null && !dateString.isEmpty()) {
                return DATE_FORMAT.parse(dateString);
            }
        } catch (Exception e) {
            System.err.println("Error parsing date: " + dateString);
        }
        return null;
    }

    /**
     * Helper method to parse list fields (e.g., visibleT
     * oUserGroupIds, subcategoryIds) from the CSV.
     *
     * @param fields the array of fields (columns) from the CSV row
     * @param headerMap the map of header
     *                  names to column indices
     * @param fieldName the name of the field to parse as a list
     * @return a list of strings or an
     * empty list if the field is empty or not found
     */
    static List<String> parseCsvList(
            final String[] fields, final Map<String,
            Integer> headerMap, final String fieldName) {
        List<String> list = new ArrayList<>();
        String fieldValue = getFieldValue(
                fields, headerMap, fieldName);
        if (fieldValue != null && !fieldValue.isEmpty()) {
            String[] items = fieldValue.split(";");
            for (String item : items) {
                list.add(item.trim());
            }
        }
        return list;
    }

    /**
     * Validates a ProductCategory object using Jakarta Bean Validation.
     *
     * @param productCategory the ProductCategory object to validate
     * @return true if the productCategory is valid, false otherwise
     */
    static boolean isValidProductCategory(
            final ProductCategory productCategory) {
        Set<ConstraintViolation<ProductCategory>> violations =
                VALIDATOR.validate(productCategory);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage =
                    new StringBuilder("Validation errors:");
            for (ConstraintViolation<ProductCategory> violation : violations) {
                errorMessage.append("\n").append(violation.getMessage());
            }
            // Log validation errors
            System.err.println(errorMessage.toString());
            return false;
        }
        return true;
    }
}
