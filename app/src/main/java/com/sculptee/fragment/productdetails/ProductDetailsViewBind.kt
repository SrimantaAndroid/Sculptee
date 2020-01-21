package com.sculptee.fragment.productdetails

import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.R
import com.sculptee.screens.home.HomeActivity
import com.viewpagerindicator.CirclePageIndicator

class ProductDetailsViewBind:DeviceResolution {
    var homeActivity: HomeActivity?=null
    var  view: View?=null
    var img_details:ImageView?=null
    var tv_avalibility:TextView?=null
    var  tv_avaragerating:TextView?=null
    var tv_ratingcount:TextView?=null
    var tv_reviewcount:TextView?=null
    var tv_price:TextView?=null
    var tv_product_name:TextView?=null
    var  tv_price_actual:TextView?=null
    var tv_off:TextView?=null
    var tv_offprice:TextView?=null
    var cake_img1:ImageView?=null
    var cake_img2:ImageView?=null
    var cake_img3:ImageView?=null
    var cake_img4:ImageView?=null
    var tv_description:TextView?=null
    var tv_ingradient:TextView?=null
    var ll_description:LinearLayout?=null
    var ll_ingradients:LinearLayout?=null
    var tv_desc_text:TextView?=null
    var tv_ingradient_text:TextView?=null
    var tv_add_to_cart:TextView?=null
    var img_circle_green:ImageView?=null
    var img_circle_red:ImageView?=null
    var chk_eggbased:RadioButton?=null
    var with_sugger:RadioButton?=null
    var tv_flovour:TextView?=null
    var tv_weight:TextView?=null
    var tv_eggbased:TextView?=null
    var tv_with_suger:TextView?=null
    var et_message:TextView?=null
    var tv_weighttext:TextView?=null
    var tv_flavourtext:TextView?=null
    var tv_weightval:TextView?=null
    var rl_addtocart:RelativeLayout?=null
    var rl_heart:RelativeLayout?=null
    var img_share:ImageView?=null
    var rl_radio_eggbased:RelativeLayout?=null
    var rl_radio_eggless:RelativeLayout?=null
    var rl_imagedetails:RelativeLayout?=null
    var vp_imageslider:ViewPager?=null
    var indicator:CirclePageIndicator?=null
    var img360:ImageView?=null
    var ratingbar:RatingBar?=null
    var btn_submitreview:Button?=null
    var et_review:EditText?=null
    var tv_ratereview:TextView?=null
    var ll_review:RelativeLayout?=null
    var rv_commantlist:RecyclerView?=null

    constructor( homeActivity: HomeActivity?, view: View?):  super(homeActivity){
        this.homeActivity=homeActivity;
        this.view=view
        viewbinds()
        settypeface();
    }

    private fun settypeface() {
        tv_avalibility?.setTypeface(getMavenProBold(homeActivity))
        tv_avaragerating?.setTypeface(getMavenProBold(homeActivity))
        tv_ratingcount?.setTypeface(getMavenProRegular(homeActivity))
        tv_reviewcount?.setTypeface(getMavenProRegular(homeActivity))
        tv_price?.setTypeface(getMavenProBold(homeActivity))
        tv_price_actual?.setTypeface(getMavenProRegular(homeActivity))
        tv_off?.setTypeface(getMavenProRegular(homeActivity))
        tv_product_name?.setTypeface(getMavenProBold(homeActivity))
        tv_offprice?.setTypeface(getMavenProBold(homeActivity))
        tv_desc_text?.setTypeface(getMavenProRegular(homeActivity))
        tv_ingradient_text?.setTypeface(getMavenProRegular(homeActivity))
        tv_add_to_cart?.setTypeface(getMavenProBold(homeActivity))

        tv_eggbased?.setTypeface(getMavenProRegular(homeActivity))
        et_message?.setTypeface(getMavenProRegular(homeActivity))
        tv_with_suger?.setTypeface(getMavenProRegular(homeActivity))
        tv_weighttext?.setTypeface(getMavenProRegular(homeActivity))
        tv_flavourtext?.setTypeface(getMavenProRegular(homeActivity))
        tv_flovour?.setTypeface(getMavenProRegular(homeActivity))
        tv_weightval?.setTypeface(getMavenProRegular(homeActivity))

        tv_ratereview?.setTypeface(getMavenProBold(homeActivity))
        et_review?.setTypeface(getMavenProRegular(homeActivity))
        btn_submitreview?.setTypeface(getMavenProBold(homeActivity))
    }

