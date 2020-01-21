package com.sculptee.model.events

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class EventResponse {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("name")
    @Expose
    private var name: String? = null
    @SerializedName("slug")
    @Expose
    private var slug: String? = null
    @SerializedName("parent")
    @Expose
    private var parent: Int? = null
    @SerializedName("description")
    @Expose
    private var description: String? = null
    @SerializedName("display")
    @Expose
    private var display: String? = null
    @SerializedName("image")
    @Expose
    private var image: Image? = null
    @SerializedName("menu_order")
    @Expose
    private var menuOrder: Int? = null
    @SerializedName("count")
    @Expose
    private var count: Int? = null
    @SerializedName("_links")
    @Expose
    private var links: Links? = null

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

    fun getSlug(): String? {
        return slug
    }

    fun setSlug(slug: String) {
        this.slug = slug
    }

    fun getParent(): Int? {
        return parent
    }

    fun setParent(parent: Int?) {
        this.parent = parent
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String) {
        this.description = description
    }

    fun getDisplay(): String? {
        return display
    }

    fun setDisplay(display: String) {
        this.display = display
    }

    fun getImage(): Image? {
        return image
    }

    fun setImage(image: Image) {
        this.image = image
    }

    fun getMenuOrder(): Int? {
        return menuOrder
    }

    fun setMenuOrder(menuOrder: Int?) {
        this.menuOrder = menuOrder
    }

    fun getCount(): Int? {
        return count
    }

    fun setCount(count: Int?) {
        this.count = count
    }

    fun getLinks(): Links? {
        return links
    }

    fun setLinks(links: Links) {
        this.links = links
    }
}