package com.iks.commerce.returnorder.repository;

import com.iks.commerce.returnorder.entity.ReturnOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


/**
 * The interface Return order repo.
 */
public interface ReturnOrderRepository extends
        MongoRepository<ReturnOrder, String> {


    /**
     * Find by id and customer id optional.
     *
     * @param customerId the id
     * @return the optional
     */
// Find by customerId
    List<ReturnOrder> findByCustomerId(String customerId);

    /**
     * Find by po number list.
     *
     * @param poNumber the po number
     * @return the list
     */
// Find by poNumber
    List<ReturnOrder> findByPoNumber(String poNumber);

    /**
     * Find by web store id list.
     *
     * @param webStoreId the web store id
     * @return the list
     */
// Find by webStoreId
    List<ReturnOrder> findByWebStoreId(String webStoreId);
}
