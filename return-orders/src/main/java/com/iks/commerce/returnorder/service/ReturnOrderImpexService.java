package com.iks.commerce.returnorder.service;

import com.iks.commerce.returnorder.constant.StringConstant;
import com.iks.commerce.returnorder.exception.ResourceNotFound;
import com.iks.commerce.returnorder.entity.ReturnOrder;
import com.iks.commerce.returnorder.repository.ReturnOrderRepository;
import com.iks.commerce.returnorder.utils.CsvUtil;
import com.iks.commerce.returnorder.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;


/**
 * The type Return order service.
 * This service class handles the business logic for managing return orders.
 */
@Service
public class ReturnOrderImpexService {

    /**
     * The type Return order service.
     * This service class handles the business logic for managing return orders.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ReturnOrderImpexService.class);
    /**
     * The type Return order service.
     * This service class handles the business logic for managing return orders.
     */
    private final ReturnOrderRepository returnOrderRepo;

    /**
     * Instantiates a new Return order service.
     *
     * @param returnOrderR the return order repository
     */
    public ReturnOrderImpexService(final ReturnOrderRepository returnOrderR) {
        this.returnOrderRepo = returnOrderR;
    }

    /**
     * Adds a new return order.
     *
     * @param file the return order file to process
     * @return the added return order
     */
    public List<ReturnOrder> processReturnOrderFile(
            final MultipartFile file) {
        LOGGER.info(StringConstant.PROCESSING_FILE + file
                .getOriginalFilename());

        try {
            String fileName = file
                    .getOriginalFilename();
            if (fileName == null) {
                LOGGER.error(StringConstant
                        .FILE_NAME_NULL);
                throw new IllegalArgumentException(StringConstant
                        .FILE_NAME_NULL);
            }

            List<ReturnOrder> returnOrders;
            if (fileName.endsWith(".csv")) {
                LOGGER.info(StringConstant
                        .PARSING_CSV);
                returnOrders = CsvUtil.parseCsv(file
                        .getInputStream());
            } else if (fileName.endsWith(".json")) {
                LOGGER.info(StringConstant.PARSING_JSON);
                returnOrders = JsonUtil
                        .parseJson(file.getInputStream());
            } else {
                LOGGER.error(StringConstant
                        .UNSUPPORTED_FILE_TYPE + fileName);
                throw new IllegalArgumentException(StringConstant
                        .UNSUPPORTED_FILE_TYPE + fileName);
            }

            returnOrders.forEach(returnOrder -> LOGGER
                    .debug(StringConstant
                            .SAVING_RETURN_ORDER + returnOrder));

            // Save to MongoDB
            returnOrderRepo.saveAll(
                    returnOrders);
            LOGGER.info(StringConstant
                    .SUCCESSFUL_FILE_PROCESSING, returnOrders
                    .size());

            return returnOrders;
        } catch (IOException e) {
            LOGGER.error(StringConstant
                    .ERROR_PROCESSING_FILE + e.getMessage(), e);
            throw new RuntimeException(StringConstant
                    .ERROR_PROCESSING_FILE + e.getMessage(), e);
        }
    }


    /**
     * Retrieves a return order by its ID.
     *
     * @param id the ID of the return order to be retrieved
     * @return an Optional containing the found
     * return order, or an empty Optional if not found
     */
    public Optional<ReturnOrder> getReturnOrderById(final String id) {
        LOGGER.info(StringConstant.LOG_FETCHING_RETURN_ORDER, id);
        Optional<ReturnOrder> returnOrder = returnOrderRepo.findById(id);
        if (returnOrder.isPresent()) {
            LOGGER.info(StringConstant
                    .LOG_FOUND_RETURN_ORDER, id);
        } else {
            LOGGER.warn(StringConstant
                    .LOG_NOT_FOUND_RETURN_ORDER, id);
        }
        return returnOrder;
    }

    /**
     * Updates an existing return order using the
     * file and ID from the path variable.
     *
     * @param id the ID of the return order to be updated
     * @param file the file containing the updated return order details
     * @return the updated return order
     */
    public ReturnOrder updateReturnOrderFromFile(
            final String id,  // ID passed via path variable
            final MultipartFile file) throws Exception {
        LOGGER.info(StringConstant
                .ORDER_ITEM_UPDATE_ATTEMPT_MESSAGE, file
                .getOriginalFilename());

        // Retrieve the existing return order using the provided ID
        ReturnOrder existingReturnOrder =
                returnOrderRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(StringConstant.
                        ORDER_ITEM_NOT_FOUND + id));

        InputStream inputStream = file
                .getInputStream();
        String fileType = file
                .getContentType();

        List<ReturnOrder> returnOrders;

