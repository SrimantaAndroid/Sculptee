package com.sculptee.fragment.todaydeal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sculptee.R
import com.sculptee.screens.home.HomeActivity

class TodayDealFeagment:Fragment() {
    var  homeActivity:HomeActivity?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeActivity=activity as HomeActivity?
        val view=LayoutInflater.from(homeActivity).inflate(R.layout.fragment_tday_deal,null)
        return view
    }
}