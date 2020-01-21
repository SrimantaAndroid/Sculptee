package com.sculptee.fragment.about


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution

import com.sculptee.R
import com.sculptee.screens.home.HomeActivity

import android.text.Html
import android.text.Spanned
import android.text.method.ScrollingMovementMethod


class AboutUsFragment : Fragment() {
    var tv_aboutustext:TextView?=null
    var homeActivity:HomeActivity?=null
    var deviceResolution:DeviceResolution?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeActivity= activity as HomeActivity?
        homeActivity!!.homeActivityViewBind!!.tv_headertext?.setText(homeActivity?.resources?.getString(R.string.aboutus))
        homeActivity!!.homeActivityOnClick!!.openaboutus()
        deviceResolution=DeviceResolution((homeActivity!!))
        val view:View=LayoutInflater.from(homeActivity).inflate(R.layout.fragment_about_us,container,false)
        tv_aboutustext=view.findViewById(R.id.tv_aboutustext)
        tv_aboutustext!!.setTypeface(deviceResolution!!.getMavenProRegular(homeActivity))
      //  val sp = Html.fromHtml(getString(R.string.aboutus_text))
       // tv_aboutustext!!.setText(getString(R.string.aboutus_text))
        tv_aboutustext!!.movementMethod= ScrollingMovementMethod()
        return view
    }


}
