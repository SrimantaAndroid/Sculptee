package com.sculptee.model.events

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Links {
    @SerializedName("self")
    @Expose
    private var self: List<Self>? = null
    @SerializedName("collection")
    @Expose
    private var collection: List<Collection>? = null

    fun getSelf(): List<Self>? {
        return self
    }

    fun setSelf(self: List<Self>) {
        this.self = self
    }

    fun getCollection(): List<Collection>? {
        return collection
    }

    fun setCollection(collection: List<Collection>) {
        this.collection = collection
    }

}