package com.sculptee.screens.login

import android.view.View
import android.widget.Toast
import com.rts.commonutils_2_0.netconnection.ConnectionDetector
import com.sculptee.R
import com.sculptee.utils.Alert

class LogInActivityOnClick : View.OnClickListener{
    var logInActivity: LogInActivity?=null
    var logInActivityViewBind: LoGInActivityViewBind?=null
    constructor(logInActivity: LogInActivity, logInActivityViewBind: LoGInActivityViewBind){
        this.logInActivity=logInActivity
        this.logInActivityViewBind=logInActivityViewBind
        setonclick()
    }

    private fun setonclick() {
        logInActivityViewBind?.btn_login?.setOnClickListener(this)
        logInActivityViewBind?.rl_backlogin?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
        R.id.Btn_login->{
            checkblankfeild();
        }
            R.id.rl_backlogin->{
                logInActivity!!.finish()
            }
       }
    }

    private fun checkblankfeild() {
        if(!logInActivityViewBind?.et_mobileno?.text.toString().equals("")){
            if(!logInActivityViewBind?.et_mobileno?.text.toString().equals("")){
                if (ConnectionDetector.isConnectingToInternet(logInActivity))
                logInActivity?.callApiforlogin()
                else
                    Alert.showalert(logInActivity!!,logInActivity!!.resources.getString(R.string.nointernet))
            }
            else
                Toast.makeText(logInActivity,"Please enter password",Toast.LENGTH_SHORT).show()
        }
        else
           Toast.makeText(logInActivity,"Please enter email",Toast.LENGTH_SHORT).show()
    }


}