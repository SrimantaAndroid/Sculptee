package com.sculptee.screens.myorders

import android.app.Activity
import android.view.View
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution

class MyorderViewBinds: DeviceResolution {
    var activity: MyOrdersActivity?=null
    var view :View?=null
    constructor(myOrdersActivity: MyOrdersActivity, view: View) : super(myOrdersActivity){
        this.activity=activity
        this.view=view
        viewbinds()
    }

    private fun viewbinds() {

    }

}