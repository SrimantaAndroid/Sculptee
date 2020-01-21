package com.sculptee.fragment.myaccount

import android.view.View
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.rts.commonutils_2_0.netconnection.ConnectionDetector
import com.sculptee.R
import com.sculptee.network.ApiInterface
import com.sculptee.network.RetroFitApiService
import com.sculptee.screens.home.HomeActivity
import com.sculptee.utils.Alert
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.sculptee.utils.preferenceconstant.PreferenceConstantParam
import com.sculptee.utils.sheardpreference.AppPreferenceHalper
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyAccountOnClick: View.OnClickListener{


    var homeActivity: HomeActivity?=null
    var myAccountViewBind: MyAccountViewBind?=null
    constructor(homeActivity: HomeActivity, myAccountViewBind: MyAccountViewBind){
        this.homeActivity=homeActivity
        this.myAccountViewBind=myAccountViewBind
        setonclick()
    }

    private fun setonclick() {
        myAccountViewBind!!.btn_submit!!.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btn_submit->{
                 checkblank()
            }

        }

    }

    private fun checkblank() {
        if (myAccountViewBind!!.chkchangepassword!!.isChecked==true) {
            if (!myAccountViewBind!!.et_newpassword!!.text.toString().equals("")) {
                if (!myAccountViewBind!!.et_confirmnewpassword!!.text.toString().equals("")) {
                    if (myAccountViewBind!!.et_newpassword!!.text.toString().equals(myAccountViewBind!!.et_confirmnewpassword!!.text.toString())) {
                          callapiforupadteprofile()
                    } else
                        Alert.showalert(homeActivity!!, "Password and Confirm password doesn't match.")

                } else
                    Alert.showalert(homeActivity!!, "Please enter new Confirm password.")
            } else
                Alert.showalert(homeActivity!!, "Please enter new password.")
        }else
             Alert.showalert(homeActivity!!, "Please checked Change Password.")
    }

    private fun callapiforupadteprofile() {
        if(ConnectionDetector.isConnectingToInternet(homeActivity)) {
              val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
              customProgress.showProgress(homeActivity!!, "Please Wait..", false)
            val apiInterface = RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)
            var jobj: JSONObject? = null
            try {
                jobj = JSONObject();
                jobj.put("password",myAccountViewBind!!.et_confirmnewpassword!!.text.toString())

                var obj: JSONObject = jobj
                var jsonParser: JsonParser = JsonParser()
                var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;

            val callapi = apiInterface?.Changepassword(AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_ID,"")!!.toString(),gsonObject)
            callapi!!.enqueue(object : Callback<ResponseBody>{
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        customProgress.hideProgress()
                        if (response.isSuccessful){
                            Alert.showalert(homeActivity!!,"Password updated Successfully")
                        }else
                            Alert.showalert(homeActivity!!,"Internal server error.")
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        customProgress.hideProgress()

                    }

                })
            }catch (e:Exception){
                e.printStackTrace()
                customProgress.hideProgress()
            }


        }else
            Alert.showalert(homeActivity!!,homeActivity!!.resources.getString(R.string.nointernet))
    }
}