package com.sculptee.screens.placeorder

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.sculptee.R
import com.sculptee.screens.cart.CartActivity
import com.sculptee.utils.Alert
import com.sculptee.utils.preferenceconstant.PreferenceConstantParam
import com.sculptee.utils.sheardpreference.AppPreferenceHalper


class PlaceOrderOnclick : View.OnClickListener {
    var placeOrderActivity: PlaceOrderActivity? = null
    var placeorderViewBind: PlaceorderViewBind? = null

    constructor(placeOrderActivity: PlaceOrderActivity, placeorderViewBind: PlaceorderViewBind) {
        this.placeOrderActivity = placeOrderActivity
        this.placeorderViewBind = placeorderViewBind
        setonclick()
    }

    private fun setonclick() {
        placeorderViewBind?.btn_complete_order?.setOnClickListener(this)
        placeorderViewBind?.img_back?.setOnClickListener(this)
        //  placeorderViewBind?.tv_billing_details?.setOnClickListener(this)
        //  placeorderViewBind?.tv_shipping_details?.setOnClickListener(this)
        placeorderViewBind?.tv_next?.setOnClickListener(this)
        placeorderViewBind?.tv_previous?.setOnClickListener(this)

        placeorderViewBind?.tv_ship_office?.setOnClickListener(this)
        placeorderViewBind?.tv_ship_home?.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_ship_home -> {
                //  if( placeorderViewBind?.tv_underline_home?.visibility==View.VISIBLE) {
                placeorderViewBind?.tv_underline_office?.visibility = View.INVISIBLE
                placeorderViewBind?.tv_underline_home?.visibility = View.VISIBLE
                placeOrderActivity?.Addressselection=1
                placeOrderActivity?.resources?.getColor(R.color.desc_un_elected_bg)?.let {
                    placeorderViewBind?.tv_ship_home?.setBackgroundColor(it
                    )
                }
                placeOrderActivity?.resources?.getColor(R.color.card_bg_color)?.let {
                    placeorderViewBind?.tv_ship_office?.setBackgroundColor(
                        it
                    )
                }
                placeOrderActivity?.saveaddressoffice()
                placeOrderActivity?.setValueforShipAddresshome()
                //  }
            }
            R.id.tv_ship_office -> {
                //  if( placeorderViewBind?.tv_underline_office?.visibility==View.VISIBLE) {
                placeorderViewBind?.tv_underline_home?.visibility = View.INVISIBLE
                placeorderViewBind?.tv_underline_office?.visibility = View.VISIBLE
                placeOrderActivity?.Addressselection=2
                placeOrderActivity?.resources?.getColor(R.color.desc_un_elected_bg)?.let {
                    placeorderViewBind?.tv_ship_office?.setBackgroundColor(
                        it
                    )
                }

                placeOrderActivity?.resources?.getColor(R.color.card_bg_color)?.let {
                    placeorderViewBind?.tv_ship_home?.setBackgroundColor(
                        it
                    )
                }
                placeOrderActivity?.saveshippingaddresshome()
                placeOrderActivity?.setValueforShipAddressOffice()
                // }
            }
            R.id.tv_next -> {
                if (placeorderViewBind?.ll_billingdetails?.visibility == View.VISIBLE) {
                    placeorderViewBind?.ll_billingdetails?.visibility = View.GONE
                    placeorderViewBind?.ll_shippingdetails?.visibility = View.VISIBLE
                    placeorderViewBind?.et_ship_fristname?.requestFocus()
                    if (AppPreferenceHalper.read(PreferenceConstantParam.isLogIn, false) == true){

                        placeorderViewBind?.et_ship_company_name!!.setText(AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_EMAIL, ""))
                        placeorderViewBind?.et_ship_company_name!!.isEnabled=false

                    }
                } else
                    placeorderViewBind?.ll_shippingdetails?.visibility = View.VISIBLE
            }
            R.id.tv_previous -> {
                if (placeorderViewBind?.ll_shippingdetails?.visibility == View.VISIBLE) {
                    placeorderViewBind?.ll_shippingdetails?.visibility = View.GONE
                    placeorderViewBind?.ll_billingdetails?.visibility = View.VISIBLE
                    placeorderViewBind?.et_fristname?.requestFocus()
                } else
                    placeorderViewBind?.ll_billingdetails?.visibility = View.VISIBLE
            }
            R.id.btn_complete_order -> {
                // placeOrderActivity?.callApifropincodechecking();
                // placeOrderActivity?.craetecartlistorder()
                if (!placeorderViewBind?.et_fristname!!.text.toString().equals("")) {
                    if (!placeorderViewBind?.et_last_name!!.text.toString().equals("")) {
                        //if (!placeorderViewBind?.et_phone!!.text.toString().equals("")) {
                           // if (placeorderViewBind?.et_phone!!.text.toString().length>=10) {
                                if(!placeorderViewBind?.et_streetaddress1?.text.toString().equals("")){
                                    if(!placeorderViewBind?.et_post?.text.toString().equals("")){
                                        if (!placeorderViewBind?.et_ship_fristname!!.text.toString().equals("")){
                                            if (!placeorderViewBind?.et_ship_last_name!!.text.toString().equals("")){
                                                if (!placeorderViewBind?.et_ship_phone!!.text.toString().equals("")){

                                                    if(placeorderViewBind?.et_ship_phone?.text.toString().length>=10&& validCellPhone(placeorderViewBind?.et_ship_phone?.text.toString())) {
                                                        if (!placeorderViewBind?.et_ship_company_name!!.text.toString().equals("")){
                                                        if(!placeorderViewBind?.et_ship_streetaddress1!!.text.toString().equals("")){
                                                        if (!placeorderViewBind?.et_ship_post?.text.toString().equals(""))

                                                            placeOrderActivity?.callApifropincodechecking()
                                                        else
                                                            Alert.showalert(placeOrderActivity!!, "Please enter your Shipping pin code.")
                                                        }
                                                        else
                                                            Alert.showalert(placeOrderActivity!!, "Please enter Street address for Shipping.")
                                                    }else
                                                        Alert.showalert(placeOrderActivity!!, "Please enter Email-address.")
                                                    }else
                                                        Alert.showalert(placeOrderActivity!!, "Please enter Valid Phone no.")
                                                } else
                                                    Alert.showalert(placeOrderActivity!!, "Please enter your Shipping Phone no.")
                                            } else
                                                Alert.showalert(placeOrderActivity!!, "Please enter your Shipping Last name.")
                                        } else
                                            Alert.showalert(placeOrderActivity!!, "Please enter your Shipping First name.")
                                    }
                                    else
                                        Alert.showalert(placeOrderActivity!!, "Please enter Billing post code.")
                                }
                                else
                                    Alert.showalert(placeOrderActivity!!, "Please enter Street address for Billing.")
                           /* }
                            else
                                Alert.showalert(placeOrderActivity!!, "Please enter Valid Phone no.")
                        } else
                            Alert.showalert(placeOrderActivity!!, "Please enter your Billing Phone no.")*/
                    } else
                        Alert.showalert(placeOrderActivity!!, "Please enter your Billing Last name.")
                } else
                    Alert.showalert(placeOrderActivity!!, "Please enter your Billing First name.")

                // placeOrderActivity?.payment()


            }
            R.id.img_back -> {

                val intent=Intent(placeOrderActivity!!,CartActivity::class.java)
                placeOrderActivity!!.startActivity(intent)
                placeOrderActivity?.finish()

            }

            R.id.tv_billing_details -> {
                if (placeorderViewBind?.ll_billingdetails?.visibility == View.VISIBLE)
                    placeorderViewBind?.ll_billingdetails?.visibility = View.GONE
                else
                    placeorderViewBind?.ll_billingdetails?.visibility = View.VISIBLE
            }
            R.id.tv_shipping_details -> {
                if (placeorderViewBind?.ll_shippingdetails?.visibility == View.VISIBLE)
                    placeorderViewBind?.ll_shippingdetails?.visibility = View.GONE
                else
                    placeorderViewBind?.ll_shippingdetails?.visibility = View.VISIBLE
            }
        }
    }
    fun validCellPhone(number: String): Boolean {
        return android.util.Patterns.PHONE.matcher(number).matches()
    }
}