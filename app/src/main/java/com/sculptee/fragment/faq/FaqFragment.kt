package com.sculptee.fragment.faq


import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution

import com.sculptee.R
import com.sculptee.screens.home.HomeActivity


class FaqFragment : Fragment() {
    var tv_aboutustext: TextView?=null
    var homeActivity: HomeActivity?=null
    var deviceResolution: DeviceResolution?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        homeActivity= activity as HomeActivity?
        homeActivity!!.homeActivityViewBind!!.tv_headertext?.setText(homeActivity?.resources?.getString(R.string.faq))
        homeActivity!!.homeActivityOnClick!!.openfaq()
        deviceResolution=DeviceResolution((homeActivity!!))
        val view:View=LayoutInflater.from(homeActivity).inflate(R.layout.fragment_faq,container,false)
        tv_aboutustext=view.findViewById(R.id.faqtext)
        tv_aboutustext!!.setTypeface(deviceResolution!!.getMavenProRegular(homeActivity))
        //  val sp = Html.fromHtml(getString(R.string.aboutus_text))
        // tv_aboutustext!!.setText(getString(R.string.aboutus_text))
        tv_aboutustext!!.movementMethod= ScrollingMovementMethod()
        return view
    }


}
