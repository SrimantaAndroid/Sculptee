package com.sculptee.fragment.wishlist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.R
import com.sculptee.screens.home.HomeActivity

class WishListViewBind:DeviceResolution {
    var homeActivity: HomeActivity?=null
    var view: View?=null
    var rec_wishlist:RecyclerView?=null
    constructor(homeActivity: HomeActivity?, view: View?) : super(homeActivity){
        this.homeActivity=homeActivity
        this.view=view
        viewbinds()

    }

    private fun viewbinds() {
        rec_wishlist=view!!.findViewById(R.id.rec_wishlist)
    }
}