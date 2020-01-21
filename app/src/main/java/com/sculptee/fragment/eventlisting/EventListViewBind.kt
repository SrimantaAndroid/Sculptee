package com.sculptee.fragment.eventlisting

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.R
import com.sculptee.screens.home.HomeActivity

class EventListViewBind:DeviceResolution {
    var homeActivity: HomeActivity?=null
    var view: View?=null
    var rec_category_itemlist:RecyclerView?=null
    var tv_noitem:TextView?=null
    constructor(homeActivity: HomeActivity?, view: View?) : super(homeActivity){
        this.homeActivity=homeActivity
        this.view=view
        vienbinds();
    }

    private fun vienbinds() {
        rec_category_itemlist=view?.findViewById(R.id.rec_category_itemlist)
        tv_noitem=view?.findViewById(R.id.tv_noitem);
        tv_noitem?.setTypeface(getMavenProBold(homeActivity))
    }
}