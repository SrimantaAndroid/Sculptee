package com.sculptee.screens.placeorder

import android.view.View
import android.widget.*
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.R
import com.sculptee.utils.preferenceconstant.PreferenceConstantParam
import com.sculptee.utils.sheardpreference.AppPreferenceHalper

class PlaceorderViewBind :DeviceResolution{
    var placeOrderActivity: PlaceOrderActivity?=null
    var view: View?=null
    var img_back:ImageView?=null
    var tv_headertext:TextView?=null
    var tv_billing_details:TextView?=null
    var tv_frist_name:TextView?=null
    var et_fristname:EditText?=null
    var tv_last_name:TextView?=null
    var et_last_name:EditText?=null
    var tv_country_name:TextView?=null
    var et_billemail_name:EditText?=null
    var tv_company_name:TextView?=null
    var et_country:EditText?=null
    var tv_street_address:TextView?=null
    var et_streetaddress1:EditText?=null
    var et_streetaddress2:EditText?=null
    var tv_town:TextView?=null
    var et_town:EditText?=null
    var tv_state:TextView?=null
    var et_state:EditText?=null
    var tv_post:TextView?=null
    var et_post:EditText?=null

    var tv_shipping_details:TextView?=null
    var tv_ship_frist_name:TextView?=null
    var et_ship_fristname:EditText?=null
    var tv_ship_last_name:TextView?=null
    var et_ship_last_name:EditText?=null
    var tv_ship_country_name:TextView?=null
    var et_ship_company_name:EditText?=null
    var tv_ship_company_name:TextView?=null
    var et_ship_country:EditText?=null
    var tv_ship_street_address:TextView?=null
    var et_ship_streetaddress1:EditText?=null
    var et_ship_streetaddress2:EditText?=null
    var tv_ship_town:TextView?=null
    var et_ship_town:EditText?=null
    var tv_ship_state:TextView?=null
    var et_ship_state:EditText?=null
    var tv_ship_post:TextView?=null
    var et_ship_post:EditText?=null

    var tv_addition_info:TextView?=null
    var tv_order_notes:TextView?=null
    var et_ordernotes:EditText?=null
    var btn_complete_order:Button?=null
    var ll_billingdetails:LinearLayout?=null
    var ll_additionlinfo:LinearLayout?=null
    var ll_shippingdetails:LinearLayout?=null
    var tv_phoneno:TextView?=null
    var tv_mobilecode:TextView?=null
    var et_phone:EditText?=null
    var tv_ship_phoneno:TextView?=null
    var tv_ship_phonecode:TextView?=null
    var et_ship_phone:EditText?=null
    var tv_next:TextView?=null
    var tv_previous:TextView?=null
    var tv_underline_office:TextView?=null
    var tv_underline_home:TextView?=null
    var tv_ship_office:TextView?=null
    var tv_ship_home:TextView?=null

    constructor(placeOrderActivity: PlaceOrderActivity, view: View):super(placeOrderActivity){
        this.placeOrderActivity=placeOrderActivity
        this.view=view;
        viewbinds();
        settypeface();
    }

