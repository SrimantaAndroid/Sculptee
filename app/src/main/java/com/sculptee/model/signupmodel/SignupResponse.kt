package com.sculptee.model.signupmodel

import com.sculptee.model.loginmodel.ShippingAddress
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class SignupResponse {
    @SerializedName("ID")
    @Expose
    private var iD: String? = null
    @SerializedName("user_login")
    @Expose
    private var userLogin: String? = null
    @SerializedName("user_nicename")
    @Expose
    private var userNicename: String? = null
    @SerializedName("user_email")
    @Expose
    private var userEmail: String? = null
    @SerializedName("user_url")
    @Expose
    private var userUrl: String? = null
    @SerializedName("user_registered")
    @Expose
    private var userRegistered: String? = null
    @SerializedName("user_activation_key")
    @Expose
    private var userActivationKey: String? = null
    @SerializedName("user_status")
    @Expose
    private var userStatus: String? = null
    @SerializedName("display_name")
    @Expose
    private var displayName: String? = null
    @SerializedName("billing_address")
    @Expose
    private var billingAddress: BillingAddress? = null
    @SerializedName("shipping_address")
    @Expose
    private var shippingAddress: ShippingAddress? = null
    @SerializedName("formatted_billing_address")
    @Expose
    private var formattedBillingAddress: String? = null
    @SerializedName("formatted_shipping_address")
    @Expose
    private var formattedShippingAddress: String? = null
    @SerializedName("avatar_url")
    @Expose
    private var avatarUrl: Any? = null
    @SerializedName("order_ids")
    @Expose
    private var orderIds: String? = null
    @SerializedName("wishlists")
    @Expose
    private var wishlists: String? = null
    @SerializedName("device")
    @Expose
    private var device: String? = null
    @SerializedName("token")
    @Expose
    private var token: String? = null
    @SerializedName("row_ID")
    @Expose
    private var rowID: String? = null
    @SerializedName("code")
    @Expose
    private var code: String? = null

    @SerializedName("purchase_product_ids")
    @Expose
    private var purchaseProductIds: String? = null

    fun getPurchaseProductIds(): String? {
        return purchaseProductIds
    }

    fun setPurchaseProductIds(purchaseProductIds: String) {
        this.purchaseProductIds = purchaseProductIds
    }

    fun getID(): String? {
        return iD
    }

    fun setID(iD: String) {
        this.iD = iD
    }

    fun getUserLogin(): String? {
        return userLogin
    }

    fun setUserLogin(userLogin: String) {
        this.userLogin = userLogin
    }

    fun getUserNicename(): String? {
        return userNicename
    }

    fun setUserNicename(userNicename: String) {
        this.userNicename = userNicename
    }

    fun getUserEmail(): String? {
        return userEmail
    }

    fun setUserEmail(userEmail: String) {
        this.userEmail = userEmail
    }

    fun getUserUrl(): String? {
        return userUrl
    }

    fun setUserUrl(userUrl: String) {
        this.userUrl = userUrl
    }

    fun getUserRegistered(): String? {
        return userRegistered
    }

    fun setUserRegistered(userRegistered: String) {
        this.userRegistered = userRegistered
    }

    fun getUserActivationKey(): String? {
        return userActivationKey
    }

    fun setUserActivationKey(userActivationKey: String) {
        this.userActivationKey = userActivationKey
    }

    fun getUserStatus(): String? {
        return userStatus
    }

    fun setUserStatus(userStatus: String) {
        this.userStatus = userStatus
    }

    fun getDisplayName(): String? {
        return displayName
    }

    fun setDisplayName(displayName: String) {
        this.displayName = displayName
    }

    fun getBillingAddress(): BillingAddress? {
        return billingAddress
    }

    fun setBillingAddress(billingAddress: BillingAddress) {
        this.billingAddress = billingAddress
    }

    fun getShippingAddress(): ShippingAddress? {
        return shippingAddress
    }

    fun setShippingAddress(shippingAddress: ShippingAddress) {
        this.shippingAddress = shippingAddress
    }

    fun getFormattedBillingAddress(): String? {
        return formattedBillingAddress
    }

    fun setFormattedBillingAddress(formattedBillingAddress: String) {
        this.formattedBillingAddress = formattedBillingAddress
    }

    fun getFormattedShippingAddress(): String? {
        return formattedShippingAddress
    }

    fun setFormattedShippingAddress(formattedShippingAddress: String) {
        this.formattedShippingAddress = formattedShippingAddress
    }

    fun getAvatarUrl(): Any? {
        return avatarUrl
    }

    fun setAvatarUrl(avatarUrl: Any) {
        this.avatarUrl = avatarUrl
    }

    fun getOrderIds(): String? {
        return orderIds
    }

    fun setOrderIds(orderIds: String) {
        this.orderIds = orderIds
    }

    fun getWishlists(): String? {
        return wishlists
    }

    fun setWishlists(wishlists: String) {
        this.wishlists = wishlists
    }

    fun getDevice(): String? {
        return device
    }

    fun setDevice(device: String) {
        this.device = device
    }

    fun getToken(): String? {
        return token
    }

    fun setToken(token: String) {
        this.token = token
    }

    fun getRowID(): String? {
        return rowID
    }

    fun setRowID(rowID: String) {
        this.rowID = rowID
    }

    fun getCode(): String? {
        return code
    }

    fun setCode(code: String) {
        this.code = code
    }
}