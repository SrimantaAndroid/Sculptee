package com.sculptee.screens.home

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.R

class HomeActivityViewBind : DeviceResolution {
    var homeActivity: HomeActivity?=null
    var view: View?=null
    var imgmenu:ImageView?=null
    var drawer_layout:DrawerLayout?=null
     var tv_headertext:TextView?=null
    var img_notification:ImageView?=null
    var img_cart:ImageView?=null
    var ll_oeders:LinearLayout?=null
    var ll_logout: RelativeLayout?=null
    var tv_cart_count:TextView?=null
    var rl_home:RelativeLayout?=null
    var rl_shop:RelativeLayout?=null
    var tv_home_desc:TextView?=null
    var tv_home:TextView?=null
    var tv_shop:TextView?=null
    var tv_shop_desc:TextView?=null
    var rl_todaydeal:RelativeLayout?=null
    var tv_todaydeal:TextView?=null
    var tv_dealdesc:TextView?=null
    var rl_myorder:RelativeLayout?=null
    var tv_myorder:TextView?=null
    var tv_myorder_desc:TextView?=null
    var rl_lovelist:RelativeLayout?=null
    var tv_lovelist:TextView?=null
    var tv_lovelisrdesc:TextView?=null
    var rl_myaccount:RelativeLayout?=null
    var tv_myaccount: TextView?=null
    var tv_my_accountdesc:TextView?=null
    var tv_settings:TextView?=null
    var ll_settings:RelativeLayout?=null
    var tv_logout:TextView?=null
    var tv_faq:TextView?=null
    var tv_aboutus:TextView?=null
    var tv_contactus:TextView?=null
    var rl_contactus:RelativeLayout?=null
    var rl_aboutus:RelativeLayout?=null
    var rl_faq:RelativeLayout?=null
    var rl_menu:RelativeLayout?=null
    var rl_lgout:RelativeLayout?=null
    var tv_lgout:TextView?=null
    var rl_profile_address:RelativeLayout?=null
    var tv_address:TextView?=null
    var tv_address_desc:TextView?=null

    constructor(homeActivity: HomeActivity, view: View?) : super(homeActivity){
        this.homeActivity=homeActivity
        this.view=view
        viewbinds()
        settypeface()
    }

    private fun settypeface() {
        tv_headertext?.setTypeface(getKOMIKAX(homeActivity))
        tv_cart_count?.setTypeface(getMavenProBold(homeActivity))
        tv_home_desc?.setTypeface(getMavenProRegular(homeActivity))
        tv_home?.setTypeface(getMavenProRegular(homeActivity))
        tv_shop?.setTypeface(getMavenProRegular(homeActivity))
        tv_shop_desc?.setTypeface(getMavenProRegular(homeActivity))
        tv_todaydeal?.setTypeface(getMavenProRegular(homeActivity))
        tv_dealdesc?.setTypeface(getMavenProRegular(homeActivity))
        tv_myorder?.setTypeface(getMavenProRegular(homeActivity))
        tv_myorder_desc?.setTypeface(getMavenProRegular(homeActivity))
        tv_lovelist?.setTypeface(getMavenProRegular(homeActivity))
        tv_lovelisrdesc?.setTypeface(getMavenProRegular(homeActivity))
        tv_myaccount?.setTypeface(getMavenProRegular(homeActivity))
        tv_my_accountdesc?.setTypeface(getMavenProRegular(homeActivity))
        tv_logout?.setTypeface(getMavenProRegular(homeActivity))

        tv_faq?.setTypeface(getMavenProRegular(homeActivity))
        tv_aboutus?.setTypeface(getMavenProRegular(homeActivity))
        tv_contactus?.setTypeface(getMavenProRegular(homeActivity))
        tv_lgout?.setTypeface(getMavenProRegular(homeActivity))

        tv_address?.setTypeface(getMavenProRegular(homeActivity))
        tv_address_desc?.setTypeface(getMavenProRegular(homeActivity))
    }

    private fun viewbinds() {
        this.imgmenu=view?.findViewById(R.id.imgmenu)
        this.drawer_layout=view?.findViewById(R.id.drawer_layout)
        tv_headertext=view?.findViewById(R.id.tv_headertext)
        img_notification=view?.findViewById(R.id.img_notification)
        img_cart=view?.findViewById(R.id.img_cart)
     //   ll_oeders=view?.findViewById(R.id.ll_oeders)
        ll_logout=view?.findViewById(R.id.rl_logout)
        tv_cart_count=view?.findViewById(R.id.tv_cart_count)
        rl_home=view?.findViewById(R.id.rl_home)
        rl_shop=view?.findViewById(R.id.rl_shop)

        tv_home_desc=view?.findViewById(R.id.tv_home_desc)
        tv_home=view?.findViewById(R.id.tv_home)
        tv_shop=view?.findViewById(R.id.tv_shop)
        tv_shop_desc=view?.findViewById(R.id.tv_shop_desc)
        rl_todaydeal=view?.findViewById(R.id.rl_todaydeal)
        tv_todaydeal=view?.findViewById(R.id.tv_todaydeal)
        tv_dealdesc=view?.findViewById(R.id.tv_dealdesc)
        rl_myorder=view?.findViewById(R.id.rl_myorder)
        tv_myorder=view?.findViewById(R.id.tv_myorder)
        tv_myorder_desc=view?.findViewById(R.id.tv_myorder_desc)
        rl_lovelist=view?.findViewById(R.id.rl_lovelist)
        tv_lovelist=view?.findViewById(R.id.tv_lovelist)
        tv_lovelisrdesc=view?.findViewById(R.id.tv_lovelisrdesc)
        rl_myaccount=view?.findViewById(R.id.rl_myaccount)
        tv_myaccount=view?.findViewById(R.id.tv_myaccount)
        tv_my_accountdesc=view?.findViewById(R.id.tv_my_accountdesc)
        tv_settings=view?.findViewById(R.id.tv_settings)
        ll_settings=view?.findViewById(R.id.ll_settings)
        tv_logout=view?.findViewById(R.id.tv_logout)

        tv_faq=view?.findViewById(R.id.tv_faq)
        rl_faq=view?.findViewById(R.id.rl_faq)
        tv_aboutus=view?.findViewById(R.id.tv_aboutus)
        rl_aboutus=view?.findViewById(R.id.rl_aboutus)
        tv_contactus=view?.findViewById(R.id.tv_contactus)
        rl_contactus=view?.findViewById(R.id.rl_contactus)
        rl_menu=view?.findViewById(R.id.rl_menu)

        rl_lgout=view?.findViewById(R.id.rl_lgout)
        tv_lgout=view?.findViewById(R.id.tv_lgout)
        rl_profile_address=view?.findViewById(R.id.rl_profile_address1)
        tv_address=view?.findViewById(R.id.tv_address)
        tv_address_desc=view?.findViewById(R.id.tv_address_desc)
    }
}