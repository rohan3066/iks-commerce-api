package com.iks.commerce.returnorder.controller.v1;

import com.iks.commerce.returnorder.exception.CustomMethodArgumentNotValidException;
import com.iks.commerce.returnorder.exception.ResourceNotFound;
import com.iks.commerce.returnorder.entity.ReturnOrder;
import com.iks.commerce.returnorder.service.ReturnOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Optional;

/**
 * The type Return order controller.
 */
@RestController
@RequestMapping("/v1/api-return-order")
public class ReturnOrderController {

    /**
     * The ReturnOrderService instance.
     */
    private final ReturnOrderService returnOrderService;

    /**
     * Instantiates a new Return order controller.
     *
     * @param service the return order service
     */
    public ReturnOrderController(final ReturnOrderService service) {
        this.returnOrderService = service;
    }

    /**
     * Add return order response entity.
     *
     * @param returnOrder   the return order
     * @param bindingResult the binding result
     * @return the response entity
     * @throws CustomMethodArgumentNotValidException
     * the custom method argument not valid exception
     */
    @PostMapping
    public ResponseEntity<?> addReturnOrder(
            @Valid @RequestBody final ReturnOrder returnOrder,
            final BindingResult bindingResult)
            throws CustomMethodArgumentNotValidException {
        if (bindingResult.hasErrors()) {
            throw new CustomMethodArgumentNotValidException(
                    null, bindingResult);
        }
        return new ResponseEntity<>(
                returnOrderService.addReturnOrder(returnOrder),
                HttpStatus.CREATED
        );
    }

    /**
     * Gets return order by id.
     *
     * @param id the id
     * @return the return order by id
     * @throws ResourceNotFound the resource not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getReturnOrderById(
            @PathVariable final String id)
            throws ResourceNotFound {

        Optional<ReturnOrder> returnOrder = returnOrderService
                .getReturnOrderById(id);
        if (returnOrder.isEmpty()) {
            throw new ResourceNotFound(
                    "Return order with ID " + id + " not found"
            );
        }
        return new ResponseEntity<>(returnOrder, HttpStatus.OK);
    }

    /**
     * Update return order response entity.
     *
     * @param id                 the id
     * @param updatedReturnOrder the updated return order
     * @param bindingResult      the binding result
     * @return the response entity
     * @throws CustomMethodArgumentNotValidException
     * the custom method argument not valid exception
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReturnOrder(
            @PathVariable final String id,
            @Valid @RequestBody final ReturnOrder updatedReturnOrder,
            final BindingResult bindingResult)
            throws CustomMethodArgumentNotValidException {

        if (bindingResult.hasErrors()) {
            throw new CustomMethodArgumentNotValidException(
                    null, bindingResult);
        }
        ReturnOrder updatedOrder = returnOrderService.updateReturnOrder(
                id, updatedReturnOrder);
        return ResponseEntity.ok(updatedOrder);
    }

    /**
     * Delete return order response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReturnOrder(
            @PathVariable final String id) {
        try {
            returnOrderService.deleteReturnOrder(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        }
    }

    /**
     * Gets return orders by customer id.
     *
     * @param customerId the customer id
     * @return the return orders by customer id
     * @throws ResourceNotFound the resource not found
     */
    @GetMapping("/customerId/{customerId}")
    public ResponseEntity<?>
    getReturnOrdersByCustomerId(
            @PathVariable final String customerId) throws ResourceNotFound {
        List<ReturnOrder> returnOrder = returnOrderService
                .getReturnOrderByCustomerId(customerId);
        if (returnOrder.isEmpty()) {
            throw new ResourceNotFound(
                    "Return order with customerId " + customerId + " not found"
            );
        }
        return new ResponseEntity<>(returnOrder, HttpStatus.OK);
    }

    /**
     * Gets return orders by po number.
     *
     * @param poNumber the po number
     * @return the return orders by po number
     * @throws ResourceNotFound the resource not found
     */
// Get ReturnOrders by poNumber
    @GetMapping("/poNumber/{poNumber}")
    public ResponseEntity<?>
    getReturnOrdersByPoNumber(
            @PathVariable final String poNumber) throws ResourceNotFound {
        List<ReturnOrder> returnOrder = returnOrderService
                .getReturnOrdersByPoNumber(poNumber);
        if (returnOrder.isEmpty()) {
            throw new ResourceNotFound(
                    "Return order with poNumber " + poNumber + " not found"
            );
        }
        return new ResponseEntity<>(returnOrder, HttpStatus.OK);
    }

    /**
     * Gets return orders by web store id.
     *
     * @param webStoreId the web store id
     * @return the return orders by web store id
     */
// Get ReturnOrders by webStoreId
    @GetMapping("/webStoreId/{webStoreId}")
    public ResponseEntity<?>
    getReturnOrdersByWebStoreId(
            @PathVariable final String webStoreId) {
        List<ReturnOrder> returnOrder = returnOrderService
                .getReturnOrdersByWebStoreId(webStoreId);
        if (returnOrder.isEmpty()) {
            throw new ResourceNotFound(
                    "Return order with webStoreId " + webStoreId + " not found"
            );
        }
        return new ResponseEntity<>(returnOrder, HttpStatus.OK);
    }
}
