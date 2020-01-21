package com.sculptee.screens.coupons

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.sculptee.R

class MYCouponActivity : AppCompatActivity() {
    var myCouponViewBind:MyCouponViewBind?=null
   var myCouponOnClick:MyCouponOnClick?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view=LayoutInflater.from(this).inflate(R.layout.activity_mycoupon,null)
        setContentView(view)
        myCouponViewBind= MyCouponViewBind(this,view)
        myCouponOnClick=MyCouponOnClick(this,myCouponViewBind!!)
    }
}
