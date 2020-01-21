package com.sculptee.fragment.event

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.R
import com.sculptee.screens.home.HomeActivity

class EventFragmentViewBind:DeviceResolution {
    var  homeActivity: HomeActivity?=null
    var view: View?=null
    var btn_wedding:Button?=null
    var rec_event_list:RecyclerView?=null
    constructor(homeActivity: HomeActivity, view: View?) : super(homeActivity){
        this.homeActivity=homeActivity
        this.view=view
        viewbind();
    }

    private fun viewbind() {
        this.rec_event_list=view?.findViewById(R.id.rec_event_list)
       // btn_wedding=view?.findViewById(R.id.btn_wedding)
    }

}