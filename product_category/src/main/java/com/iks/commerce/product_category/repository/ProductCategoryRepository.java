package com.iks.commerce.product_category.repository;

import com.iks.commerce.product_category.entity.ProductCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Product category repository.
 */
@Repository
public interface ProductCategoryRepository
        extends MongoRepository<ProductCategory, String> {
    /**
     * Find by name containing list.
     *
     * @param name the name
     * @return the list
     */
    List<ProductCategory>
    findByNameContaining(String name);

    /**
     * Find by description containing list.
     *
     * @param description the description
     * @return the list
     */
    List<ProductCategory> findByDescriptionContaining(
            String description);

    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<ProductCategory> findByName(
            String name);

    /**
     * Find by name containing or description containing list.
     *
     * @param name        the name
     * @param description the description
     * @return the list
     */
    List<ProductCategory> findByNameContainingOrDescriptionContaining(
            String name, String description);
}
