package com.sculptee.model.eventlistingmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class DefaultAttribute {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("name")
    @Expose
    private var name: String? = null
    @SerializedName("option")
    @Expose
    private var option: String? = null

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getOption(): String? {
        return option
    }

    fun setOption(option: String) {
        this.option = option
    }

}