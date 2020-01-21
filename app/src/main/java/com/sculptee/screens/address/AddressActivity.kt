package com.sculptee.screens.address

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.sculptee.R

class AddressActivity : AppCompatActivity() {
    var addressViewBind:AddressViewBind?=null
    var addressOnClick:AddressOnclick?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view:View=LayoutInflater.from(this).inflate(R.layout.activity_address,null)
        addressViewBind= AddressViewBind(this,view)
        addressOnClick=AddressOnclick(this,addressViewBind!!)
        setContentView(view)
    }
}
