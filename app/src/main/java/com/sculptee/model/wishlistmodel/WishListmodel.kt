package com.sculptee.model.wishlistmodel

data class WishListmodel(val prod_img:String,val p_id:Int,val p_name:String,val  p_type:String,val P_flavour:String,var p_price:String, val p_weight:Int,
                         val rating_count:Int,val average_rating:String,val product_reviews_count:String, val withegg_per_pound:String,val eggless_per_pound:String) {
}