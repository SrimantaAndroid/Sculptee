package com.sculptee.fragment.wishlist

import android.view.View
import com.sculptee.screens.home.HomeActivity

class WishListOnClick: View.OnClickListener {
    var homeActivity: HomeActivity?=null
    var view: WishListViewBind?=null
    constructor(homeActivity: HomeActivity?, WishListViewBind: WishListViewBind?){
        this.homeActivity=homeActivity;
        this.view=view
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){

        }
    }
}