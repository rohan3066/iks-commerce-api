package com.iks.commerce.returnorder.utils;

import com.iks.commerce.returnorder.entity.ReturnOrder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public final class CsvUtil {

    // Private constructor to prevent instantiation
    private CsvUtil() {
        throw new UnsupportedOperationException(
                "This is a utility class and cannot be instantiated");
    }
    /**
     * Utility class for parsing CSV files and processing order items.
     */

    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Parses the CSV input stream and maps it t
     * o a list of ReturnOrder objects.
     *
     * @param inputStream the input stream containing the CSV data
     * @return a list of ReturnOrder objects
     * @throws IOException if there's an issue reading the CSV data
     */
    public static List<ReturnOrder> parseCsv(
            final InputStream inputStream) throws IOException {
        List<ReturnOrder> returnOrders =
                new ArrayList<>();
        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            Map<String, Integer> headerMap =
                    new HashMap<>();

            // Reading the header line
            line = reader.readLine();
            if (line != null) {
                String[] headers = line.split(",");
                for (int i = 0; i < headers.length; i++) {
                    headerMap.put(headers[i].trim(), i);
                }

            }

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                ReturnOrder returnOrder = new ReturnOrder();

                // Log the raw fields for debugging
                System.out.println("Parsing line: " + line);
                System.out.println("Fields: " + String.join(", ", fields));

                returnOrder.setReturnItem(getFieldValue(
                        fields, headerMap, "name"));
                returnOrder.setCustomerId(getFieldValue(
                        fields, headerMap, "customerId"));
                returnOrder.setBillingAddress(getFieldValue(
                        fields, headerMap, "billingAddress"));
                returnOrder.setCurrencyISOCode(getFieldValue(
                        fields, headerMap, "currencyISOCode"));
                returnOrder.setGrandTotalAmount(parseDoubleSafely(
                        getFieldValue(fields, headerMap, "grandTotalAmount")));
                returnOrder.setPaymentGroupId(getFieldValue(
                        fields, headerMap, "paymentGroupId"));
                returnOrder.setPaymentMethodId(getFieldValue(
                        fields, headerMap, "paymentMethodId"));
                returnOrder.setPoNumber(getFieldValue(
                        fields, headerMap, "poNumber"));
                returnOrder.setActive(Boolean.parseBoolean(
                        getFieldValue(fields, headerMap, "active")));
                returnOrder.setWebStoreId(getFieldValue(
                        fields, headerMap, "webStoreId"));
                returnOrder.setTaxType(parseList(getFieldValue(
                        fields, headerMap, "taxType")));
                returnOrder.setTotalAmount(parseDoubleSafely(getFieldValue(
                        fields, headerMap, "totalAmount")));
                returnOrder.setTotalProductAmount(
                        parseDoubleSafely(getFieldValue(
                        fields, headerMap,
                        "totalProductAmount")));
                returnOrder.setTotalProductLineItemCount(
                        parseIntSafely(getFieldValue(
                        fields, headerMap, "totalProductLineItemCount")));
                returnOrder.setTotalProductTaxAmount(
                        parseDoubleSafely(getFieldValue(
                        fields, headerMap, "totalProductTaxAmount")));
                returnOrder.setTotalProductCount(parseIntSafely(getFieldValue(
                        fields, headerMap, "totalProductCount")));
                returnOrder.setTotalTaxAmount(parseDoubleSafely(getFieldValue(
                        fields, headerMap, "totalTaxAmount")));
                returnOrder.setType(parseList(getFieldValue(
                        fields, headerMap, "type")));
                returnOrder.setUniqueProductCount(
                        parseIntSafely(getFieldValue(
                        fields, headerMap, "uniqueProductCount")));
                returnOrder.setLastModifiedOn(parseDate(getFieldValue(
                        fields, headerMap, "lastModifiedOn")));
                returnOrder.setCreatedOn(parseDate(getFieldValue(
                        fields, headerMap, "createdOn")));
                returnOrder.setModifiedBy(getFieldValue(
                        fields, headerMap, "modifiedBy"));

                // Validate and add to the list

                    returnOrders.add(returnOrder);

            }
        }
        return returnOrders;
    }

    /**
     * @param fields      the CSV line split into fields
     * @param headerMap   the map of header names to column indices
     * @param fieldName   the field name to retrieve the value for
     * @return the field value as a String, or an
     * empty string if not found
     */
    static String getFieldValue(
            final String[] fields, final Map<String, Integer> headerMap,
            final String fieldName) {
        if (headerMap.containsKey(fieldName)) {
            Integer index = headerMap.get(fieldName);
            if (index != null && index < fields.length) {
                return fields[index].trim();
            }
        }
        return "";
    }

    /**
     * Safely parses a double value from a string.
     *
     * @param value the string to parse
     * @return the parsed double value, or 0.0 if parsing fails
     */
    private static double parseDoubleSafely(
            final String value) {
        return (value == null || value.trim().
                isEmpty()) ? 0.0 : Double
                .parseDouble(value);
    }


    /**
     * Parses a CSV list string (e.g.,
     * [\"item1\",\"item2\",\"item3\"]") into a list of strings.
     *
     * @param value the string to parse into a list
     * @return a list of strings
     */
    private static List<String> parseList(
            final String value) {
        if (value != null && !value.trim()
                .isEmpty()) {
            // Remove leading and trailing square
            // brackets and any unwanted escape characters
            String cleanedValue = value.replaceAll(
                    "[\\[\\]\"]", "").trim();

            // If there are items left,
            // split by comma to create the list
            if (!cleanedValue.isEmpty()) {
                String[] items = cleanedValue
                        .split(",");
                return List.of(items);
            }
        }
        return new ArrayList<>();
    }



    /**
     * Parses a string into a date.
     * Assumes the date is in a standard format (e.g., "yyyy-MM-dd").
     *
     * @param dateString the string to parse into a date
     * @return the parsed date, or null if parsing fails
     */
    static Date parseDate(
            final String dateString) {
        try {
            if (dateString != null && !dateString
                    .isEmpty()) {
                return DATE_FORMAT
                        .parse(dateString);
            }
        } catch (Exception e) {
            System.err.println(
                    "Error parsing date: " + dateString);
        }
        return null;
    }
    /**
     * Safely parses an integer value from a string.
     *
     * @param value the string to parse
     * @return the parsed integer value, or 0 if parsing fails
     */
    private static int parseIntSafely(
            final String value) {
        try {
            return value.isEmpty() ? 0 : Integer.parseInt(value
                    .trim());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing integer: " + value);
            return 0;
        }
    }




}
