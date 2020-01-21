package com.sculptee.model.orderlist

data class OrderListModel(val prod_img:String,val p_id:Int,val p_name:String,val  p_type:String,val P_flavour:String,var p_price:String, val p_weight:Int,
                          val p_status:String,val p_delivarydate:String,val p_paymentmethod:String,val p_methodtile:String,
                          val p_b_streetaddress:String,val p_b_city:String,val p_b_state:String,val p_b_pincode:String,val p_s_name:String,
                          val p_s_streetname:String,val p_s_city:String,val p_s_state:String,val p_s_pincode:String,val p_item_cost:String,val p_packagecost:Int,val p_tax:Int,
                          val p_s_cost:String,val p_tax_gst: Int,val islast:Boolean,val total_amount:Float,val order_invoice_url:String) {
}