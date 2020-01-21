package com.sculptee.screens.sociallogin

import android.content.Intent
import android.view.View
import android.widget.Button
import com.facebook.login.LoginManager
import com.rts.commonutils_2_0.netconnection.ConnectionDetector
import com.sculptee.R
import com.sculptee.screens.login.LogInActivity
import com.sculptee.screens.signup.SignUpActivity
import com.sculptee.utils.Alert
import java.util.*

class SocialLoginOnClick: View.OnClickListener {
    var socialSignActivity: SocialSignActivity?=null
    var socialSignViewBind: SocialSignViewBind?=null

    constructor(socialSignActivity: SocialSignActivity, socialSignViewBind: SocialSignViewBind){
        this.socialSignActivity=socialSignActivity;
        this.socialSignViewBind=socialSignViewBind;
        setonclicklistner()
    }

    private fun setonclicklistner() {
        socialSignViewBind?.tv_login?.setOnClickListener(this)
        socialSignViewBind?.tv_signup?.setOnClickListener(this)
        socialSignViewBind?.btn_facebook?.setOnClickListener(this)
        socialSignViewBind?.btn_google?.setOnClickListener(this)
        socialSignViewBind?.rl_backsocialsign?.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.tv_signup->{
                socialSignActivity?.startActivity(Intent(socialSignActivity,SignUpActivity::class.java))

            }
            R.id.tv_login->{
                socialSignActivity?.startActivity(Intent(socialSignActivity,LogInActivity::class.java))
            }
            R.id.btn_facebook->{
                if (ConnectionDetector.isConnectingToInternet(socialSignActivity)) {
                   // LoginManager.getInstance().logInWithReadPermissions(socialSignActivity,
                      //  Arrays.asList("public_profile", "email")
                  //  )
                    //Alert.showalert(socialSignActivity!!,"Under Development")
                   socialSignActivity?.setupfacebooklogin()
                }else
                    Alert.showalert(socialSignActivity!!,socialSignActivity!!.resources.getString(R.string.nointernet))


            }
            R.id.btn_google->{
                if (ConnectionDetector.isConnectingToInternet(socialSignActivity))
                socialSignActivity?.googlelogin()
                else
                    Alert.showalert(socialSignActivity!!,socialSignActivity!!.resources.getString(R.string.nointernet))

            }
            R.id.rl_backsocialsign->{
                socialSignActivity?.finish()
            }

        }

    }
}