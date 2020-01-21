package com.sculptee.fragment.address

import android.view.View
import android.widget.*
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.R
import com.sculptee.screens.home.HomeActivity

class AddressViewBind:DeviceResolution {

    var view: View?=null
    var rl_addaddress:RelativeLayout?=null
    var tv_add_new_address:TextView?=null
    var activity: HomeActivity?=null
    var ll_add_address:LinearLayout?=null
    var tv_type:TextView?=null
    var tv_addresstype:TextView?=null
    var tv_company:TextView?=null
    var et_companyname:EditText?=null
    var tv_frist_name:TextView?=null
    var et_fristname:EditText?=null
    var tv_last_name:TextView?=null
    var et_last_name:TextView?=null
    var tv_street_address:TextView?=null
    var et_streetaddress1:EditText?=null
    var et_streetaddress2:EditText?=null
    var tv_town:TextView?=null
    var et_town:EditText?=null
    var tv_state:TextView?=null
    var et_country:EditText?=null
    var tv_post:TextView?=null
    var et_post:EditText?=null
    var chk_setdeafult:CheckBox?=null
    var btn_submitaddress:Button?=null
    var ll_reclyerview:LinearLayout?=null
    var reclyerview_address:RecyclerView?=null

    constructor(activity: HomeActivity?, view: View) :super(activity){
        this.view=view
        this.activity=activity
        viewbinds()
        settypeface()
    }

    private fun settypeface() {
        tv_add_new_address!!.setTypeface(getMavenProRegular(activity))
        tv_type!!.setTypeface(getMavenProRegular(activity))
        tv_addresstype!!.setTypeface(getMavenProRegular(activity))
        tv_company!!.setTypeface(getMavenProRegular(activity))
        et_companyname!!.setTypeface(getMavenProRegular(activity))
        tv_frist_name!!.setTypeface(getMavenProRegular(activity))
        et_fristname!!.setTypeface(getMavenProRegular(activity))
        tv_last_name!!.setTypeface(getMavenProRegular(activity))
        et_last_name!!.setTypeface(getMavenProRegular(activity))
        tv_street_address!!.setTypeface(getMavenProRegular(activity))
        et_streetaddress1!!.setTypeface(getMavenProRegular(activity))
        et_streetaddress2!!.setTypeface(getMavenProRegular(activity))
        tv_town!!.setTypeface(getMavenProRegular(activity))
        et_town!!.setTypeface(getMavenProRegular(activity))
        tv_state!!.setTypeface(getMavenProRegular(activity))
        et_country!!.setTypeface(getMavenProRegular(activity))
        tv_post!!.setTypeface(getMavenProRegular(activity))
        et_post!!.setTypeface(getMavenProRegular(activity))
        chk_setdeafult!!.setTypeface(getMavenProRegular(activity))
        btn_submitaddress!!.setTypeface(getMavenProBold(activity))
    }

    private fun viewbinds() {
        rl_addaddress=view!!.findViewById(R.id.rl_addaddress)
        tv_add_new_address=view!!.findViewById(R.id.tv_add_new_address)
        ll_add_address=view!!.findViewById(R.id.ll_add_address)
        tv_type=view!!.findViewById(R.id.tv_type)
        tv_addresstype=view!!.findViewById(R.id.tv_addresstype)
        tv_company=view!!.findViewById(R.id.tv_company)
        et_companyname=view!!.findViewById(R.id.et_companyname)
        tv_frist_name=view!!.findViewById(R.id.tv_frist_name)
        et_fristname=view!!.findViewById(R.id.et_fristname)
        et_last_name=view!!.findViewById(R.id.et_last_name)
        tv_street_address=view!!.findViewById(R.id.tv_street_address)
        et_streetaddress1=view!!.findViewById(R.id.et_streetaddress1)
        et_streetaddress2=view!!.findViewById(R.id.et_streetaddress2)
        tv_town=view!!.findViewById(R.id.tv_town)
        et_town=view!!.findViewById(R.id.et_town)
        et_country=view!!.findViewById(R.id.et_country)
        tv_state=view!!.findViewById(R.id.tv_state)
        tv_post=view!!.findViewById(R.id.tv_post)
        et_post=view!!.findViewById(R.id.et_post)
        chk_setdeafult=view!!.findViewById(R.id.chk_setdeafult)
        btn_submitaddress=view!!.findViewById(R.id.btn_submitaddress)
        ll_reclyerview=view!!.findViewById(R.id.ll_reclyerview)
        reclyerview_address=view!!.findViewById(R.id.reclyerview_address)
        tv_last_name=view!!.findViewById(R.id.tv_last_name)

    }

}