    private fun viewbinds() {
       img_details=view?.findViewById(R.id.img_details)
        tv_avalibility=view?.findViewById(R.id.tv_avalibility)
        tv_avaragerating=view?.findViewById(R.id.tv_avaragerating)
        tv_ratingcount=view?.findViewById(R.id.tv_ratingcount)
        tv_reviewcount=view?.findViewById(R.id.tv_reviewcount)
        tv_price=view?.findViewById(R.id.tv_price)
        tv_product_name=view?.findViewById(R.id.tv_product_name)
        tv_price_actual=view?.findViewById(R.id.tv_price_actual)
        tv_off=view?.findViewById(R.id.tv_off)
        tv_offprice=view?.findViewById(R.id.tv_offprice)
        cake_img1=view?.findViewById(R.id.cake_img1)
        cake_img2=view?.findViewById(R.id.cake_img2)
        cake_img3=view?.findViewById(R.id.cake_img3)
        cake_img4=view?.findViewById(R.id.cake_img4)

        tv_description=view?.findViewById(R.id.tv_description)
        tv_ingradient=view?.findViewById(R.id.tv_ingradient)
        ll_description=view?.findViewById(R.id.ll_description)
        ll_ingradients=view?.findViewById(R.id.ll_ingradients)
        tv_desc_text=view?.findViewById(R.id.tv_desc_text)
        tv_ingradient_text=view?.findViewById(R.id.tv_ingradient_text)
        tv_add_to_cart=view?.findViewById(R.id.tv_add_to_cart)
        img_circle_green=view?.findViewById(R.id.img_circle_green)
        img_circle_red=view?.findViewById(R.id.img_circle_red)

        chk_eggbased=view?.findViewById(R.id.chk_eggbased)
        with_sugger=view?.findViewById(R.id.chkwith_sugger)
        tv_flovour=view?.findViewById(R.id.tv_flovour)
        tv_weight=view?.findViewById(R.id.tv_weight)
        tv_eggbased=view?.findViewById(R.id.tv_eggbased)
        et_message=view?.findViewById(R.id.et_message)
        tv_with_suger=view?.findViewById(R.id.tv_with_suger)
        tv_weighttext=view?.findViewById(R.id.tv_weighttext)
        tv_flavourtext=view?.findViewById(R.id.tv_flavourtext)
        tv_weightval=view?.findViewById(R.id.tv_weightval)
        rl_addtocart=view?.findViewById(R.id.rl_addtocart);
        rl_heart=view?.findViewById(R.id.rl_heart)
        img_share=view?.findViewById(R.id.img_share)

        rl_radio_eggbased=view?.findViewById(R.id.rl_radio_eggbased)
        rl_radio_eggless=view?.findViewById(R.id.rl_radio_eggless)
        rl_imagedetails=view?.findViewById(R.id.rl_imagedetails)
        vp_imageslider=view?.findViewById(R.id.vp_imageslider)
        indicator=view?.findViewById(R.id.indicator)
        img360=view?.findViewById(R.id.img360)
        ratingbar=view?.findViewById(R.id.ratingbar)

        btn_submitreview=view?.findViewById(R.id.btn_submitreview)
        et_review=view?.findViewById(R.id.et_review)
        tv_ratereview=view?.findViewById(R.id.tv_ratereview)
        ll_review=view!!.findViewById(R.id.ll_review)
        rv_commantlist=view!!.findViewById(R.id.rv_commantlist)

    }
}