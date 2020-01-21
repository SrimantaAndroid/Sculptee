package com.sculptee.screens.profile

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.R

class ProfileViewBind:DeviceResolution{
    var profileActivity: ProfileActivity?=null
    var view: View?=null
    var back:ImageView?=null
    var tv_profilenmae:TextView?=null
    var img_profile:ImageView?=null
    var rl_profileorder:RelativeLayout?=null
     var tv_order_desc:TextView?=null
    var tv_pro_order:TextView?=null
    var rl_helpcenter:RelativeLayout?=null
    var tv_help:TextView?=null
    var tv_help_desc:TextView?=null
    var rl_profile_address:RelativeLayout?=null
    var tv_address:TextView?=null
    var tv_address_desc:TextView?=null
    var rl_mycoupon:RelativeLayout?=null
    var tv_coupon:TextView?=null
    var tv_coupon_desc:TextView?=null
    var rl_profile_lovelist:RelativeLayout?=null
    var tv_lovelist:TextView?=null
    var tv_my_lovelist_desc:TextView?=null
    var rl_profile:RelativeLayout?=null
    var tv_manageprofile:TextView?=null
    var tv_manageprofile_desc:TextView?=null
    var btnlogout:Button?=null

    constructor(profileActivity: ProfileActivity, view: View):super(profileActivity){

        this.profileActivity=profileActivity
        this.view=view
        viewbinds()
        settypeface()
    }

    private fun settypeface() {
        tv_profilenmae?.setTypeface(getMavenProBold(profileActivity))
        btnlogout?.setTypeface(getMavenProBold(profileActivity))
        tv_pro_order?.setTypeface(getMavenProRegular(profileActivity))
        tv_order_desc?.setTypeface(getMavenProRegular(profileActivity))
        tv_help?.setTypeface(getMavenProRegular(profileActivity))
        tv_help_desc?.setTypeface(getMavenProRegular(profileActivity))
        tv_address?.setTypeface(getMavenProRegular(profileActivity))
        tv_address_desc?.setTypeface(getMavenProRegular(profileActivity))
        tv_coupon?.setTypeface(getMavenProRegular(profileActivity))
        tv_coupon_desc?.setTypeface(getMavenProRegular(profileActivity))
        tv_lovelist?.setTypeface(getMavenProRegular(profileActivity))
        tv_my_lovelist_desc?.setTypeface(getMavenProRegular(profileActivity))
        tv_manageprofile?.setTypeface(getMavenProRegular(profileActivity))
        tv_manageprofile_desc?.setTypeface(getMavenProRegular(profileActivity))
      //  tv_logout?.setTypeface(getMavenProRegular(profileActivity))
    }

    private fun viewbinds() {
        back=view!!.findViewById(R.id.img_back_profile)
        tv_profilenmae=view!!.findViewById(R.id.tv_profilenmae)
        img_profile=view!!.findViewById(R.id.img_profile)
        rl_profileorder=view!!.findViewById(R.id.rl_profileorder)
        tv_order_desc=view!!.findViewById(R.id.tv_order_desc)
        tv_pro_order=view!!.findViewById(R.id.tv_pro_order)
        rl_helpcenter=view!!.findViewById(R.id.rl_helpcenter)
        tv_help=view!!.findViewById(R.id.tv_help)
        tv_help_desc=view!!.findViewById(R.id.tv_help_desc)
        rl_profile_address=view!!.findViewById(R.id.rl_profile_address)
        tv_address=view!!.findViewById(R.id.tv_address)
        tv_address_desc=view!!.findViewById(R.id.tv_address_desc)

        rl_mycoupon=view!!.findViewById(R.id.rl_mycoupon)
        tv_coupon=view!!.findViewById(R.id.tv_coupon)
        tv_coupon_desc=view!!.findViewById(R.id.tv_coupon_desc)
        rl_profile_lovelist=view!!.findViewById(R.id.rl_profile_lovelist)
        tv_lovelist=view!!.findViewById(R.id.tv_lovelist)
        tv_my_lovelist_desc=view!!.findViewById(R.id.tv_my_lovelist_desc)
        rl_profile=view!!.findViewById(R.id.rl_profile)
        tv_manageprofile=view!!.findViewById(R.id.tv_manageprofile)
        tv_manageprofile_desc=view!!.findViewById(R.id.tv_manageprofile_desc)
        btnlogout=view!!.findViewById(R.id.btnlogout)
    }
}