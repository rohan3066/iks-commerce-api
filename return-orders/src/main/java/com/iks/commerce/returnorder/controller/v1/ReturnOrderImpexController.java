package com.iks.commerce.returnorder.controller.v1;

import com.iks.commerce.returnorder.exception.ResourceNotFound;
import com.iks.commerce.returnorder.entity.ReturnOrder;
import com.iks.commerce.returnorder.service.ReturnOrderImpexService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Optional;

import static com.iks.commerce.returnorder.service.ReturnOrderService.LOGGER;

/**
 * The type Return order controller.
 */
@RestController
@RequestMapping("/v1/impex-api-return-order")
public class ReturnOrderImpexController {

    /**
     * The ReturnOrderService instance.
     */
    private final ReturnOrderImpexService returnOrderService;

    /**
     * Instantiates a new Return order controller.
     *
     * @param service the return order service
     */
    public ReturnOrderImpexController(
            final ReturnOrderImpexService service) {
        this.returnOrderService = service;
    }

    /**
     * Add a return order by processing a file.
     *
     * @param file the return order file to be uploaded
     * @return a {@link ResponseEntity} containing the created
     * return order and HTTP status {@link HttpStatus#CREATED}
     */
    @PostMapping("/upload")
    public ResponseEntity<?> addReturnOrder(
            @RequestParam("file") final MultipartFile file) {
        try {

            List<ReturnOrder> returnOrders = returnOrderService
                    .processReturnOrderFile(file);

            // Return success response with CREATED status
            return ResponseEntity.ok(returnOrders);
        } catch (Exception e) {
            LOGGER.error("Error uploading return order file", e);


            return ResponseEntity
                    .badRequest()
                    .body("Error uploading return order file: " + e
                            .getMessage());
        }
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
        return new ResponseEntity<>(
                returnOrder, HttpStatus.OK);
    }

    /**
     * Update an existing return order.
     *
     * @param id
     * the ID of the return order to be updated
     * @param file
     * the file containing the updated return order details
     * @return a {@link ResponseEntity} containing the updated
     * return order and HTTP status {@link HttpStatus#OK}
     * @throws IllegalArgumentException
     * if invalid input is provided
     * @throws RuntimeException
     * if an error occurs during the update
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateReturnOrder(
            @PathVariable final String id,
            @RequestParam("file") final MultipartFile file) {
        try {
            ReturnOrder updatedReturnOrder =
                    returnOrderService
                    .updateReturnOrderFromFile(id, file);

            // Return a success response with updated return order details
            return ResponseEntity.ok(
                    "Return order with ID " + updatedReturnOrder
                            .getId() + " updated successfully.");
        } catch (IllegalArgumentException e) {
            // Handle invalid input or order not found
            return ResponseEntity
                    .badRequest()
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseEntity
                    .status(HttpStatus
                            .INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
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
