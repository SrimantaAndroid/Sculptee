package com.sculptee.screens.mylovelist

import android.view.View
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution

class MyLoveListViewBind:DeviceResolution {
    var myLoveList: MyLoveList?=null
    var view: View?=null
    constructor(myLoveList: MyLoveList, view: View) : super(myLoveList){
      this.myLoveList=myLoveList
        this.view=view
    }
}