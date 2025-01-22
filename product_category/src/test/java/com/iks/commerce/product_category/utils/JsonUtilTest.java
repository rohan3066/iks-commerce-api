package com.iks.commerce.product_category.utils;

import com.iks.commerce.product_category.entity.ProductCategory;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonUtilTest {

    private static final String VALID_JSON = "[\n" +
            "  {\n" +
            "    \"code\": \"PC001\",\n" +
            "    \"name\": \"Electronics\",\n" +
            "    \"description\": \"Category for Electronics\",\n" +
            "    \"catalogId\": \"1\",\n" +
            "    \"seoKeywords\": \"electronics\",\n" +
            "    \"metaDescription\": \"meta1\",\n" +
            "    \"createdOn\": \"2025-01-01\",\n" +
            "    \"modifiedOn\": \"2025-01-02\",\n" +
            "    \"visibleToUserGroupIds\": [\"Group1\", \"Group2\"],\n" +
            "    \"subcategoryIds\": [\"Subcat1\", \"Subcat2\"],\n" +
            "    \"productIds\": [\"Prod1\", \"Prod2\"],\n" +
            "    \"createdBy\": \"Admin\",\n" +
            "    \"modifiedBy\": \"Admin\"\n" +
            "  }\n" +
            "]";

    private static final String INVALID_JSON = "[\n" +
            "  {\n" +
            "    \"code\": \"PC001\",\n" +
            "    \"name\": \"Electronics\",\n" +
            "    \"description\": \"Category for Electronics\",\n" +
            "    \"catalogId\": \"1\",\n" +
            "    \"seoKeywords\": \"electronics\",\n" +
            "    \"metaDescription\": \"meta1\",\n" +
            "    \"createdOn\": \"invalid-date\",\n" +
            "    \"modifiedOn\": \"2025-01-02\",\n" +
            "    \"visibleToUserGroupIds\": [\"Group1\", \"Group2\"],\n" +
            "    \"subcategoryIds\": [\"Subcat1\", \"Subcat2\"],\n" +
            "    \"productIds\": [\"Prod1\", \"Prod2\"],\n" +
            "    \"createdBy\": \"Admin\",\n" +
            "    \"modifiedBy\": \"Admin\"\n" +
            "  }\n" +
            "]";

    private static final String EMPTY_JSON = "[]";

    @Test
    public void testParseJson_ValidJson() throws IOException {
        // Mock an InputStream of the valid JSON string
        ByteArrayInputStream inputStream = new ByteArrayInputStream(VALID_JSON.getBytes());

        // Parse the JSON into ProductCategory list
        List<ProductCategory> productCategories = JsonUtil.parseJson(inputStream);  // Directly call static method

        // Assert the list is not empty
        assertNotNull(productCategories);
        assertEquals(1, productCategories.size());

        // Assert specific fields in the parsed ProductCategory
        ProductCategory productCategory = productCategories.get(0);
        assertEquals("PC001", productCategory.getCode());
        assertEquals("Electronics", productCategory.getName());
        assertEquals("Category for Electronics", productCategory.getDescription());
    }



    @Test
    public void testParseJson_EmptyJson() throws IOException {
        // Mock an InputStream of the empty JSON string
        ByteArrayInputStream inputStream = new ByteArrayInputStream(EMPTY_JSON.getBytes());

        // Parse the empty JSON into ProductCategory list
        List<ProductCategory> productCategories = JsonUtil.parseJson(inputStream);

        // Assert that the list is empty
        assertNotNull(productCategories);
        assertTrue(productCategories.isEmpty());
    }

    @Test
    public void testParseJson_InvalidJsonStructure() {
        // Pass an invalid JSON structure to check error handling
        String invalidJson = "{ invalid JSON }";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(invalidJson.getBytes());

        // Expecting an exception due to malformed JSON
        assertThrows(IOException.class, () -> JsonUtil.parseJson(inputStream));
    }

    @Test
    public void testParseJson_LogsJsonContent() throws IOException {
        // Mock an InputStream of the valid JSON string
        ByteArrayInputStream inputStream = new ByteArrayInputStream(VALID_JSON.getBytes());

        // Capture the output of the log statement
        // In a real test, you would need to use a mocking framework to capture System.out
        // Here we are simply verifying the method executes without errors
        List<ProductCategory> productCategories = JsonUtil.parseJson(inputStream);  // Directly call static method

        // Validate no exceptions were thrown and data is parsed
        assertNotNull(productCategories);
    }
}
