package com.sculptee.screens.myprofile

import android.view.View

class MyProfileOnClick:View.OnClickListener {
    var myprofileActivity: MyProfile_Activity?=null
    var myProfileViewBind: MyProfileViewBind?=null
    constructor(myprofileActivity: MyProfile_Activity, myProfileViewBind: MyProfileViewBind){
        this.myProfileViewBind=myProfileViewBind
        this.myprofileActivity=myprofileActivity
    }

    override fun onClick(p0: View?) {

    }
}