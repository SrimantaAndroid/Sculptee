package com.sculptee.screens.cart

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import com.sculptee.R
import com.sculptee.screens.placeorder.PlaceOrderActivity
import com.sculptee.utils.Alert
import com.sculptee.utils.preferenceconstant.PreferenceConstantParam
import com.sculptee.utils.sheardpreference.AppPreferenceHalper

import android.graphics.drawable.Drawable
import com.mohammedalaa.seekbar.RangeSeekBarView


class CartOnClick: View.OnClickListener {
    var cartListViewBind: CartListViewBind?=null
    var cartActivity: CartActivity?=null

    constructor(cartListViewBind: CartListViewBind, cartActivity: CartActivity){
        this.cartActivity=cartActivity
        this.cartListViewBind=cartListViewBind
        setonclick();
    }


    private fun setonclick() {
        cartListViewBind?.btn_process_ocheckout?.setOnClickListener(this)
        cartListViewBind?.img_back?.setOnClickListener(this)
        cartListViewBind?.btn_updatecart?.setOnClickListener(this)
        cartListViewBind?.btn_apply_range?.setOnClickListener(this)
        cartListViewBind?.rl_applycoupon?.setOnClickListener(this)
        cartListViewBind?.btn_apply_cupon?.setOnClickListener(this)
       /* if(AppPreferenceHalper.read(PreferenceConstantParam.isLogIn,false)==true) {
            cartListViewBind?.btn_apply?.setOnClickListener(this)
           // cartListViewBind?.btn_apply?.background!!.alpha= 45
        }else
        {
            cartListViewBind?.et_promocode!!.isEnabled=false
            val background =   cartListViewBind?.btn_apply?.background
            background!!.setAlpha(80)
        }*/

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_apply_range->{
              cartActivity?.showtotalprice()
            }
            R.id.btn_process_ocheckout->{
                if (cartActivity!!.cartlist.size>0) {
                    AppPreferenceHalper.write(
                        PreferenceConstantParam.PAYVAL,
                        cartListViewBind?.tv_advanceval?.text.toString()
                    )
                    val intent = Intent(cartActivity, PlaceOrderActivity::class.java)
                    cartActivity?.startActivity(intent)
                }else
                    Alert.showalert(cartActivity!!,"You don't have any cart item.")
            }
            R.id.img_back->{
                cartActivity?.finish()
            }

            R.id.btn_apply_cupon->{
                if(!cartListViewBind!!.et_coupon!!.text.toString().equals("")){
                    if (cartListViewBind!!.et_gauestemail!!.visibility==View.VISIBLE) {
                        if (!cartListViewBind!!.et_gauestemail!!.text.toString().equals("")) {
                            cartActivity?.callapiforapplycoupon()
                        } else
                            Alert.showalert(cartActivity!!, "Please Enter valid-email address.")
                    }else
                        cartActivity?.callapiforapplycoupon()
                }else
                    Alert.showalert(cartActivity!!,"Please Enter Coupon-Code")
            }
            R.id.btn_updatecart->{
               cartActivity?.updateprice()
            }
            R.id.rl_applycoupon->{
                if (cartListViewBind!!.rl_coupon!!.visibility==View.GONE){
                    cartListViewBind!!.rl_coupon!!.visibility=View.VISIBLE
                    if(AppPreferenceHalper.read(PreferenceConstantParam.isLogIn,false)==true) {
                        cartListViewBind!!.et_gauestemail!!.visibility=View.GONE
                        cartListViewBind!!.tv_gauestemail!!.visibility=View.GONE
                    }else{
                        cartListViewBind!!.et_gauestemail!!.visibility=View.VISIBLE
                        cartListViewBind!!.tv_gauestemail!!.visibility=View.VISIBLE
                    }

                }else{
                    cartListViewBind!!.rl_coupon!!.visibility=View.GONE
                }
            }
            R.id.btn_apply->{
                if(AppPreferenceHalper.read(PreferenceConstantParam.isLogIn,false)==true) {
                    if (!cartListViewBind!!.et_promocode!!.text.toString().equals(""))
                          cartActivity?.callapiforapplycoupon()
                          else
                          Alert.showalert(cartActivity!!,"Please Enter Coupon-Code")
                }
            }
        }
    }
}