package com.sculptee.screens.profile

import android.view.View
import com.rts.commonutils_2_0.netconnection.ConnectionDetector
import com.sculptee.R
import com.sculptee.network.ApiInterface
import com.sculptee.network.RetroFitApiService
import com.sculptee.utils.Alert
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.sculptee.utils.preferenceconstant.PreferenceConstantParam
import com.sculptee.utils.sheardpreference.AppPreferenceHalper
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class ProfileOnclick:View.OnClickListener {
     var profileActivity: ProfileActivity?=null
     var profileViewBind: ProfileViewBind?=null
    constructor(profileActivity: ProfileActivity, profileViewBind: ProfileViewBind){
        this.profileActivity=profileActivity
        this.profileViewBind=profileViewBind
        setonclick()
    }

    private fun setonclick() {
        profileViewBind!!.back!!.setOnClickListener(this)
        profileViewBind!!.btnlogout!!.setOnClickListener(this)
        profileViewBind!!.rl_profileorder!!.setOnClickListener(this)
        profileViewBind!!.rl_helpcenter!!.setOnClickListener(this)
        profileViewBind!!.rl_profile!!.setOnClickListener(this)
        profileViewBind!!.rl_profile_address!!.setOnClickListener(this)
        profileViewBind!!.rl_mycoupon!!.setOnClickListener(this)
        profileViewBind!!.rl_profile_lovelist!!.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
         R.id.img_back_profile->{
             profileActivity!!.finish()
         }
            R.id.btnlogout->{
                if(AppPreferenceHalper.read(PreferenceConstantParam.isLogIn,false)==true) {
                  //  Alert.showyesnoalert(profileActivity!!, "Are you want to logout?")
                }
              //  else
                  //  Alert.showalert(profileActivity!!,"You already logged-out")
            }
            R.id.rl_profileorder->{

            }
            R.id.rl_profile_address->{

            }
            R.id.rl_profile_lovelist->{

            }
            R.id.rl_profile->{

            }
            R.id.rl_helpcenter->{

            }
            R.id.rl_mycoupon->{
                callapiforcoupon()

            }

        }

    }

    private fun callapiforcoupon() {
        if (ConnectionDetector.isConnectingToInternet(profileActivity)) {
            val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
            this!!.profileActivity?.let { customProgress.showProgress(it, "Please Wait..", false) }
            val apiInterface = RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)
            val callapi = apiInterface!!.getusercoupon(AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_ID, "")!!)
            callapi!!.enqueue(object : retrofit2.Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    customProgress.hideProgress()
                    
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    customProgress.hideProgress()
                }
            })
        }
    }
}