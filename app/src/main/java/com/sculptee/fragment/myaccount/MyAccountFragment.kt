package com.sculptee.fragment.myaccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sculptee.R
import com.sculptee.screens.home.HomeActivity
import com.sculptee.utils.preferenceconstant.PreferenceConstantParam
import com.sculptee.utils.sheardpreference.AppPreferenceHalper

class MyAccountFragment:Fragment() {
    var homeActivity:HomeActivity?=null
    var myAccountViewBind:MyAccountViewBind?=null
    var myAccountOnClick:MyAccountOnClick?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeActivity= activity as HomeActivity?
        homeActivity!!.homeActivityViewBind!!.tv_headertext?.setText(homeActivity?.resources?.getString(R.string.myacccount))
        homeActivity!!.homeActivityOnClick!!.openmyaccount()
        val view=LayoutInflater.from(homeActivity).inflate(R.layout.fragment_my_account,container,false)
        myAccountViewBind= MyAccountViewBind(homeActivity!!,view)
        myAccountOnClick= MyAccountOnClick(homeActivity!!,myAccountViewBind!!)
        setvalue()
        return view
    }

    private fun setvalue() {
        myAccountViewBind!!.tv_fullnameval!!.setText(AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_NAME,""))

        if (!AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_MOBILE,"").equals("null") && !AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_MOBILE,"").equals(""))
            myAccountViewBind!!.tv_mobilenoval!!.setText(AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_MOBILE, ""))
       // if (myAccountViewBind!!.tv_mobilenoval!!.text.toString()==null)
            //myAccountViewBind!!.tv_mobilenoval!!.setText("")
        else {
            myAccountViewBind!!.tv_mobilenoval!!.setText("")
           // if (myAccountViewBind!!.tv_mobilenoval!!.text.toString()==null)
                //myAccountViewBind!!.tv_mobilenoval!!.setText("")
        }

    }
}