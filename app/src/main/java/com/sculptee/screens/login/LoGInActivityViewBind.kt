package com.sculptee.screens.login

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.R

class LoGInActivityViewBind:DeviceResolution {
    var logInActivity: LogInActivity?=null
    var view: View?=null
    var et_mobileno:EditText?=null
    var tv_login:TextView?=null
    var et_password:TextView?=null
    var btn_login:Button?=null
    var rl_backlogin:RelativeLayout?=null
    constructor(logInActivity: LogInActivity, view: View?) : super(logInActivity){
        this.logInActivity=logInActivity;
        this.view=view
        viewbinds()
        settypeface();
    }

    private fun settypeface() {
        tv_login?.setTypeface(getKOMIKAX(logInActivity))
        et_mobileno?.setTypeface(getMavenProRegular(logInActivity))
        et_password?.setTypeface(getMavenProRegular(logInActivity))
        btn_login?.setTypeface(getMavenProBold(logInActivity))

    }

    private fun viewbinds() {
        et_mobileno=view?.findViewById(R.id.et_mobileno)
        tv_login=view?.findViewById(R.id.tv_login)
        et_password=view?.findViewById(R.id.et_password)
        btn_login=view?.findViewById(R.id.Btn_login)
        rl_backlogin=view?.findViewById(R.id.rl_backlogin)


    }
}