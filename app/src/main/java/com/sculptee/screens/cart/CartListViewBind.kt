package com.sculptee.screens.cart

import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.mohammedalaa.seekbar.RangeSeekBarView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.R
import org.w3c.dom.Text

class CartListViewBind :DeviceResolution{
    var cartActivity: CartActivity?=null
    var view: View?=null
    var rec_cart_list:RecyclerView?=null
    var btn_process_ocheckout:Button?=null
    var img_back:ImageView?=null
    var tv_headertext:TextView?=null
    var tv_product_details:TextView?=null
    var tv_mycoupons:TextView?=null
    var tv_price_cart:TextView?=null
    var btn_updatecart:Button?=null
    var tv_summery:TextView?=null
    var btn_apply:Button?=null
    var tv_subtotal:TextView?=null
    var tv_subtotalval:TextView?=null
    var tv_total_price:TextView?=null
    var tv_total_price_text:TextView?=null
    var et_promocode:EditText?=null
    var rangeSeekbar: RangeSeekBarView?=null
    var tv_item:TextView?=null
    var tv_itemval:TextView?=null
    var tv_postagepack:TextView?=null
    var tv_postagepackval:TextView?=null
    var tv_shippingcost:TextView?=null
    var tv_shippingcostval:TextView?=null
    var tv_tax:TextView?=null
    var tv_gstval:TextView?=null
    var img_coupon:ImageView?=null
    var tv_apply_coupon:TextView?=null
    var rl_applycoupon:RelativeLayout?=null
    var tv_rangehader:TextView?=null
    var btn_apply_range:Button?=null
    var tv_minamount:TextView?=null
    var tv_coupondiscunt:TextView?=null
    var tv_applycoupon:TextView?=null
    var tv_advamount:TextView?=null
    var tv_advanceval:TextView?=null
    var et_coupon:EditText?=null
    var rl_coupon:RelativeLayout?=null
    var btn_apply_cupon:Button?=null
    var tv_gauestemail:TextView?=null
    var et_gauestemail:EditText?=null



    constructor(cartActivity: CartActivity, view: View) : super(cartActivity){
        this.cartActivity=cartActivity
        this.view=view
        setviewbinds();
        settypeface();

    }

    private fun settypeface() {
        tv_headertext?.typeface=getMavenProBold(cartActivity)
        tv_product_details?.typeface=getmavenproBlack(cartActivity)
        tv_mycoupons?.typeface=getmavenproBlack(cartActivity)
        tv_price_cart?.typeface=getmavenproBlack(cartActivity)
        btn_updatecart?.typeface=getMavenProBold(cartActivity)
        tv_summery?.typeface=getmavenproBlack(cartActivity)
        btn_apply?.typeface=getMavenProBold(cartActivity)
        btn_process_ocheckout?.typeface=getmavenproBlack(cartActivity)
        et_promocode?.typeface=getMavenProRegular(cartActivity)


        tv_subtotal?.typeface=getMavenProBold(cartActivity)
        tv_subtotalval?.typeface=getMavenProBold(cartActivity)
        tv_total_price_text?.typeface=getMavenProBold(cartActivity)
        tv_total_price?.typeface=getMavenProBold(cartActivity)

        tv_item?.typeface=getMavenProRegular(cartActivity)
        tv_itemval?.typeface=getMavenProRegular(cartActivity)


        tv_postagepack?.typeface=getMavenProRegular(cartActivity)
        tv_postagepackval?.typeface=getMavenProRegular(cartActivity)
        tv_shippingcostval?.typeface=getMavenProRegular(cartActivity)
        tv_tax?.typeface=getMavenProRegular(cartActivity)
        tv_gstval?.typeface=getMavenProRegular(cartActivity)
        tv_shippingcost?.typeface=getMavenProRegular(cartActivity)



        tv_apply_coupon?.typeface=getMavenProRegular(cartActivity)
        tv_rangehader?.typeface=getmavenproBlack(cartActivity)
        tv_minamount?.typeface=getMavenProRegular(cartActivity)
        tv_coupondiscunt?.typeface=getMavenProRegular(cartActivity)
        tv_advamount?.typeface=getMavenProBold(cartActivity)
        tv_advanceval?.typeface=getMavenProBold(cartActivity)
        btn_apply_range?.typeface=getmavenproBlack(cartActivity)

        btn_apply_cupon?.typeface=getMavenProBold(cartActivity)
        et_coupon?.typeface=getMavenProRegular(cartActivity)

        tv_gauestemail?.typeface=getMavenProBold(cartActivity)
        et_gauestemail?.typeface=getMavenProRegular(cartActivity)

    }

    private fun setviewbinds() {
        rec_cart_list=view?.findViewById(R.id.rec_cart_list)
        btn_process_ocheckout=view?.findViewById(R.id.btn_process_ocheckout)
        img_back=view?.findViewById(R.id.img_back)
        tv_headertext=view?.findViewById(R.id.tv_headertext)
        tv_product_details=view?.findViewById(R.id.tv_product_details)
        tv_mycoupons=view?.findViewById(R.id.tv_mycoupons)
        tv_price_cart=view?.findViewById(R.id.tv_price_cart)
        btn_updatecart=view?.findViewById(R.id.btn_updatecart);
        tv_summery=view?.findViewById(R.id.tv_summery)

        tv_subtotal=view?.findViewById(R.id.tv_subtotal)
        tv_subtotalval=view?.findViewById(R.id.tv_subtotalval)
        tv_total_price=view?.findViewById(R.id.tv_total_price);
        tv_total_price_text=view?.findViewById(R.id.tv_total_price_text)
        et_promocode=view?.findViewById(R.id.et_promocode)
        rangeSeekbar=view?.findViewById<RangeSeekBarView>(R.id.range_seekbar)
        btn_apply=view?.findViewById(R.id.btn_apply)


        tv_item=view?.findViewById(R.id.tv_item)
        tv_itemval=view?.findViewById(R.id.tv_itemval)
        tv_postagepack=view?.findViewById(R.id.tv_postagepack);
        tv_postagepackval=view?.findViewById(R.id.tv_postagepackval)
        tv_shippingcost=view?.findViewById(R.id.tv_shippingcost)
        tv_shippingcostval=view?.findViewById(R.id.tv_shippingcostval)
        tv_tax=view?.findViewById(R.id.tv_tax)
        tv_gstval=view?.findViewById(R.id.tv_gstval)



        img_coupon=view?.findViewById(R.id.img_coupon)
        tv_apply_coupon=view?.findViewById(R.id.tv_apply_coupon)
        rl_applycoupon=view?.findViewById(R.id.rl_applycoupon);
        tv_rangehader=view?.findViewById(R.id.tv_rangehader)
        btn_apply_range=view?.findViewById(R.id.btn_apply_range)
        tv_minamount=view?.findViewById(R.id.tv_minamount)
        tv_coupondiscunt=view?.findViewById(R.id.tv_coupondiscunt)
        tv_applycoupon=view?.findViewById(R.id.tv_applycoupon)
        tv_advamount=view?.findViewById(R.id.tv_advamount)
        tv_advanceval=view?.findViewById(R.id.tv_advanceval)

        et_coupon=view?.findViewById(R.id.et_coupon)
        rl_coupon=view?.findViewById(R.id.rl_coupon)
        btn_apply_cupon=view?.findViewById(R.id.btn_apply_cupon)

        tv_gauestemail=view?.findViewById(R.id.tv_gauestemail)
        et_gauestemail=view?.findViewById(R.id.et_gauestemail)

    }
}