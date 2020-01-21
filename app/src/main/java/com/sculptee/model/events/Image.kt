package com.sculptee.model.events

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Image {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("date_created")
    @Expose
    private var dateCreated: String? = null
    @SerializedName("date_created_gmt")
    @Expose
    private var dateCreatedGmt: String? = null
    @SerializedName("date_modified")
    @Expose
    private var dateModified: String? = null
    @SerializedName("date_modified_gmt")
    @Expose
    private var dateModifiedGmt: String? = null
    @SerializedName("src")
    @Expose
    private var src: String? = null
    @SerializedName("name")
    @Expose
    private var name: String? = null
    @SerializedName("alt")
    @Expose
    private var alt: String? = null

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getDateCreated(): String? {
        return dateCreated
    }

    fun setDateCreated(dateCreated: String) {
        this.dateCreated = dateCreated
    }

    fun getDateCreatedGmt(): String? {
        return dateCreatedGmt
    }

    fun setDateCreatedGmt(dateCreatedGmt: String) {
        this.dateCreatedGmt = dateCreatedGmt
    }

    fun getDateModified(): String? {
        return dateModified
    }

    fun setDateModified(dateModified: String) {
        this.dateModified = dateModified
    }

    fun getDateModifiedGmt(): String? {
        return dateModifiedGmt
    }

    fun setDateModifiedGmt(dateModifiedGmt: String) {
        this.dateModifiedGmt = dateModifiedGmt
    }

    fun getSrc(): String? {
        return src
    }

    fun setSrc(src: String) {
        this.src = src
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getAlt(): String? {
        return alt
    }

    fun setAlt(alt: String) {
        this.alt = alt
    }

}