    private fun settypeface() {
        tv_headertext?.typeface=getMavenProBold(placeOrderActivity)
        tv_billing_details?.typeface=getMavenProBold(placeOrderActivity)
        tv_frist_name?.typeface=getMavenProRegular(placeOrderActivity)
        et_fristname?.typeface=getMavenProRegular(placeOrderActivity)
        tv_last_name?.typeface=getMavenProRegular(placeOrderActivity)
        et_last_name?.typeface=getMavenProRegular(placeOrderActivity)
        tv_country_name?.typeface=getMavenProRegular(placeOrderActivity)
        et_billemail_name?.typeface=getMavenProRegular(placeOrderActivity)
        tv_company_name?.typeface=getMavenProRegular(placeOrderActivity)
        et_country?.typeface=getMavenProRegular(placeOrderActivity)
        tv_street_address?.typeface=getMavenProRegular(placeOrderActivity)
        et_streetaddress1?.typeface=getMavenProRegular(placeOrderActivity)
        et_streetaddress2?.typeface=getMavenProRegular(placeOrderActivity)
        tv_town?.typeface=getMavenProRegular(placeOrderActivity)
        et_town?.typeface=getMavenProRegular(placeOrderActivity)
        tv_state?.typeface=getMavenProRegular(placeOrderActivity)
        et_state?.typeface=getMavenProRegular(placeOrderActivity)
        tv_post?.typeface=getMavenProRegular(placeOrderActivity)
        et_post?.typeface=getMavenProRegular(placeOrderActivity)
        tv_addition_info?.typeface=getMavenProBold(placeOrderActivity)
        tv_order_notes?.typeface=getMavenProRegular(placeOrderActivity)
        et_ordernotes?.typeface=getMavenProRegular(placeOrderActivity)
        tv_ship_post?.typeface=getMavenProBold(placeOrderActivity)
        tv_shipping_details?.typeface=getMavenProBold(placeOrderActivity)


        tv_ship_frist_name?.typeface=getMavenProRegular(placeOrderActivity)
        et_ship_fristname?.typeface=getMavenProRegular(placeOrderActivity)
        tv_ship_last_name?.typeface=getMavenProRegular(placeOrderActivity)
        et_ship_last_name?.typeface=getMavenProRegular(placeOrderActivity)
        tv_ship_country_name?.typeface=getMavenProRegular(placeOrderActivity)
        et_ship_company_name?.typeface=getMavenProRegular(placeOrderActivity)
        tv_ship_company_name?.typeface=getMavenProRegular(placeOrderActivity)
        et_ship_country?.typeface=getMavenProRegular(placeOrderActivity)
        tv_ship_street_address?.typeface=getMavenProRegular(placeOrderActivity)
        et_ship_streetaddress1?.typeface=getMavenProRegular(placeOrderActivity)
        et_ship_streetaddress2?.typeface=getMavenProRegular(placeOrderActivity)
        tv_ship_town?.typeface=getMavenProRegular(placeOrderActivity)
        et_ship_town?.typeface=getMavenProRegular(placeOrderActivity)
        tv_ship_state?.typeface=getMavenProRegular(placeOrderActivity)
        et_ship_state?.typeface=getMavenProRegular(placeOrderActivity)
        tv_ship_post?.typeface=getMavenProRegular(placeOrderActivity)
        et_ship_post?.typeface=getMavenProRegular(placeOrderActivity)


        tv_phoneno?.typeface=getMavenProRegular(placeOrderActivity)
        tv_mobilecode?.typeface=getMavenProRegular(placeOrderActivity)
        et_phone?.typeface=getMavenProRegular(placeOrderActivity)
        tv_ship_phoneno?.typeface=getMavenProRegular(placeOrderActivity)
        tv_ship_phonecode?.typeface=getMavenProRegular(placeOrderActivity)
        et_ship_phone?.typeface=getMavenProRegular(placeOrderActivity)
        tv_next?.typeface=getMavenProBold(placeOrderActivity)
        tv_previous?.typeface=getMavenProBold(placeOrderActivity)

        tv_ship_office?.typeface=getMavenProBold(placeOrderActivity)
        tv_ship_home?.typeface=getMavenProBold(placeOrderActivity)

    }

