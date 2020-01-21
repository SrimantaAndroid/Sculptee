package com.sculptee.screens.myprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.sculptee.R

class MyProfile_Activity : AppCompatActivity() {
  var myProfileViewBind:MyProfileViewBind?=null
    var myProfileOnClick:MyProfileOnClick?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view:View=LayoutInflater.from(this).inflate(R.layout.activity_my_profile_,null)
        setContentView(view)
        myProfileViewBind= MyProfileViewBind(this,view)
        myProfileOnClick=MyProfileOnClick(this,myProfileViewBind!!)
    }
}
