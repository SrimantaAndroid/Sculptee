package com.sculptee.screens.mylovelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sculptee.R

class MyLoveList:AppCompatActivity() {
    var myLoveListViewBind:MyLoveListViewBind?=null
    var myLoveOnClick:MyLoveOnClick?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view:View=LayoutInflater.from(this).inflate(R.layout.activity_my_lovelist,null)
        setContentView(R.layout.activity_my_lovelist)
        myLoveListViewBind= MyLoveListViewBind(this,view)
        myLoveOnClick= MyLoveOnClick(this,myLoveListViewBind!!)
    }
}