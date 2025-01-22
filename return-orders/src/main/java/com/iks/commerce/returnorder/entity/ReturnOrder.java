package com.iks.commerce.returnorder.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * The type ReturnOrder.
 */
@Document(collection = "return_orders")
public class ReturnOrder {
    /**
     * The unique identifier for the return order.
     */
    @Id
    private String orderId;

    /**
     * The name of the order.
     */
    @NotBlank(message = "Name is mandatory")
    private String name;

    /**
     * The ID of the customer associated with the order.
     */
    @NotBlank(message = "Customer ID is mandatory")
    private String customerId;

    /**
     * The billing address for the order.
     */
    @NotBlank(message = "Billing address is mandatory")
    private String billingAddress;

    /**
     * The currency ISO code.
     */
    @NotBlank(message = "Currency ISO code is mandatory")
    @Pattern(
            regexp = "^[A-Z]{3}$",
            message = "Currency ISO code must be a 3-letter code")
    private String currencyISOCode;

    /**
     * The grand total amount for the order.
     */
    @Positive(message = "Grand total amount must be positive")
    private double grandTotalAmount;

    /**
     * The payment group ID associated with the order.
     */
    @NotBlank(message = "Payment group ID is mandatory")
    private String paymentGroupId;

    /**
     * The payment method ID for the order.
     */
    @NotBlank(message = "Payment method ID is mandatory")
    private String paymentMethodId;

    /**
     * The purchase order number.
     */
    @NotBlank(message = "Purchase order number is mandatory")
    private String poNumber;

    /**
     * Indicates whether the order is active.
     */
    private boolean active;

    /**
     * The web store ID associated with the order.
     */
    @NotBlank(message = "Web store ID is mandatory")
    private String webStoreId;

    /**
     * The list of tax types applicable to the order.
     */
    @NotNull(message = "Tax type is required")
    private List<String> taxType;

    /**
     * The total amount for the order.
     */
    @PositiveOrZero(message = "Total amount must be zero or positive")
    private double totalAmount;

    /**
     * The total product amount for the order.
     */
    @PositiveOrZero(message = "Total product amount must be zero or positive")
    private double totalProductAmount;

    /**
     * The total number of line items in the order.
     */
    @PositiveOrZero(
            message = "Total product line item count must be zero or positive")
    private int totalProductLineItemCount;

    /**
     * The total tax amount for products in the order.
     */
    @PositiveOrZero(
            message = "Total product tax amount must be zero or positive")
    private double totalProductTaxAmount;

    /**
     * The total count of products in the order.
     */
    @PositiveOrZero(message = "Total product count must be zero or positive")
    private int totalProductCount;

    /**
     * The total tax amount for the order.
     */
    @PositiveOrZero(message = "Total tax amount must be zero or positive")
    private double totalTaxAmount;

    /**
     * The list of types associated with the order.
     */
    @NotEmpty(message = "Type list cannot be empty")
    private List<String> type;

    /**
     * The count of unique products in the order.
     */
    @PositiveOrZero(message = "Unique product count must be zero or positive")
    private int uniqueProductCount;

    /**
     * The date when the order was last modified.
     */
    @PastOrPresent(message = "Last modified date cannot be in the future")
    private java.util.Date lastModifiedOn;

    /**
     * The date when the order was created.
     */
    @PastOrPresent(message = "Created date cannot be in the future")
    private java.util.Date createdOn;

    /**
     * The username of the person who modified the order.
     */
    @NotBlank(message = "Modified by is mandatory")
    private String modifiedBy;

    // Getters and setters using standard naming conventions

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return orderId;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(final String id) {
        this.orderId = id;
    }

    /**
     * Gets order name.
     *
     * @return the order name
     */
    public String getName() {
        return name;
    }

        /**
     * Sets order name.
     *
     * @param returnItem the order name
     */
    public void setReturnItem(final String returnItem) {
        this.name = returnItem;
    }

    /**
     * Gets customer id.
     *
     * @return the customer id
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Sets customer id.
     *
     * @param cusId the customer id
     */
    public void setCustomerId(final String cusId) {
        this.customerId = cusId;
    }

    /**
     * Gets billing address.
     *
     * @return the billing address
     */
    public String getBillingAddress() {
        return billingAddress;
    }

    /**
     * Sets billing address.
     *
     * @param billingAdd the billing address
     */
    public void setBillingAddress(final String billingAdd) {
        this.billingAddress = billingAdd;
    }

    /**
     * Gets currency iso code.
     *
     * @return the currency iso code
     */
    public String getCurrencyISOCode() {
        return currencyISOCode;
    }

    /**
     * Sets currency iso code.
     *
     * @param currCode the currency iso code
     */
    public void setCurrencyISOCode(final String currCode) {
        this.currencyISOCode = currCode;
    }

    /**
     * Gets grand total amount.
     *
     * @return the grand total amount
     */
    public double getGrandTotalAmount() {
        return grandTotalAmount;
    }

    /**
     * Sets grand total amount.
     *
     * @param grandTotal the grand total amount
     */
    public void setGrandTotalAmount(final double grandTotal) {
        this.grandTotalAmount = grandTotal;
    }

    /**
     * Gets payment group id.
     *
     * @return the payment group id
     */
    public String getPaymentGroupId() {
        return paymentGroupId;
    }

