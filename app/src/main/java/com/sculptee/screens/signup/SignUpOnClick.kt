package com.sculptee.screens.signup

import android.view.View
import android.widget.Toast
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.rts.commonutils_2_0.netconnection.ConnectionDetector
import com.sculptee.R
import com.sculptee.model.loginmodel.LogInApiResponse
import com.sculptee.model.signupmodel.SignupResponse
import com.sculptee.network.ApiInterface
import com.sculptee.network.RetroFitApiService
import com.sculptee.utils.Alert
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.sculptee.utils.preferenceconstant.PreferenceConstantParam
import com.sculptee.utils.sheardpreference.AppPreferenceHalper
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpOnClick : View.OnClickListener{
    var signUpActivity: SignUpActivity?=null
    var signUpViewBind: SignUpViewBind?=null
    constructor(signUpActivity: SignUpActivity, signUpViewBind: SignUpViewBind){
        this.signUpActivity=signUpActivity
        this.signUpViewBind=signUpViewBind
        setonclicklistner();
    }

    private fun setonclicklistner() {
       signUpViewBind?.btn_signup?.setOnClickListener(this)
        signUpViewBind?.rl_backsignup?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
           R.id.Btn_signup->{
               if(!signUpViewBind?.et_name?.text.toString().equals("")){
                   if(!signUpViewBind?.et_email?.text.toString().equals("")){
                       if(!signUpViewBind?.et_mobileno?.text.toString().equals("")){
                           if(!signUpViewBind?.et_password?.text.toString().equals("")){
                                   callsignup();
                                    //callsignupwithrawresponse();
                           }
                           else
                               Alert.showalert(signUpActivity!!,"Please enter your password.")
                       }
                       else
                           Alert.showalert(signUpActivity!!,"Please enter mobile no.")
                   }
                   else
                       Alert.showalert(signUpActivity!!,"Please enter your e-mail.")
               }else
                 Alert.showalert(signUpActivity!!,"Please enter your name.")
           }
            R.id.rl_backsignup->{
                signUpActivity!!.finish()
            }

        }

    }

    private fun callsignupwithrawresponse() {
        if (ConnectionDetector.isConnectingToInternet(signUpActivity)) {
            val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
            signUpActivity?.let { customProgress.showProgress(it, "Please Wait..", false) }
            val apiInterface =
                RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)
            // val callapi =apiInterface?.login(logInActivityViewBind?.et_mobileno?.text.toString(),logInActivityViewBind?.et_password?.text.toString())
            var loginjson = JSONObject();
            try {
                loginjson.put("username", signUpViewBind?.et_name?.text.toString())
                loginjson.put("email", signUpViewBind?.et_email?.text.toString())
                loginjson.put("password", signUpViewBind?.et_password?.text.toString())
                loginjson.put("reg_through", "APP")
                loginjson.put("first_name", "")
                loginjson.put("last_name", "")
                loginjson.put("simple_local_avatar", "")
                loginjson.put("billing_phone",signUpViewBind?.et_mobileno?.text.toString())
                loginjson.put("device",AppPreferenceHalper.read(PreferenceConstantParam.DEVICE_IMEI,""))
                loginjson.put("token",AppPreferenceHalper.read(PreferenceConstantParam.DEVICE_TOKEN,""))
            } catch (e: Exception) {
                e.printStackTrace()
            }
            var obj: JSONObject = loginjson
            var jsonParser: JsonParser = JsonParser()
            var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
            val callapi = apiInterface?.signupwithraw(gsonObject)
            callapi?.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    //  if (progress.isShowing)
                    //  progress.dismiss()
                    customProgress.hideProgress()
                    if (response.isSuccessful) {
                            signUpActivity?.finish()
                        } else {
                            Toast.makeText(signUpActivity, "Name or Email already exist.", Toast.LENGTH_LONG).show()
                        }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    //   if (progress.isShowing)
                    //    progress.dismiss()
                    customProgress.hideProgress()
                }
            })
        }else
            Alert.showalert(signUpActivity!!,signUpActivity!!.resources.getString(R.string.nointernet))
    }

    private fun callsignup() {
        if (ConnectionDetector.isConnectingToInternet(signUpActivity)) {
            val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
            signUpActivity?.let { customProgress.showProgress(it, "Please Wait..", false) }
            val apiInterface =
                RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)
            // val callapi =apiInterface?.login(logInActivityViewBind?.et_mobileno?.text.toString(),logInActivityViewBind?.et_password?.text.toString())
            var loginjson = JSONObject();
            try {
                loginjson.put("username", signUpViewBind?.et_name?.text.toString())
                loginjson.put("email", signUpViewBind?.et_email?.text.toString())
                loginjson.put("password", signUpViewBind?.et_password?.text.toString())
                loginjson.put("reg_through", "APP")
                loginjson.put("first_name", "")
                loginjson.put("last_name", "")
                loginjson.put("simple_local_avatar", "")
                loginjson.put("billing_phone",signUpViewBind?.et_mobileno?.text.toString())
                loginjson.put("device",AppPreferenceHalper.read(PreferenceConstantParam.DEVICE_IMEI,""))
                loginjson.put("token",AppPreferenceHalper.read(PreferenceConstantParam.DEVICE_TOKEN,""))
            } catch (e: Exception) {
                e.printStackTrace()
            }
            var obj: JSONObject = loginjson
            var jsonParser: JsonParser = JsonParser()
            var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
            val callapi = apiInterface?.signup(gsonObject)
            callapi?.enqueue(object : Callback<SignupResponse> {
                override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                    //  if (progress.isShowing)
                    //  progress.dismiss()
                    customProgress.hideProgress()
                    if (response.isSuccessful) {
                        if (response.body()?.getCode().equals("success")) {
                            AppPreferenceHalper.write(PreferenceConstantParam.isLogIn, true)
                            AppPreferenceHalper.write(PreferenceConstantParam.CUSTOMER_ID, response.body()!!.getID()!!)
                            //AppPreferenceHalper.write(PreferenceConstantParam.TOKEN, it) }
                            AppPreferenceHalper.write(PreferenceConstantParam.CUSTOMER_EMAIL, response.body()!!.getUserEmail()!!)
                            AppPreferenceHalper.write(PreferenceConstantParam.WISHLIST, response.body()!!.getWishlists()!!.toString())
                            AppPreferenceHalper.write(PreferenceConstantParam.ORDER_IDS, response.body()!!.getPurchaseProductIds()!!)
                            if (response.body()!!.getAvatarUrl()!=null)
                                AppPreferenceHalper.write(PreferenceConstantParam.CUSTOMER_IMAGE,response.body()!!.getAvatarUrl()!!.toString())
                            AppPreferenceHalper.write(PreferenceConstantParam.CUSTOMER_NAME,response.body()!!.getDisplayName()!!)
                            AppPreferenceHalper.write(PreferenceConstantParam.CUSTOMER_MOBILE,response.body()!!.getBillingAddress()!!.getPhone().toString())
                            AppPreferenceHalper.write(PreferenceConstantParam.ISFOEM_LOGIN,true)

                            signUpActivity?.finish()
                        } else {
                            Toast.makeText(signUpActivity, "Name or Email already exist.", Toast.LENGTH_LONG).show()
                        }
                    }else {
                        Alert.showalert(signUpActivity!!,"Internal server error.")
                        //Toast.makeText(signUpActivity, " Something  Wrong. Please Try Again. ", Toast.LENGTH_LONG)
                         //   .show()
                    }


                }

                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    //   if (progress.isShowing)
                    //    progress.dismiss()
                    customProgress.hideProgress()
                }
            })
        }else
            Alert.showalert(signUpActivity!!,signUpActivity!!.resources.getString(R.string.nointernet))

    }

}