    private fun viewbinds() {
        img_back=view?.findViewById(R.id.img_back)
        tv_headertext=view?.findViewById(R.id.tv_headertext)
        tv_billing_details=view?.findViewById(R.id.tv_billing_details)
        tv_frist_name=view?.findViewById(R.id.tv_frist_name)
        et_fristname=view?.findViewById(R.id.et_fristname)
        tv_last_name=view?.findViewById(R.id.tv_last_name)
        et_last_name=view?.findViewById(R.id.et_last_name)
        tv_country_name=view?.findViewById(R.id.tv_country_name)
        et_billemail_name=view?.findViewById(R.id.et_bill_email_name)
        tv_company_name=view?.findViewById(R.id.tv_email_name)
        et_country=view?.findViewById(R.id.et_country)
        tv_street_address=view?.findViewById(R.id.tv_street_address)
        et_streetaddress1=view?.findViewById(R.id.et_streetaddress1)
        et_streetaddress2=view?.findViewById(R.id.et_streetaddress2)
        tv_town=view?.findViewById(R.id.tv_town)
        et_town=view?.findViewById(R.id.et_town)
        tv_state=view?.findViewById(R.id.tv_state)
        et_state=view?.findViewById(R.id.et_state)
        tv_post=view?.findViewById(R.id.tv_post)
        et_post=view?.findViewById(R.id.et_post)
        tv_addition_info=view?.findViewById(R.id.tv_addition_info)
        tv_order_notes=view?.findViewById(R.id.tv_order_notes)
        et_ordernotes=view?.findViewById(R.id.et_ordernotes)
        btn_complete_order=view?.findViewById(R.id.btn_complete_order)
        ll_billingdetails=view?.findViewById(R.id.ll_billingdetails)
        ll_additionlinfo=view?.findViewById(R.id.ll_additionlinfo)
        tv_shipping_details=view?.findViewById(R.id.tv_shipping_details)
        ll_shippingdetails=view?.findViewById(R.id.ll_shippingdetails)

        tv_ship_frist_name=view?.findViewById(R.id.tv_ship_frist_name)
        et_ship_fristname=view?.findViewById(R.id.et_ship_fristname)
        tv_ship_last_name=view?.findViewById(R.id.tv_ship_last_name)
        et_ship_last_name=view?.findViewById(R.id.et_ship_last_name)
        tv_ship_country_name=view?.findViewById(R.id.tv_ship_country_name)
        et_ship_company_name=view?.findViewById(R.id.et_ship_company_name)
        tv_ship_company_name=view?.findViewById(R.id.tv_ship_company_name)
        et_ship_country=view?.findViewById(R.id.et_ship_country)
        tv_ship_street_address=view?.findViewById(R.id.tv_ship_street_address)
        et_ship_streetaddress1=view?.findViewById(R.id.et_ship_streetaddress1)
        et_ship_streetaddress2=view?.findViewById(R.id.et_ship_streetaddress2)
        tv_ship_town=view?.findViewById(R.id.tv_ship_town)
        et_ship_town=view?.findViewById(R.id.et_ship_town)
        tv_ship_state=view?.findViewById(R.id.tv_ship_state)
        et_ship_state=view?.findViewById(R.id.et_ship_state)
        tv_ship_post=view?.findViewById(R.id.tv_ship_post)
        et_ship_post=view?.findViewById(R.id.et_ship_post)

        tv_phoneno=view?.findViewById(R.id.tv_phoneno)
        tv_mobilecode=view?.findViewById(R.id.tv_mobilecode)
        et_phone=view?.findViewById(R.id.et_phone)
        tv_ship_phoneno=view?.findViewById(R.id.tv_ship_phoneno)
        tv_ship_phonecode=view?.findViewById(R.id.tv_ship_phonecode)
        et_ship_phone=view?.findViewById(R.id.et_ship_phone)
        tv_next=view?.findViewById(R.id.tv_next)
        tv_previous=view?.findViewById(R.id.tv_previous)

        tv_underline_office=view?.findViewById(R.id.tv_underline_office1)
        tv_underline_home=view?.findViewById(R.id.tv_underline_home1)
        tv_ship_office=view?.findViewById(R.id.tv_ship_office)
        tv_ship_home=view?.findViewById(R.id.tv_ship_home)

        if (AppPreferenceHalper.read(PreferenceConstantParam.isLogIn, false) == true){
            et_billemail_name!!.setText(AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_EMAIL, ""))
          //  et_ship_company_name!!.setText(AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_EMAIL, ""))
            et_billemail_name!!.isEnabled=false
          //  et_ship_company_name!!.isEnabled=false
        }
    }
}
