package com.sculptee.screens.address

import android.view.View
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution

class AddressViewBind :DeviceResolution{
    var  addressActivity: AddressActivity?=null
    var  view: View?=null
    constructor(addressActivity: AddressActivity, view: View) : super(addressActivity){
        this.addressActivity=addressActivity
        this.view=view
    }
}