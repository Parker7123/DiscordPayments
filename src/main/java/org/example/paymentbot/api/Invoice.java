package org.example.paymentbot.api;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Invoice {

    /**
     * Created
     * <p>
     *
     *
     */
    private String created;
    /**
     * Price
     * <p>
     *
     * (Required)
     *
     */
    private String price;
    /**
     * Store Id
     * <p>
     *
     *
     */
    private String storeId;
    /**
     * Currency
     * <p>
     *
     *
     */
    private String currency = "USD";
    /**
     * Paid Currency
     * <p>
     *
     *
     */
    private String paidCurrency = "";
    /**
     * Order Id
     * <p>
     *
     *
     */
    private String orderId = "";
    /**
     * Notification Url
     * <p>
     *
     *
     */
    private String notificationUrl = "";
    /**
     * Redirect Url
     * <p>
     *
     *
     */
    private String redirectUrl = "";
    /**
     * Buyer Email
     * <p>
     *
     *
     */
    private String buyerEmail = "";
    /**
     * Promocode
     * <p>
     *
     *
     */
    private String promocode = "";
    /**
     * Shipping Address
     * <p>
     *
     *
     */
    private String shippingAddress = "";
    /**
     * Notes
     * <p>
     *
     *
     */
    private String notes = "";
    /**
     * Discount
     * <p>
     *
     *
     */
    private String discount;
    /**
     * Status
     * <p>
     *
     *
     */
    private String status;
    /**
     * Products
     * <p>
     *
     *
     */
    private Object products;
    /**
     * Id
     * <p>
     *
     *
     */
    private String id;
    /**
     * User Id
     * <p>
     *
     * (Required)
     *
     */
    private String userId;
    /**
     * Time Left
     * <p>
     *
     * (Required)
     *
     */
    private Integer timeLeft;
    /**
     * Expiration
     * <p>
     *
     * (Required)
     *
     */
    private Integer expiration;
    /**
     * Expiration Seconds
     * <p>
     *
     * (Required)
     *
     */
    private Integer expirationSeconds;
    /**
     * Payments
     * <p>
     *
     *
     */
    private List<InvoiceCoin> payments = null;

}