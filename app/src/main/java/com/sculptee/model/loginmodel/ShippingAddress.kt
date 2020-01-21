package com.sculptee.model.loginmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class ShippingAddress {
    @SerializedName("first_name")
    @Expose
    private var firstName: Any? = null
    @SerializedName("last_name")
    @Expose
    private var lastName: Any? = null
    @SerializedName("company")
    @Expose
    private var company: Any? = null
    @SerializedName("address_1")
    @Expose
    private var address1: Any? = null
    @SerializedName("address_2")
    @Expose
    private var address2: Any? = null
    @SerializedName("city")
    @Expose
    private var city: Any? = null
    @SerializedName("state")
    @Expose
    private var state: Any? = null
    @SerializedName("postcode")
    @Expose
    private var postcode: Any? = null
    @SerializedName("country")
    @Expose
    private var country: Any? = null
    @SerializedName("phone")
    @Expose
    private var phone: Any? = null

    fun getFirstName(): Any? {
        return firstName
    }

    fun setFirstName(firstName: Any) {
        this.firstName = firstName
    }

    fun getLastName(): Any? {
        return lastName
    }

    fun setLastName(lastName: Any) {
        this.lastName = lastName
    }

    fun getCompany(): Any? {
        return company
    }

    fun setCompany(company: Any) {
        this.company = company
    }

    fun getAddress1(): Any? {
        return address1
    }

    fun setAddress1(address1: Any) {
        this.address1 = address1
    }

    fun getAddress2(): Any? {
        return address2
    }

    fun setAddress2(address2: Any) {
        this.address2 = address2
    }

    fun getCity(): Any? {
        return city
    }

    fun setCity(city: Any) {
        this.city = city
    }

    fun getState(): Any? {
        return state
    }

    fun setState(state: Any) {
        this.state = state
    }

    fun getPostcode(): Any? {
        return postcode
    }

    fun setPostcode(postcode: Any) {
        this.postcode = postcode
    }

    fun getCountry(): Any? {
        return country
    }

    fun setCountry(country: Any) {
        this.country = country
    }

    fun getPhone(): Any? {
        return phone
    }

    fun setPhone(phone: Any) {
        this.phone = phone
    }
}