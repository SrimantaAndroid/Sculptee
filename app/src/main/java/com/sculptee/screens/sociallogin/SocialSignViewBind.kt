package com.sculptee.screens.sociallogin

import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import com.facebook.login.widget.LoginButton
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.R

class SocialSignViewBind :DeviceResolution {
    var socialSignActivity: SocialSignActivity?=null
    var view: View?=null
    var tv_signup:TextView?=null
    var tv_login:TextView?=null
    var btn_google:Button?=null
    var btn_facebook:Button?=null
    var fb_login_button:LoginButton?=null
    var rl_backsocialsign:RelativeLayout?=null
    constructor(socialSignActivity: SocialSignActivity, view: View?) : super(socialSignActivity){
        this.socialSignActivity=socialSignActivity
        this.view=view
        viewbinds()
       settypeface();
    }

    private fun settypeface() {
        tv_login?.setTypeface(getMavenProBold(socialSignActivity))
        tv_login?.setTypeface(getMavenProBold(socialSignActivity))
        btn_google?.setTypeface(getMavenProBold(socialSignActivity))
        btn_facebook?.setTypeface(getMavenProBold(socialSignActivity))



    }

    private fun viewbinds() {
        tv_signup=view?.findViewById(R.id.tv_signup)
        tv_login=view?.findViewById(R.id.tv_login)
        btn_google=view?.findViewById(R.id.btn_google)
        btn_facebook=view?.findViewById(R.id.btn_facebook)
        fb_login_button=view?.findViewById(R.id.fb_login_button)
        rl_backsocialsign=view?.findViewById(R.id.rl_backsocialsign)
    }

}