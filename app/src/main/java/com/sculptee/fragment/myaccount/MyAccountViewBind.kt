package com.sculptee.fragment.myaccount

import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.R
import com.sculptee.screens.home.HomeActivity

class MyAccountViewBind :DeviceResolution {
    var homeActivity: HomeActivity?=null
    var view: View?=null
    var tv_fullname:TextView?=null
    var tv_fullnameval:TextView?=null
    var mobileno:TextView?=null
    var tv_mobilenoval:TextView?=null
    var chkchangepassword:CheckBox?=null
    var et_newpassword:EditText?=null
    var et_confirmnewpassword:EditText?=null
    var btn_submit:Button?=null

    constructor(homeActivity: HomeActivity, view: View?) :super(homeActivity){
        this.homeActivity=homeActivity
        this.view=view
        viewbinds(view)
        settyprface();

    }

    private fun settyprface() {

        tv_fullname!!.typeface=getMavenProMedium(homeActivity)
        tv_fullnameval!!.typeface=getMavenProBold(homeActivity)
        mobileno!!.typeface=getMavenProMedium(homeActivity)
        tv_mobilenoval!!.typeface=getMavenProBold(homeActivity)
        chkchangepassword!!.typeface=getMavenProBold(homeActivity)
        et_newpassword!!.typeface=getMavenProRegular(homeActivity)
        et_confirmnewpassword!!.typeface=getMavenProRegular(homeActivity)
        btn_submit!!.typeface=getMavenProBold(homeActivity)
    }

    private fun viewbinds(view: View?) {
        tv_fullname=view!!.findViewById(R.id.tv_fullname)
        tv_fullnameval=view!!.findViewById(R.id.tv_fullnameval)
        mobileno=view!!.findViewById(R.id.mobileno)
        tv_mobilenoval=view!!.findViewById(R.id.tv_mobilenoval)
        chkchangepassword=view.findViewById(R.id.chkchangepassword)
        et_newpassword=view!!.findViewById(R.id.et_newpassword)
        et_confirmnewpassword=view!!.findViewById(R.id.et_confirmnewpassword)
        btn_submit=view.findViewById(R.id.btn_submit)


    }
}