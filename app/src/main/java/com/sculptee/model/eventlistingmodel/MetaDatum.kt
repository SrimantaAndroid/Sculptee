package com.sculptee.model.eventlistingmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class MetaDatum {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("key")
    @Expose
    private var key: String? = null
    @SerializedName("value")
    @Expose
    private var value: String? = null

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getKey(): String? {
        return key
    }

    fun setKey(key: String) {
        this.key = key
    }

    fun getValue(): String? {
        return value
    }

    fun setValue(value: String) {
        this.value = value
    }
}