        // Parsing the file based on its type
        if (StringConstant
                .JSON_CONTENT_TYPE
                .equals(fileType) || file
                .getOriginalFilename().endsWith(".json")) {
            LOGGER.info(StringConstant
                    .PARSING_JSON);
            returnOrders = JsonUtil
                    .parseJson(inputStream);
        } else if (StringConstant
                .CSV_CONTENT_TYPE.equals(fileType) || file
                .getOriginalFilename().endsWith(".csv")) {
            LOGGER.info(StringConstant
                    .PARSING_CSV);
            returnOrders = CsvUtil
                    .parseCsv(inputStream);
        } else {
            LOGGER.error(StringConstant
                    .UNSUPPORTED_FILE_TYPE + fileType);
            throw new IllegalArgumentException(StringConstant
                    .UNSUPPORTED_FILE_TYPE + fileType);
        }

        if (!returnOrders.isEmpty()) {
            ReturnOrder updatedReturnOrder =
                    returnOrders.get(0);

            // Update fields from the parsed data
            existingReturnOrder.setBillingAddress(updatedReturnOrder
                    .getBillingAddress());
            existingReturnOrder.setActive(updatedReturnOrder
                    .isActive());
            existingReturnOrder.setTotalAmount(updatedReturnOrder
                    .getTotalAmount());
            existingReturnOrder.setType(updatedReturnOrder
                    .getType());
            existingReturnOrder
                    .setLastModifiedOn(new Date());

            // Save the updated return order
            ReturnOrder savedReturnOrder =
                    returnOrderRepo.save(existingReturnOrder);
            LOGGER.info(StringConstant
                    .ORDER_ITEM_UPDATE_SUCCESS_MESSAGE, savedReturnOrder
                    .getId());
        }

        LOGGER.info(StringConstant
                .SUCCESSFUL_ORDER_ITEM_UPDATE, returnOrders.size());
        return returnOrders
                .isEmpty() ? null : returnOrders.get(0);
    }
    /**
     * Deletes a return order by its ID.
     *
     * @param id the ID of the return order to be deleted
     * @throws ResourceNotFound if no return order
     * is found with the specified ID
     */
    public void deleteReturnOrder(final String id) {
        LOGGER.info(StringConstant
                .LOG_DELETING_RETURN_ORDER, id);
        Optional<ReturnOrder> order = getReturnOrderById(id);
        if (order.isPresent()) {
            returnOrderRepo.delete(order.get());
            LOGGER.info(StringConstant
                    .LOG_SUCCESS_DELETE_RETURN_ORDER, id);
        } else {
            LOGGER.error(StringConstant
                    .LOG_ERROR_DELETE_RETURN_ORDER, id);
            throw new ResourceNotFound(
                    "Order item is not found with ID: " + id);
        }
    }

    /**
     * Gets return orders by customer id.
     *
     * @param customerId the customer id
     * @return the return orders by customer id
     */
    public List<ReturnOrder>
    getReturnOrderByCustomerId(final String customerId) {
        LOGGER.info(StringConstant.LOG_FETCHING_RETURN_ORDER, customerId);
        List<ReturnOrder> returnOrder = returnOrderRepo
                .findByCustomerId(customerId);
        if (returnOrder.isEmpty()) {
            LOGGER.info(StringConstant
                    .LOG_FOUND_RETURN_ORDER, customerId);
        } else {
            LOGGER.warn(StringConstant
                    .LOG_NOT_FOUND_RETURN_ORDER, customerId);
        }
        return returnOrder;
    }

    /**
     * Gets return orders by po number.
     *
     * @param poNumber the po number
     * @return the return orders by po number
     */
    public List<ReturnOrder>
    getReturnOrdersByPoNumber(final String poNumber) {
        LOGGER.info(StringConstant.LOG_FETCHING_RETURN_ORDER, poNumber);
        List<ReturnOrder> returnOrder = returnOrderRepo
                .findByPoNumber(poNumber);
        if (returnOrder.isEmpty()) {
            LOGGER.info(StringConstant
                    .LOG_FOUND_RETURN_ORDER, poNumber);
        } else {
            LOGGER.warn(StringConstant
                    .LOG_NOT_FOUND_RETURN_ORDER, poNumber);
        }
        return returnOrder;
    }

    /**
     * Gets return orders by web store id.
     *
     * @param webStoreId the web store id
     * @return the return orders by web store id
     */
    public List<ReturnOrder>
    getReturnOrdersByWebStoreId(final String webStoreId) {
        LOGGER.info(StringConstant.LOG_FETCHING_RETURN_ORDER, webStoreId);
        List<ReturnOrder> returnOrder = returnOrderRepo
                .findByWebStoreId(webStoreId);
        if (returnOrder.isEmpty()) {
            LOGGER.info(StringConstant
                    .LOG_FOUND_RETURN_ORDER, webStoreId);
        } else {
            LOGGER.warn(StringConstant
                    .LOG_NOT_FOUND_RETURN_ORDER, webStoreId);
        }
        return returnOrder;
    }

}


