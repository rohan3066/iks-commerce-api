package com.iks.commerce.returnorder.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iks.commerce.returnorder.entity.ReturnOrder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * The type Json util for ReturnOrder.
 */
public final class JsonUtil {

    // Private constructor to prevent instantiation
    private JsonUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Parse JSON into a list of ReturnOrder objects.
     *
     * @param inputStream the input stream containing JSON data
     * @return the list of ReturnOrder objects
     * @throws IOException if there is an error
     * reading the input stream or parsing the JSON
     */
    public static List<ReturnOrder> parseJson(
            final InputStream inputStream) throws IOException {
        ObjectMapper objectMapper =
                new ObjectMapper();
        objectMapper.configure(DeserializationFeature
                .FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Read the entire input stream as a byte array
        String json = new String(inputStream
                .readAllBytes());

        // Convert the byte array back into
        // an InputStream for Jackson parsing
        InputStream resetStream = new java.io
                .ByteArrayInputStream(json.getBytes());

        // Parse the JSON into a list of ReturnOrder objects
        return objectMapper.readValue(
                resetStream,
                new TypeReference<List<ReturnOrder>>() { });
    }
}
