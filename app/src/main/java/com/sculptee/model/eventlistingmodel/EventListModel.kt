package com.sculptee.model.eventlistingmodel

data class EventListModel(
    val id: Int,
    val name: String,
    val stock: Int,
    val average_rating: String,
    val rating_count: Int,
    val imgsrc: String?,
    val reviewcount:Int,
   val price:String
) {
}