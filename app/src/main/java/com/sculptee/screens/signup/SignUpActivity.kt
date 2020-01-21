package com.sculptee.screens.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sculptee.R

class SignUpActivity:AppCompatActivity() {
    var view:View?=null
    var signUpViewBind:SignUpViewBind?=null
    var signUpOnClick:SignUpOnClick?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view=LayoutInflater.from(this).inflate(R.layout.activity_signup,null)
        signUpViewBind= SignUpViewBind(this,view)
        signUpOnClick= SignUpOnClick(this, signUpViewBind!!)
        setContentView(view)
    }
}