package com.sculptee.model.eventlistingmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Dimensions {
    @SerializedName("length")
    @Expose
    private var length: String? = null
    @SerializedName("width")
    @Expose
    private var width: String? = null
    @SerializedName("height")
    @Expose
    private var height: String? = null

    fun getLength(): String? {
        return length
    }

    fun setLength(length: String) {
        this.length = length
    }

    fun getWidth(): String? {
        return width
    }

    fun setWidth(width: String) {
        this.width = width
    }

    fun getHeight(): String? {
        return height
    }

    fun setHeight(height: String) {
        this.height = height
    }

}