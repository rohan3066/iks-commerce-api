package com.iks.commerce.product_category.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
// Private constructor to prevent instantiation



/**
 * The type Product category.
 */
@Document(collection = "product_categories")
public class ProductCategory {
    /**
     * The type Product category.
     */
    @Id
    private String id;


    /**
     * The type Product category.
     */
    @NotNull(message = "Category Code is required")
    private String code; // Unique identifier for the category
    /**
     * The type Product category.
     */
    @NotBlank(message = "Category name is required")
    private String name;
    /**
     * The type Product category.
     */
    @NotBlank(message = "Description name is required")
    private String description;

    // Store related IDs as strings
    /**
     * The type Product category.
     */
    @NotNull(message = "CatalogID is required")
    private String catalogId;
    /**
     * The type Product category.
     */
    @NotNull(message = "GroupIds is required")
    private List<String> visibleToUserGroupIds;
    /**
     * The type Product category.
     */
    @NotNull(message = "Subcategory IDs is required")
    private List<String> subcategoryIds; // List of child ProductCategory IDs
    /**
     * The type Product category.
     */
    @NotNull(message = "Product Ids is required")
    private List<String> productIds; // List of ProductModel IDs
    /**
     * The type Product category.
     */
    @NotNull(message = "CreatedBy is required")
    private String createdBy;
    /**
     * The type Product category.
     */
    @NotNull(message = "ModifiedBy is required")
    private String modifiedBy;
    /**
     * The type Product category.
     */
    private java.util.Date createdOn;
    /**
     * The type Product category.
     */
    private java.util.Date modifiedOn;

    // Metadata
    /**
     * The type Product category.
     */
    @NotBlank(message = "seoKeywords name is required")
    private String seoKeywords;
    /**
     * The type Product category.
     */
    @NotBlank(message = "metaDescription name is required")
    private String metaDescription;

    /**
     * Gets id.
     *
     * @return the id
     */
// Getters and Setters
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param ids the id
     */
    public void setId(final String ids) {
        this.id = ids;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param codeWord the code
     */
    public void setCode(final String codeWord) {
        this.code = codeWord;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param names the name
     */
    public void setName(final String names) {
        this.name = names;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param descriptions the description
     */
    public void setDescription(final String descriptions) {
        this.description = descriptions;
    }

    /**
     * Gets catalog id.
     *
     * @return the catalog id
     */
    public String getCatalogId() {
        return catalogId;
    }

    /**
     * Sets catalog id.
     *
     * @param catalogIds the catalog id
     */
    public void setCatalogId(final String catalogIds) {
        this.catalogId = catalogIds;
    }

    /**
     * Gets visible to user group ids.
     *
     * @return the visible to user group ids
     */
    public List<String> getVisibleToUserGroupIds() {
        return visibleToUserGroupIds;
    }

    /**
     * Sets visible to user group ids.
     *
     * @param visibleToUserGroupIdss the visible to user group ids
     */
    public void setVisibleToUserGroupIds(
            final List<String> visibleToUserGroupIdss) {
        this.visibleToUserGroupIds = visibleToUserGroupIdss;
    }

    /**
     * Gets subcategory ids.
     *
     * @return the subcategory ids
     */
    public List<String> getSubcategoryIds() {
        return subcategoryIds;
    }

    /**
     * Sets subcategory ids.
     *
     * @param subcategoryIdss the subcategory ids
     */
    public void setSubcategoryIds(final List<String> subcategoryIdss) {
        this.subcategoryIds = subcategoryIdss;
    }

    /**
     * Gets product ids.
     *
     * @return the product ids
     */
    public List<String> getProductIds() {
        return productIds;
    }

    /**
     * Sets product ids.
     *
     * @param catagoryProductIds the product ids
     */
    public void setProductIds(final List<String> catagoryProductIds) {
        this.productIds = catagoryProductIds;
    }

    /**
     * Gets created by.
     *
     * @return the created by
     */
    public String getCreatedBy() {
        return createdBy;
    }


    /**
     * Sets created by.
     *
     * @param createBy the created by
     */
    public void setCreatedBy(final String createBy) {
        this.createdBy = createBy;
    }

    /**
     * Gets modified by.
     *
     * @return the modified by
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Sets modified by.
     *
     * @param modifyBy the modified by
     */
    public void setModifiedBy(final String modifyBy) {
        this.modifiedBy = modifyBy;
    }

    /**
     * Gets created on.
     *
     * @return the created on
     */
    public java.util.Date getCreatedOn() {
        return createdOn;
    }


    /**
     * Sets created on.
     *
     * @param createOn the created on
     */
    public void setCreatedOn(final java.util.Date createOn) {
        this.createdOn = createOn;
    }

    /**
     * Gets modified on.
     *
     * @return the modified on
     */
    public java.util.Date getModifiedOn() {
        return modifiedOn;
    }

    /**
     * Sets modified on.
     *
     * @param modifyOn the modified on
     */
    public void setModifiedOn(
            final java.util.Date modifyOn) {
        this.modifiedOn = modifyOn;
    }

    /**
     * Gets seo keywords.
     *
     * @return the seo keywords
     */
    public String getSeoKeywords() {
        return seoKeywords;
    }

    /**
     * Sets seo keywords.
     *
     * @param seoKeyword the seo keywords
     */
    public void setSeoKeywords(final String seoKeyword) {
        this.seoKeywords = seoKeyword;
    }

    /**
     * Gets meta description.
     *
     * @return the meta description
     */
    public String getMetaDescription() {
        return metaDescription;
    }

    /**
     * Sets meta description.
     *
     * @param metaDescriptions the meta description
     */
    public void setMetaDescription(final String metaDescriptions) {
        this.metaDescription = metaDescriptions;
    }
}
