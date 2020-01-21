package com.sculptee.model.address

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class AddressModel {
    @SerializedName("ID")
    @Expose
    private var iD: String? = null
    @SerializedName("user_id")
    @Expose
    private var userId: String? = null
    @SerializedName("address_for")
    @Expose
    private var addressFor: String? = null
    @SerializedName("address_type")
    @Expose
    private var addressType: String? = null
    @SerializedName("is_default")
    @Expose
    private var isDefault: String? = null
    @SerializedName("email")
    @Expose
    private var email: String? = null
    @SerializedName("first_name")
    @Expose
    private var firstName: String? = null
    @SerializedName("last_name")
    @Expose
    private var lastName: String? = null
    @SerializedName("company")
    @Expose
    private var company: String? = null
    @SerializedName("address_1")
    @Expose
    private var address1: String? = null
    @SerializedName("address_2")
    @Expose
    private var address2: String? = null
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
    @SerializedName("phone")
    @Expose
    private var phone: String? = null
    @SerializedName("add_date")
    @Expose
    private var addDate: String? = null
    @SerializedName("update_date")
    @Expose
    private var updateDate: String? = null

    fun getID(): String? {
        return iD
    }

    fun setID(iD: String) {
        this.iD = iD
    }

    fun getUserId(): String? {
        return userId
    }

    fun setUserId(userId: String) {
        this.userId = userId
    }

    fun getAddressFor(): String? {
        return addressFor
    }

    fun setAddressFor(addressFor: String) {
        this.addressFor = addressFor
    }

    fun getAddressType(): String? {
        return addressType
    }

    fun setAddressType(addressType: String) {
        this.addressType = addressType
    }

    fun getIsDefault(): String? {
        return isDefault
    }

    fun setIsDefault(isDefault: String) {
        this.isDefault = isDefault
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String) {
        this.email = email
    }

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

    fun getCompany(): String? {
        return company
    }

    fun setCompany(company: String) {
        this.company = company
    }

    fun getAddress1(): String? {
        return address1
    }

    fun setAddress1(address1: String) {
        this.address1 = address1
    }

    fun getAddress2(): String? {
        return address2
    }

    fun setAddress2(address2: String) {
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

    fun getPhone(): String? {
        return phone
    }

    fun setPhone(phone: String) {
        this.phone = phone
    }

    fun getAddDate(): String? {
        return addDate
    }

    fun setAddDate(addDate: String) {
        this.addDate = addDate
    }

    fun getUpdateDate(): String? {
        return updateDate
    }

    fun setUpdateDate(updateDate: String) {
        this.updateDate = updateDate
    }

}