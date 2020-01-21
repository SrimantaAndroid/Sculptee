package com.sculptee.screens.mylovelist

import android.view.View

class MyLoveOnClick:View.OnClickListener {
    var myLoveList: MyLoveList?=null
    var myLoveListViewBind: MyLoveListViewBind?=null
    constructor(myLoveList: MyLoveList, myLoveListViewBind: MyLoveListViewBind){
        this.myLoveList=myLoveList;
        this.myLoveListViewBind=myLoveListViewBind
    }

    override fun onClick(p0: View?) {

    }
}