package com.sculptee.fragment.event

import android.view.View
import com.sculptee.R
import com.sculptee.screens.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_event.view.*

class EventFragmentOnCLick: View.OnClickListener {
    var homeActivity: HomeActivity?=null
    var eventFragmentViewBind: EventFragmentViewBind?=null
    constructor(homeActivity: HomeActivity?, eventFragmentViewBind: EventFragmentViewBind?){
        this.eventFragmentViewBind=eventFragmentViewBind
        this.homeActivity=homeActivity
        setonclicklistner()
    }

    private fun setonclicklistner() {
     //   eventFragmentViewBind?.btn_wedding?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
         /*   R.id.btn_wedding->{
                homeActivity?.loadweddingfragment()
            }*/
        }

    }
}