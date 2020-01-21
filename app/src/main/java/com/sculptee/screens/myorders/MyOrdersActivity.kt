package com.sculptee.screens.myorders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.sculptee.R

class MyOrdersActivity : AppCompatActivity() {
  var myorderViewBinds:MyorderViewBinds?=null
    var myOrdersOnClick:MyOrdersOnClick?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = LayoutInflater.from(this).inflate(R.layout.activity_my_orders,null)
        setContentView(view)
        myorderViewBinds= MyorderViewBinds(this,view)
        myOrdersOnClick=MyOrdersOnClick(this,myorderViewBinds!!)
    }
}