    /**
     * Sets payment group id.
     *
     * @param paymentGroId the payment group id
     */
    public void setPaymentGroupId(final String paymentGroId) {
        this.paymentGroupId = paymentGroId;
    }

    /**
     * Gets payment method id.
     *
     * @return the payment method id
     */
    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    /**
     * Sets payment method id.
     *
     * @param paymentId the payment method id
     */
    public void setPaymentMethodId(final String paymentId) {
        this.paymentMethodId = paymentId;
    }

    /**
     * Gets purchase order number.
     *
     * @return the purchase order number
     */
    public String getPoNumber() {
        return poNumber;
    }

    /**
     * Sets purchase order number.
     *
     * @param poNo the purchase order number
     */
    public void setPoNumber(final String poNo) {
        this.poNumber = poNo;
    }

    /**
     * Is active boolean.
     *
     * @return the boolean
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets active.
     *
     * @param status the active
     */
    public void setActive(final boolean status) {
        this.active = status;
    }

    /**
     * Gets web store id.
     *
     * @return the web store id
     */
    public String getWebStoreId() {
        return webStoreId;
    }

    /**
     * Sets web store id.
     *
     * @param wsId the web store id
     */
    public void setWebStoreId(final String wsId) {
        this.webStoreId = wsId;
    }

    /**
     * Gets tax type.
     *
     * @return the tax type
     */
    public List<String> getTaxType() {
        return taxType;
    }

    /**
     * Sets tax type.
     *
     * @param txType the tax type
     */
    public void setTaxType(final List<String> txType) {
        this.taxType = txType;
    }

    /**
     * Gets total amount.
     *
     * @return the total amount
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets total amount.
     *
     * @param total the total amount
     */
    public void setTotalAmount(final double total) {
        this.totalAmount = total;
    }

    /**
     * Gets total product amount.
     *
     * @return the total product amount
     */
    public double getTotalProductAmount() {
        return totalProductAmount;
    }

    /**
     * Sets total product amount.
     *
     * @param totalProAmount the total product amount
     */
    public void setTotalProductAmount(final double totalProAmount) {
        this.totalProductAmount = totalProAmount;
    }

    /**
     * Gets total product line item count.
     *
     * @return the total product line item count
     */
    public int getTotalProductLineItemCount() {
        return totalProductLineItemCount;
    }

    /**
     * Sets total product line item count.
     *
     * @param totalProductLineItemCountValue the total product line item count
     */
    public void setTotalProductLineItemCount(
            final int totalProductLineItemCountValue) {
        this.totalProductLineItemCount = totalProductLineItemCountValue;
    }

    /**
     * Gets total product tax amount.
     *
     * @return the total product tax amount
     */
    public double getTotalProductTaxAmount() {
        return totalProductTaxAmount;
    }

    /**
     * Sets total product tax amount.
     *
     * @param totalProductTaxAmountValue the total product tax amount
     */
    public void setTotalProductTaxAmount(
            final double totalProductTaxAmountValue) {
        this.totalProductTaxAmount = totalProductTaxAmountValue;
    }

    /**
     * Gets total product count.
     *
     * @return the total product count
     */
    public int getTotalProductCount() {
        return totalProductCount;
    }

    /**
     * Sets total product count.
     *
     * @param totalProductCountValue the total product count
     */
    public void setTotalProductCount(final int totalProductCountValue) {
        this.totalProductCount = totalProductCountValue;
    }

    /**
     * Gets total tax amount.
     *
     * @return the total tax amount
     */
    public double getTotalTaxAmount() {
        return totalTaxAmount;
    }

    /**
     * Sets total tax amount.
     *
     * @param totalTaxAmountValue the total tax amount
     */
    public void setTotalTaxAmount(final double totalTaxAmountValue) {
        this.totalTaxAmount = totalTaxAmountValue;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public List<String> getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param typeReturn the type
     */
    public void setType(final List<String> typeReturn) {
        this.type = typeReturn;
    }

    /**
     * Gets unique product count.
     *
     * @return the unique product count
     */
    public int getUniqueProductCount() {
        return uniqueProductCount;
    }

    /**
     * Sets unique product count.
     *
     * @param uniqueProductCountValue the unique product count
     */
    public void setUniqueProductCount(final int uniqueProductCountValue) {
        this.uniqueProductCount = uniqueProductCountValue;
    }

    /**
     * Gets last modified date.
     *
     * @return the last modified date
     */
    public java.util.Date getLastModifiedOn() {
        return lastModifiedOn;
    }

    /**
     * Sets last modified date.
     *
     * @param modifiedDate the last modified date
     */
    public void setLastModifiedOn(final java.util.Date modifiedDate) {
        this.lastModifiedOn = modifiedDate;
    }

    /**
     * Gets created date.
     *
     * @return the created date
     */
    public java.util.Date getCreatedOn() {
        return createdOn;
    }

    /**
     * Sets created date.
     *
     * @param createdAt the created date
     */
    public void setCreatedOn(final java.util.Date createdAt) {
        this.createdOn = createdAt;
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
     * @param modified the modified by
     */
    public void setModifiedBy(final String modified) {
        this.modifiedBy = modified;
    }

}
