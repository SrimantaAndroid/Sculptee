package com.sculptee.screens.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.rts.commonutils_2_0.netconnection.ConnectionDetector
import com.sculptee.R
import com.sculptee.network.ApiInterface
import com.sculptee.network.RetroFitApiService
import com.sculptee.screens.sociallogin.SocialSignActivity
import com.sculptee.utils.Alert
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.sculptee.utils.preferenceconstant.PreferenceConstantParam
import com.sculptee.utils.sheardpreference.AppPreferenceHalper
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileActivity:AppCompatActivity() {
    var profileViewBind:ProfileViewBind?=null
    var profileOnclick:ProfileOnclick?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view:View=LayoutInflater.from(this).inflate(R.layout.activity_my_profile,null)
        setContentView(view)
        profileViewBind=ProfileViewBind(this,view)
        profileOnclick= ProfileOnclick(this, profileViewBind!!)
        setprofileinfo()
    }

    private fun setprofileinfo() {
        profileViewBind!!.tv_profilenmae!!.setText(AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_NAME,""))
        if(!AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_NAME,"").equals("")) {
            Glide.with(this)
                .load(AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_NAME, ""))
                //  .apply(requestOptions)
                //  .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(cartActivity.resources.getDimension(R.dimen._3sdp).toInt())))
                .into(profileViewBind!!.img_profile)
        }
    }

    fun calllogoutdeleteusertoken() {
        val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        if (ConnectionDetector.isConnectingToInternet(this)) {

            customProgress.showProgress(this, "Please Wait..", false)
            val apiInterface = RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)
            var tokendeletejson = JSONObject();
            try {
                tokendeletejson.put("user_id", AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_ID, ""))
                tokendeletejson.put("device", AppPreferenceHalper.read(PreferenceConstantParam.DEVICE_IMEI, ""))
                tokendeletejson.put("token", AppPreferenceHalper.read(PreferenceConstantParam.DEVICE_TOKEN, ""))
                //  loginjson.put("billing_phone","")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            var obj: JSONObject = tokendeletejson
            var jsonParser: JsonParser = JsonParser()
            var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
            val callapi = apiInterface?.deletetoken(gsonObject)
            callapi!!.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    customProgress.hideProgress()
                    if(response.isSuccessful){
                        alertyesfuncation()
                    }else{
                        Alert.showalert(this@ProfileActivity, "Internet Server error")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    customProgress.hideProgress()
                }
            })
        }else
            Alert.showalert(this,"No Internet connection.")
    }
    @SuppressLint("MissingPermission")
    fun alertyesfuncation() {
        var ieminumber=AppPreferenceHalper.read(PreferenceConstantParam.DEVICE_IMEI,"")
        AppPreferenceHalper.clear()
        AppPreferenceHalper.write(PreferenceConstantParam.isLogIn,false)
        AppPreferenceHalper.write(PreferenceConstantParam.WISHLIST,"")
        AppPreferenceHalper.write(PreferenceConstantParam.CUSTOMER_ID,"0")


        AppPreferenceHalper.write(PreferenceConstantParam.DEVICE_IMEI,ieminumber!!)
        gettokenfromfcm()
        // calllogoutdeleteusertoken()

        Alert.showalert(this," Logout Successfully.")
        val intent=Intent(this,SocialSignActivity::class.java)
        startActivity(intent)
        finish()

    }
    @SuppressLint("MissingPermission")
    private fun gettokenfromfcm() {
        //  val TelephonyMgr = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        //   val imei = TelephonyMgr.deviceId
        //  val imei= Settings.Secure.getString(context.getContenResolver(), InstanceID.getInstance(context).getId());
        //  AppPreferenceHalper.write(PreferenceConstantParam.DEVICE_IMEI,imei.toString())
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    //   Log.w(TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token
                AppPreferenceHalper.write(PreferenceConstantParam.DEVICE_TOKEN, token!!.toString())

            })
    }

}