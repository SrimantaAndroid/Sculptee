package com.sculptee.screens.myorders

import android.view.View

class MyOrdersOnClick: View.OnClickListener

{
    var myOrdersActivity: MyOrdersActivity?=null
    var myorderViewBinds: MyorderViewBinds?=null
    constructor(myOrdersActivity: MyOrdersActivity, myorderViewBinds: MyorderViewBinds){
        this.myOrdersActivity=myOrdersActivity
        this.myorderViewBinds=myorderViewBinds
        setonclick()

    }

    private fun setonclick() {

    }

    override fun onClick(p0: View?) {

    }

}