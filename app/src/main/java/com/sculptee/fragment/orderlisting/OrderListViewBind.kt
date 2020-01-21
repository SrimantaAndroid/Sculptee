package com.sculptee.fragment.orderlisting

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.R
import com.sculptee.screens.home.HomeActivity

class OrderListViewBind:DeviceResolution{
    var rec_orderlist:RecyclerView?=null
    var rec_orderlist_previous:RecyclerView?=null
    var tv_currentorder:TextView?=null
    var tv_previousorder:TextView?=null
    var homeActivity: HomeActivity?=null
    constructor(homeActivity: HomeActivity, view: View?) : super(homeActivity){
        this.homeActivity=homeActivity
        viewbinds(view)

    }

    private fun viewbinds(view: View?) {
        rec_orderlist=view?.findViewById(R.id.rec_orderlist)
        rec_orderlist_previous=view?.findViewById(R.id.rec_orderlist_previous)
        tv_previousorder=view?.findViewById(R.id.tv_previousorder)
        tv_currentorder=view?.findViewById(R.id.tv_currentorder)
        rec_orderlist?.setNestedScrollingEnabled(false);
        rec_orderlist_previous?.setNestedScrollingEnabled(false);
        tv_currentorder?.typeface=getMavenProBold(homeActivity)
        tv_previousorder?.typeface=getMavenProBold(homeActivity)
    }
}