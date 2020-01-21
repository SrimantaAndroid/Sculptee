package com.sculptee.model.eventlistingmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class EventListResponse {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("name")
    @Expose
    private var name: String? = null
    @SerializedName("slug")
    @Expose
    private var slug: String? = null
    @SerializedName("permalink")
    @Expose
    private var permalink: String? = null
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
    @SerializedName("type")
    @Expose
    private var type: String? = null
    @SerializedName("status")
    @Expose
    private var status: String? = null
    @SerializedName("featured")
    @Expose
    private var featured: Boolean? = null
    @SerializedName("catalog_visibility")
    @Expose
    private var catalogVisibility: String? = null
    @SerializedName("description")
    @Expose
    private var description: String? = null
    @SerializedName("short_description")
    @Expose
    private var shortDescription: String? = null
    @SerializedName("sku")
    @Expose
    private var sku: String? = null
    @SerializedName("price")
    @Expose
    private var price: String? = null
    @SerializedName("regular_price")
    @Expose
    private var regularPrice: String? = null
    @SerializedName("sale_price")
    @Expose
    private var salePrice: String? = null
    @SerializedName("date_on_sale_from")
    @Expose
    private var dateOnSaleFrom: Any? = null
    @SerializedName("date_on_sale_from_gmt")
    @Expose
    private var dateOnSaleFromGmt: Any? = null
    @SerializedName("date_on_sale_to")
    @Expose
    private var dateOnSaleTo: Any? = null
    @SerializedName("date_on_sale_to_gmt")
    @Expose
    private var dateOnSaleToGmt: Any? = null
    @SerializedName("price_html")
    @Expose
    private var priceHtml: String? = null
    @SerializedName("on_sale")
    @Expose
    private var onSale: Boolean? = null
    @SerializedName("purchasable")
    @Expose
    private var purchasable: Boolean? = null
    @SerializedName("total_sales")
    @Expose
    private var totalSales: Int? = null
    @SerializedName("virtual")
    @Expose
    private var virtual: Boolean? = null
    @SerializedName("downloadable")
    @Expose
    private var downloadable: Boolean? = null
    @SerializedName("downloads")
    @Expose
    private var downloads: List<Any>? = null
    @SerializedName("download_limit")
    @Expose
    private var downloadLimit: Int? = null
    @SerializedName("download_expiry")
    @Expose
    private var downloadExpiry: Int? = null
    @SerializedName("external_url")
    @Expose
    private var externalUrl: String? = null
    @SerializedName("button_text")
    @Expose
    private var buttonText: String? = null
    @SerializedName("tax_status")
    @Expose
    private var taxStatus: String? = null
    @SerializedName("tax_class")
    @Expose
    private var taxClass: String? = null
    @SerializedName("manage_stock")
    @Expose
    private var manageStock: Boolean? = null
    @SerializedName("stock_quantity")
    @Expose
    private var stockQuantity: Int? = null
    @SerializedName("stock_status")
    @Expose
    private var stockStatus: String? = null
    @SerializedName("backorders")
    @Expose
    private var backorders: String? = null
    @SerializedName("backorders_allowed")
    @Expose
    private var backordersAllowed: Boolean? = null
    @SerializedName("backordered")
    @Expose
    private var backordered: Boolean? = null
    @SerializedName("sold_individually")
    @Expose
    private var soldIndividually: Boolean? = null
    @SerializedName("weight")
    @Expose
    private var weight: String? = null
    @SerializedName("dimensions")
    @Expose
    private var dimensions: Dimensions? = null
    @SerializedName("shipping_required")
    @Expose
    private var shippingRequired: Boolean? = null
    @SerializedName("shipping_taxable")
    @Expose
    private var shippingTaxable: Boolean? = null
    @SerializedName("shipping_class")
    @Expose
    private var shippingClass: String? = null
    @SerializedName("shipping_class_id")
    @Expose
    private var shippingClassId: Int? = null
    @SerializedName("reviews_allowed")
    @Expose
    private var reviewsAllowed: Boolean? = null
    @SerializedName("average_rating")
    @Expose
    private var averageRating: String? = null
    @SerializedName("rating_count")
    @Expose
    private var ratingCount: Int? = null
    @SerializedName("related_ids")
    @Expose
    private var relatedIds: List<Int>? = null
    @SerializedName("upsell_ids")
    @Expose
    private var upsellIds: List<Int>? = null
    @SerializedName("cross_sell_ids")
    @Expose
    private var crossSellIds: List<Int>? = null
    @SerializedName("parent_id")
    @Expose
    private var parentId: Int? = null
    @SerializedName("purchase_note")
    @Expose
    private var purchaseNote: String? = null
    @SerializedName("categories")
    @Expose
    private var categories: List<Category>? = null
    @SerializedName("tags")
    @Expose
    private var tags: List<Any>? = null
    @SerializedName("images")
    @Expose
    private var images: List<Image>? = null
    @SerializedName("attributes")
    @Expose
    private var attributes: List<Attribute>? = null
    @SerializedName("default_attributes")
    @Expose
    private var defaultAttributes: List<DefaultAttribute>? = null
    @SerializedName("variations")
    @Expose
    private var variations: List<Int>? = null
    @SerializedName("grouped_products")
    @Expose
    private var groupedProducts: List<Any>? = null
    @SerializedName("menu_order")
    @Expose
    private var menuOrder: Int? = null
    @SerializedName("meta_data")
    @Expose
    private var metaData: List<MetaDatum>? = null
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

    fun getPermalink(): String? {
        return permalink
    }

    fun setPermalink(permalink: String) {
        this.permalink = permalink
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

    fun getType(): String? {
        return type
    }

    fun setType(type: String) {
        this.type = type
    }

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String) {
        this.status = status
    }

    fun getFeatured(): Boolean? {
        return featured
    }

    fun setFeatured(featured: Boolean?) {
        this.featured = featured
    }

    fun getCatalogVisibility(): String? {
        return catalogVisibility
    }

    fun setCatalogVisibility(catalogVisibility: String) {
        this.catalogVisibility = catalogVisibility
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String) {
        this.description = description
    }

    fun getShortDescription(): String? {
        return shortDescription
    }

    fun setShortDescription(shortDescription: String) {
        this.shortDescription = shortDescription
    }

    fun getSku(): String? {
        return sku
    }

    fun setSku(sku: String) {
        this.sku = sku
    }

    fun getPrice(): String? {
        return price
    }

    fun setPrice(price: String) {
        this.price = price
    }

    fun getRegularPrice(): String? {
        return regularPrice
    }

    fun setRegularPrice(regularPrice: String) {
        this.regularPrice = regularPrice
    }

    fun getSalePrice(): String? {
        return salePrice
    }

    fun setSalePrice(salePrice: String) {
        this.salePrice = salePrice
    }

    fun getDateOnSaleFrom(): Any? {
        return dateOnSaleFrom
    }

    fun setDateOnSaleFrom(dateOnSaleFrom: Any) {
        this.dateOnSaleFrom = dateOnSaleFrom
    }

    fun getDateOnSaleFromGmt(): Any? {
        return dateOnSaleFromGmt
    }

    fun setDateOnSaleFromGmt(dateOnSaleFromGmt: Any) {
        this.dateOnSaleFromGmt = dateOnSaleFromGmt
    }

    fun getDateOnSaleTo(): Any? {
        return dateOnSaleTo
    }

    fun setDateOnSaleTo(dateOnSaleTo: Any) {
        this.dateOnSaleTo = dateOnSaleTo
    }

    fun getDateOnSaleToGmt(): Any? {
        return dateOnSaleToGmt
    }

    fun setDateOnSaleToGmt(dateOnSaleToGmt: Any) {
        this.dateOnSaleToGmt = dateOnSaleToGmt
    }

    fun getPriceHtml(): String? {
        return priceHtml
    }

    fun setPriceHtml(priceHtml: String) {
        this.priceHtml = priceHtml
    }

    fun getOnSale(): Boolean? {
        return onSale
    }

    fun setOnSale(onSale: Boolean?) {
        this.onSale = onSale
    }

    fun getPurchasable(): Boolean? {
        return purchasable
    }

    fun setPurchasable(purchasable: Boolean?) {
        this.purchasable = purchasable
    }

    fun getTotalSales(): Int? {
        return totalSales
    }

    fun setTotalSales(totalSales: Int?) {
        this.totalSales = totalSales
    }

    fun getVirtual(): Boolean? {
        return virtual
    }

    fun setVirtual(virtual: Boolean?) {
        this.virtual = virtual
    }

    fun getDownloadable(): Boolean? {
        return downloadable
    }

    fun setDownloadable(downloadable: Boolean?) {
        this.downloadable = downloadable
    }

    fun getDownloads(): List<Any>? {
        return downloads
    }

    fun setDownloads(downloads: List<Any>) {
        this.downloads = downloads
    }

    fun getDownloadLimit(): Int? {
        return downloadLimit
    }

    fun setDownloadLimit(downloadLimit: Int?) {
        this.downloadLimit = downloadLimit
    }

    fun getDownloadExpiry(): Int? {
        return downloadExpiry
    }

    fun setDownloadExpiry(downloadExpiry: Int?) {
        this.downloadExpiry = downloadExpiry
    }

    fun getExternalUrl(): String? {
        return externalUrl
    }

    fun setExternalUrl(externalUrl: String) {
        this.externalUrl = externalUrl
    }

    fun getButtonText(): String? {
        return buttonText
    }

    fun setButtonText(buttonText: String) {
        this.buttonText = buttonText
    }

    fun getTaxStatus(): String? {
        return taxStatus
    }

    fun setTaxStatus(taxStatus: String) {
        this.taxStatus = taxStatus
    }

    fun getTaxClass(): String? {
        return taxClass
    }

    fun setTaxClass(taxClass: String) {
        this.taxClass = taxClass
    }

    fun getManageStock(): Boolean? {
        return manageStock
    }

    fun setManageStock(manageStock: Boolean?) {
        this.manageStock = manageStock
    }

    fun getStockQuantity(): Int? {
        return stockQuantity
    }

    fun setStockQuantity(stockQuantity: Int?) {
        this.stockQuantity = stockQuantity
    }

    fun getStockStatus(): String? {
        return stockStatus
    }

    fun setStockStatus(stockStatus: String) {
        this.stockStatus = stockStatus
    }

    fun getBackorders(): String? {
        return backorders
    }

    fun setBackorders(backorders: String) {
        this.backorders = backorders
    }

    fun getBackordersAllowed(): Boolean? {
        return backordersAllowed
    }

    fun setBackordersAllowed(backordersAllowed: Boolean?) {
        this.backordersAllowed = backordersAllowed
    }

    fun getBackordered(): Boolean? {
        return backordered
    }

    fun setBackordered(backordered: Boolean?) {
        this.backordered = backordered
    }

    fun getSoldIndividually(): Boolean? {
        return soldIndividually
    }

    fun setSoldIndividually(soldIndividually: Boolean?) {
        this.soldIndividually = soldIndividually
    }

    fun getWeight(): String? {
        return weight
    }

    fun setWeight(weight: String) {
        this.weight = weight
    }

    fun getDimensions(): Dimensions? {
        return dimensions
    }

    fun setDimensions(dimensions: Dimensions) {
        this.dimensions = dimensions
    }

    fun getShippingRequired(): Boolean? {
        return shippingRequired
    }

    fun setShippingRequired(shippingRequired: Boolean?) {
        this.shippingRequired = shippingRequired
    }

    fun getShippingTaxable(): Boolean? {
        return shippingTaxable
    }

    fun setShippingTaxable(shippingTaxable: Boolean?) {
        this.shippingTaxable = shippingTaxable
    }

    fun getShippingClass(): String? {
        return shippingClass
    }

    fun setShippingClass(shippingClass: String) {
        this.shippingClass = shippingClass
    }

    fun getShippingClassId(): Int? {
        return shippingClassId
    }

    fun setShippingClassId(shippingClassId: Int?) {
        this.shippingClassId = shippingClassId
    }

    fun getReviewsAllowed(): Boolean? {
        return reviewsAllowed
    }

    fun setReviewsAllowed(reviewsAllowed: Boolean?) {
        this.reviewsAllowed = reviewsAllowed
    }

    fun getAverageRating(): String? {
        return averageRating
    }

    fun setAverageRating(averageRating: String) {
        this.averageRating = averageRating
    }

    fun getRatingCount(): Int? {
        return ratingCount
    }

    fun setRatingCount(ratingCount: Int?) {
        this.ratingCount = ratingCount
    }

    fun getRelatedIds(): List<Int>? {
        return relatedIds
    }

    fun setRelatedIds(relatedIds: List<Int>) {
        this.relatedIds = relatedIds
    }

    fun getUpsellIds(): List<Int>? {
        return upsellIds
    }

    fun setUpsellIds(upsellIds: List<Int>) {
        this.upsellIds = upsellIds
    }

    fun getCrossSellIds(): List<Int>? {
        return crossSellIds
    }

    fun setCrossSellIds(crossSellIds: List<Int>) {
        this.crossSellIds = crossSellIds
    }

    fun getParentId(): Int? {
        return parentId
    }

    fun setParentId(parentId: Int?) {
        this.parentId = parentId
    }

    fun getPurchaseNote(): String? {
        return purchaseNote
    }

    fun setPurchaseNote(purchaseNote: String) {
        this.purchaseNote = purchaseNote
    }

    fun getCategories(): List<Category>? {
        return categories
    }

    fun setCategories(categories: List<Category>) {
        this.categories = categories
    }

    fun getTags(): List<Any>? {
        return tags
    }

    fun setTags(tags: List<Any>) {
        this.tags = tags
    }

    fun getImages(): List<Image>? {
        return images
    }

    fun setImages(images: List<Image>) {
        this.images = images
    }

    fun getAttributes(): List<Attribute>? {
        return attributes
    }

    fun setAttributes(attributes: List<Attribute>) {
        this.attributes = attributes
    }

    fun getDefaultAttributes(): List<DefaultAttribute>? {
        return defaultAttributes
    }

    fun setDefaultAttributes(defaultAttributes: List<DefaultAttribute>) {
        this.defaultAttributes = defaultAttributes
    }

    fun getVariations(): List<Int>? {
        return variations
    }

    fun setVariations(variations: List<Int>) {
        this.variations = variations
    }

    fun getGroupedProducts(): List<Any>? {
        return groupedProducts
    }

    fun setGroupedProducts(groupedProducts: List<Any>) {
        this.groupedProducts = groupedProducts
    }

    fun getMenuOrder(): Int? {
        return menuOrder
    }

    fun setMenuOrder(menuOrder: Int?) {
        this.menuOrder = menuOrder
    }

    fun getMetaData(): List<MetaDatum>? {
        return metaData
    }

    fun setMetaData(metaData: List<MetaDatum>) {
        this.metaData = metaData
    }

    fun getLinks(): Links? {
        return links
    }

    fun setLinks(links: Links) {
        this.links = links
    }


}
