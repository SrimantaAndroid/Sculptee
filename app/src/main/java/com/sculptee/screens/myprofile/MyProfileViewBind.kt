package com.sculptee.screens.myprofile

import android.view.View
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution

class MyProfileViewBind:DeviceResolution{
    var myprofileActivity: MyProfile_Activity?=null
    var view: View?=null
    constructor(myprofileActivity: MyProfile_Activity, view: View) : super(myprofileActivity){
        this.myprofileActivity=myprofileActivity
        this.view=view
    }

}
