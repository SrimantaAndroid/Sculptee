package com.sculptee.screens.signup

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.R

class SignUpViewBind:DeviceResolution{
    var signUpActivity: SignUpActivity?=null
    var view: View?=null
    var tv_login:TextView?=null
    var et_name: EditText?=null
    var et_email:EditText?=null
    var et_mobileno:EditText?=null
    var et_password:EditText?=null
    var btn_signup:Button?=null
    var rl_backsignup:RelativeLayout?=null
    constructor(signUpActivity: SignUpActivity, view: View?) : super(signUpActivity){
        this.signUpActivity=signUpActivity
        this.view=view
        viewbinds()
        settypeface()
    }

    private fun settypeface() {
        tv_login?.setTypeface(getKOMIKAX(signUpActivity))
        et_name?.setTypeface(getMavenProRegular(signUpActivity))
        et_email?.setTypeface(getMavenProRegular(signUpActivity))
        et_mobileno?.setTypeface(getMavenProRegular(signUpActivity))
        et_password?.setTypeface(getMavenProRegular(signUpActivity))
        btn_signup?.setTypeface(getMavenProBold(signUpActivity))


    }

    private fun viewbinds() {
        tv_login=view?.findViewById(R.id.tv_login)
        et_name=view?.findViewById(R.id.et_name)
        et_email=view?.findViewById(R.id.et_email)
        et_mobileno=view?.findViewById(R.id.et_mobileno)
        et_password=view?.findViewById(R.id.et_password)
        btn_signup=view?.findViewById(R.id.Btn_signup)
        rl_backsignup=view?.findViewById(R.id.rl_backsignup)
    }


}
