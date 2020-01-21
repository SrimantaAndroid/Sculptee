package com.sculptee.model.loginmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class BillingAddress {
    @SerializedName("first_name")
    @Expose
    private var firstName: String? = null
    @SerializedName("last_name")
    @Expose
    private var lastName: String? = null
    @SerializedName("company")
    @Expose
    private var company: Any? = null
    @SerializedName("address_1")
    @Expose
    private var address1: String? = null
    @SerializedName("address_2")
    @Expose
    private var address2: Any? = null
    @SerializedName("city")
    @Expose
    private var city: String? = null
    @SerializedName("state")
    @Expose
    private var state: String? = null
    @SerializedName("postcode")
    @Expose
    private var postcode: String? = null
    @SerializedName("country")
    @Expose
    private var country: String? = null
    @SerializedName("email")
    @Expose
    private var email: String? = null
    @SerializedName("phone")
    @Expose
    private var phone: String? = null

    fun getFirstName(): String? {
        return firstName
    }

    fun setFirstName(firstName: String) {
        this.firstName = firstName
    }

    fun getLastName(): String? {
        return lastName
    }

    fun setLastName(lastName: String) {
        this.lastName = lastName
    }

    fun getCompany(): Any? {
        return company
    }

    fun setCompany(company: Any) {
        this.company = company
    }

    fun getAddress1(): String? {
        return address1
    }

    fun setAddress1(address1: String) {
        this.address1 = address1
    }

    fun getAddress2(): Any? {
        return address2
    }

    fun setAddress2(address2: Any) {
        this.address2 = address2
    }

    fun getCity(): String? {
        return city
    }

    fun setCity(city: String) {
        this.city = city
    }

    fun getState(): String? {
        return state
    }

    fun setState(state: String) {
        this.state = state
    }

    fun getPostcode(): String? {
        return postcode
    }

    fun setPostcode(postcode: String) {
        this.postcode = postcode
    }

    fun getCountry(): String? {
        return country
    }

    fun setCountry(country: String) {
        this.country = country
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun getPhone(): String? {
        return phone
    }

    fun setPhone(phone: String) {
        this.phone = phone
    }
}