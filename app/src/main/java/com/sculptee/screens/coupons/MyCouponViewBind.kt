package com.sculptee.screens.coupons

import android.view.View
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution

class MyCouponViewBind:DeviceResolution {
    var myCouponActivity: MYCouponActivity?=null
    var view: View?=null
    constructor(myCouponActivity: MYCouponActivity, view: View?): super(myCouponActivity){
        this.myCouponActivity=myCouponActivity
        this.view=view
    }
}