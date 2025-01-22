package com.iks.commerce.product_category.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iks.commerce.product_category.entity.ProductCategory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * The type Json util.
 */
public final class JsonUtil {
    private JsonUtil() {
        throw new UnsupportedOperationException(
                "Utility class cannot be instantiated");
    }

    /**
     * Parse json list.
     *
     * @param inputStream the input stream
     * @return the list
     * @throws IOException the io exception
     */
    public static List<ProductCategory> parseJson(
            final InputStream inputStream) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.
                FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Convert InputStream to String once for debugging
        String json = new String(inputStream.readAllBytes());
        System.out.println("Received JSON: " + json);

        // Reset the input stream (since it's been consumed) for parsing
        InputStream resetStream = new java.io.
                ByteArrayInputStream(json.getBytes());

        // Parse the JSON string into the List of ProductCategory
        return objectMapper.readValue(
                resetStream, new TypeReference<List<ProductCategory>>() { });
    }
}
