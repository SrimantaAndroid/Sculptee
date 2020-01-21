package com.sculptee.model.eventlistingmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Attribute {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("name")
    @Expose
    private var name: String? = null
    @SerializedName("position")
    @Expose
    private var position: Int? = null
    @SerializedName("visible")
    @Expose
    private var visible: Boolean? = null
    @SerializedName("variation")
    @Expose
    private var variation: Boolean? = null
    @SerializedName("options")
    @Expose
    private var options: List<String>? = null

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

    fun getPosition(): Int? {
        return position
    }

    fun setPosition(position: Int?) {
        this.position = position
    }

    fun getVisible(): Boolean? {
        return visible
    }

    fun setVisible(visible: Boolean?) {
        this.visible = visible
    }

    fun getVariation(): Boolean? {
        return variation
    }

    fun setVariation(variation: Boolean?) {
        this.variation = variation
    }

    fun getOptions(): List<String>? {
        return options
    }

    fun setOptions(options: List<String>) {
        this.options = options
    }
}