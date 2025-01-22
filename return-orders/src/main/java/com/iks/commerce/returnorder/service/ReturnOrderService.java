package com.iks.commerce.returnorder.service;

import com.iks.commerce.returnorder.constant.StringConstant;
import com.iks.commerce.returnorder.exception.ResourceNotFound;
import com.iks.commerce.returnorder.entity.ReturnOrder;
import com.iks.commerce.returnorder.repository.ReturnOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;


/**
 * The type Return order service.
 * This service class handles the business logic for managing return orders.
 */
@Service
public class ReturnOrderService {

    /**
     * The type Return order service.
     * This service class handles the business logic for managing return orders.
     */
    public static final Logger LOGGER = LoggerFactory
            .getLogger(ReturnOrderService.class);
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
    public ReturnOrderService(final ReturnOrderRepository returnOrderR) {
        this.returnOrderRepo = returnOrderR;
    }

    /**
     * Adds a new return order.
     *
     * @param returnOrder the return order to be added
     * @return the added return order
     */
    public ReturnOrder addReturnOrder(
            final ReturnOrder returnOrder) {
        returnOrder.setCreatedOn(new Date());
        LOGGER.info(StringConstant.
                LOG_ADDING_RETURN_ORDER, returnOrder.getId());
        ReturnOrder savedOrder = returnOrderRepo.save(returnOrder);
        LOGGER.info(StringConstant.
                LOG_SUCCESS_ADD_RETURN_ORDER, savedOrder.getId());
        return savedOrder;
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
     * Updates an existing return order.
     *
     * @param id           the ID of the return order to be updated
     * @param updatedOrder the updated return order information
     * @return the updated return order
     * @throws ResourceNotFound if no return order
     * is found with the specified ID
     */
    public ReturnOrder updateReturnOrder(
            final String id, final ReturnOrder updatedOrder) {
        LOGGER.info(StringConstant
                .LOG_UPDATING_RETURN_ORDER, id);
        Optional<ReturnOrder>
                existingOrder = returnOrderRepo.findById(id);
        if (existingOrder.isPresent()) {
            ReturnOrder existingOrderInfo = existingOrder.get();
            existingOrderInfo
                    .setLastModifiedOn(new Date());
            existingOrderInfo
                    .setBillingAddress(updatedOrder.getBillingAddress());
            existingOrderInfo.setActive(updatedOrder.isActive());
            existingOrderInfo
                    .setTotalAmount(updatedOrder.getTotalAmount());
            existingOrderInfo.setType(updatedOrder.getType());
            LOGGER.info(StringConstant
                    .LOG_SUCCESS_UPDATE_RETURN_ORDER, id);
            return returnOrderRepo.save(existingOrderInfo);
        } else {
            LOGGER.error(StringConstant
                    .LOG_ERROR_UPDATE_RETURN_ORDER, id);
            throw new ResourceNotFound(
                    "Return order not found with ID: " + id);
        }
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


