package com.sculptee.screens.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import com.google.gson.JsonParser
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

class LogInActivity:AppCompatActivity() {
    var view:View?=null
    var logInActivityOnClick:LogInActivityOnClick?=null
    var logInActivityViewBind:LoGInActivityViewBind?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view=LayoutInflater.from(this).inflate(R.layout.activity_login,null)
        setContentView(view)
        logInActivityViewBind=LoGInActivityViewBind(this,view)
        logInActivityOnClick=LogInActivityOnClick(this, logInActivityViewBind!!)

    }
    public fun callApiforlogin(){
      /*  val progress= ProgressDialog(this)
        progress?.setMessage("loading..")
        progress?.show()*/
        val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this,"Please Wait..",false)
        val apiInterface= RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)
       // val callapi =apiInterface?.login(logInActivityViewBind?.et_mobileno?.text.toString(),logInActivityViewBind?.et_password?.text.toString())
        var loginjson = JSONObject();
        try {
            loginjson.put("username",logInActivityViewBind?.et_mobileno?.text.toString())
            loginjson.put("email",logInActivityViewBind?.et_mobileno?.text.toString())
            loginjson.put("password",logInActivityViewBind?.et_password?.text.toString())
            loginjson.put("reg_through","APP")
            loginjson.put("first_name"," ")
            loginjson.put("last_name"," ")
            loginjson.put("simple_local_avatar"," ")
            loginjson.put("device",AppPreferenceHalper.read(PreferenceConstantParam.DEVICE_IMEI,""))
            loginjson.put("token",AppPreferenceHalper.read(PreferenceConstantParam.DEVICE_TOKEN,""))
            //  loginjson.put("billing_phone","")
        }catch (e: Exception){
            e.printStackTrace()
        }
        var obj: JSONObject = loginjson
        var jsonParser: JsonParser = JsonParser()
        var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
        val callapi =apiInterface?.login(gsonObject)
        callapi?.enqueue(object : Callback<LogInApiResponse>{
            override fun onResponse(call: Call<LogInApiResponse>, response: Response<LogInApiResponse>) {
              //  if (progress.isShowing)
                 //  progress.dismiss()
                customProgress.hideProgress()
                if(response.isSuccessful) {
                    if ( response.body()?.getCode().equals("success") ) {
                        AppPreferenceHalper.write(PreferenceConstantParam.isLogIn, true)
                        AppPreferenceHalper.write(PreferenceConstantParam.CUSTOMER_ID, response.body()!!.getID()!!)
                        if (response.body()!!.getAvatarUrl()!=null)
                           AppPreferenceHalper.write(PreferenceConstantParam.CUSTOMER_IMAGE,response.body()!!.getAvatarUrl()!!.toString())

                        AppPreferenceHalper.write(PreferenceConstantParam.CUSTOMER_NAME,response.body()!!.getDisplayName()!!)
                        //AppPreferenceHalper.write(PreferenceConstantParam.TOKEN, it) }
                        AppPreferenceHalper.write(PreferenceConstantParam.CUSTOMER_EMAIL, response.body()!!.getUserEmail()!!)
                        AppPreferenceHalper.write(PreferenceConstantParam.WISHLIST, response.body()!!.getWishlists()!!)
                        AppPreferenceHalper.write(PreferenceConstantParam.ORDER_IDS, response.body()!!.getPurchaseProductIds()!!)

                        AppPreferenceHalper.write(PreferenceConstantParam.CUSTOMER_MOBILE,response.body()!!.getBillingAddress()!!.getPhone().toString())

                        AppPreferenceHalper.write(PreferenceConstantParam.ISFOEM_LOGIN,true)

                        finish()
                    }else{
                        Alert.showalert(this@LogInActivity!!,"Wrong Credential.")
                    }
                }else
                    Alert.showalert(this@LogInActivity!!,"Internal server error.")
                    //Toast.makeText(applicationContext,"Wrong credential.",Toast.LENGTH_LONG).show()


            }

            override fun onFailure(call: Call<LogInApiResponse>, t: Throwable) {
             //   if (progress.isShowing)
                //    progress.dismiss()
                customProgress.hideProgress()
            }
        })

    }
}