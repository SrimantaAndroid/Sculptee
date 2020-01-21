package com.sculptee.screens.sociallogin

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
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
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SocialSignActivity:AppCompatActivity(),GoogleApiClient.OnConnectionFailedListener {


    var view:View?=null
    var socialSignViewBind: SocialSignViewBind?=null
    var socialLoginOnClick: SocialLoginOnClick?=null
    var callbackManager:CallbackManager?=null
    private lateinit var auth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var mGoogleApiClient:GoogleApiClient
    val RC_SIGN_IN:Int=300
    var progressDialog:ProgressDialog?=null
    val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(getApplicationContext());
        auth = FirebaseAuth.getInstance()
        callbackManager = CallbackManager.Factory.create();
        progressDialog= ProgressDialog(this)
        view=LayoutInflater.from(this).inflate(R.layout.activity_sociallogin,null)
        setContentView(view)

      //  setupfacebook();


        callbackManager = CallbackManager.Factory.create()
        socialSignViewBind?.fb_login_button?.setReadPermissions("email", "public_profile")
        socialSignViewBind?.fb_login_button?.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {

                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                customProgress.hideProgress()
                Toast.makeText(getApplicationContext(),"Cancel",Toast.LENGTH_SHORT).show()
                // ...
            }

            override fun onError(error: FacebookException) {
                customProgress.hideProgress()
                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show()
                // ...
            }
        })

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)


        mGoogleApiClient =  GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect()


        socialSignViewBind= SocialSignViewBind(this,view)
        socialLoginOnClick= SocialLoginOnClick(this, socialSignViewBind!!)
    }
    override fun onConnectionFailed(p0: ConnectionResult) {

    }
    public fun facebookloginusingfirebase(){
        // Initialize Facebook Login button
        callbackManager = CallbackManager.Factory.create()
        socialSignViewBind?.fb_login_button?.setReadPermissions("email", "public_profile")
        socialSignViewBind?.fb_login_button?.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {

                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                customProgress.hideProgress()
                Toast.makeText(getApplicationContext(),"Cancel",Toast.LENGTH_SHORT).show()
                // ...
            }

            override fun onError(error: FacebookException) {
                customProgress.hideProgress()
                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show()
                // ...
            }
        })
    }

    public fun setupfacebooklogin() {
        // Initialize Firebase Auth
     /*   progressDialog?.setMessage("loading..")
        progressDialog?.show()*/
        customProgress.showProgress(this,"Please Wait..",false)
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email","public_profile"))
        LoginManager.getInstance().registerCallback(callbackManager,object :FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                result?.accessToken?.let {
                    handleFacebookAccessToken(it) }

            }

            override fun onError(error: FacebookException?) {
              customProgress.hideProgress()
            }

            override fun onCancel() {
              customProgress.hideProgress()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch ( e: ApiException) {
                customProgress.hideProgress()
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this,"Cancel", Toast.LENGTH_SHORT).show();
                // ...
            }
        }else {
            callbackManager?.onActivityResult(requestCode, resultCode, data);
          //  setupfacebooklogin()
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
       //updateUI(currentUser)
    }
    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    customProgress.hideProgress()
                    callApiforfacebooklogin(user)
                    removeuserfromfirebase(user,credential)

                } else {
                    // If sign in fails, display a message to the user.

                }


            }
    }

    private fun removeuserfromfirebase(user: FirebaseUser?, credential: AuthCredential) {

           user!!.reauthenticate(credential)
               .addOnCompleteListener(object :OnCompleteListener<Void>{
                   override fun onComplete(p0: Task<Void>) {
                       if (p0.isSuccessful)
                           user.delete()
                   }
               })
    }

    private fun callApiforfacebooklogin(user: FirebaseUser?) {
        val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this,"Please Wait..",false)
        val apiInterface= RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)
        // val callapi =apiInterface?.login(logInActivityViewBind?.et_mobileno?.text.toString(),logInActivityViewBind?.et_password?.text.toString())
        var loginjson = JSONObject();
        try {
            loginjson.put("username",user?.displayName)
            loginjson.put("email",user?.email)
            loginjson.put("password","")
            loginjson.put("reg_through","FB_SOCIAL")
            loginjson.put("first_name",user?.displayName)
            loginjson.put("last_name"," ")
            loginjson.put("simple_local_avatar",user?.photoUrl)
            loginjson.put("device",AppPreferenceHalper.read(PreferenceConstantParam.DEVICE_IMEI,""))
            loginjson.put("token",AppPreferenceHalper.read(PreferenceConstantParam.DEVICE_TOKEN,""))
        }catch (e: Exception){
            e.printStackTrace()
        }
        var obj: JSONObject = loginjson
        var jsonParser: JsonParser = JsonParser()
        var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
        val callapi =apiInterface?.login(gsonObject)
        callapi?.enqueue(object : Callback<LogInApiResponse> {
            override fun onResponse(call: Call<LogInApiResponse>, response: Response<LogInApiResponse>) {
                //  if (progress.isShowing)
                //  progress.dismiss()
                customProgress.hideProgress()
                if(response.isSuccessful) {
                    if ( response.body()?.getCode().equals("success") ) {
                        AppPreferenceHalper.write(PreferenceConstantParam.isLogIn, true)
                        AppPreferenceHalper.write(PreferenceConstantParam.CUSTOMER_ID, response.body()!!.getID()!!)
                        //AppPreferenceHalper.write(PreferenceConstantParam.TOKEN, it) }
                        AppPreferenceHalper.write(PreferenceConstantParam.CUSTOMER_NAME,response.body()!!.getDisplayName()!!)
                        AppPreferenceHalper.write(PreferenceConstantParam.CUSTOMER_EMAIL, response.body()!!.getUserEmail()!!)
                        AppPreferenceHalper.write(PreferenceConstantParam.CUSTOMER_MOBILE,response.body()!!.getBillingAddress()!!.getPhone().toString())

                        AppPreferenceHalper.write(PreferenceConstantParam.ISFOEM_LOGIN,true)
                        AppPreferenceHalper.write(PreferenceConstantParam.ORDER_IDS, response.body()!!.getPurchaseProductIds()!!)
                        AppPreferenceHalper.write(PreferenceConstantParam.WISHLIST, response.body()!!.getWishlists()!!.toString())
                     //   AppPreferenceHalper.write(PreferenceConstantParam.ORDER_IDS, response.body()!!.getOrderIds()!!)
                        if (response.body()!!.getAvatarUrl()!=null)
                            AppPreferenceHalper.write(PreferenceConstantParam.CUSTOMER_IMAGE,response.body()!!.getAvatarUrl()!!.toString())

                        finish()
                    }else{
                        Alert.showalert(this@SocialSignActivity!!,"Something Wrong.Please try again using another Account.")
                    }
                }
                else
                    Alert.showalert(this@SocialSignActivity!!,"Internal server error.")


            }

            override fun onFailure(call: Call<LogInApiResponse>, t: Throwable) {
                //   if (progress.isShowing)
                //    progress.dismiss()
                customProgress.hideProgress()
            }
        })

    }

    public fun googlelogin(){

        customProgress.showProgress(this,"Please Wait..",false)
        //FirebaseAuth.getInstance().signOut();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.clearDefaultAccountAndReconnect();
        }
       // Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    callApiforlogin(user)
                    removeuserfromfirebase(user, credential)

                } else {

                    // If sign in fails, display a message to the user.
                }
                // ...
            }
    }

    override fun onResume() {
        super.onResume()
        if(AppPreferenceHalper.read(PreferenceConstantParam.isLogIn,false)==true){
            AppPreferenceHalper.write(PreferenceConstantParam.ISFOEM_LOGIN,true)
            finish()
        }
      //  customProgress.hideProgress()

    }

    public fun callApiforlogin(user: FirebaseUser?) {
       // val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        ///customProgress.showProgress(this,"Please Wait..",false)
        val apiInterface= RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)
        // val callapi =apiInterface?.login(logInActivityViewBind?.et_mobileno?.text.toString(),logInActivityViewBind?.et_password?.text.toString())
        var loginjson = JSONObject();
        try {
           // val uname:String=(user?.displayName)!!.replaceall("\\s", "")

            loginjson.put("username",user?.displayName)
            loginjson.put("email",user?.email)
            loginjson.put("password","")
            loginjson.put("reg_through","GPLUS_SOCIAL")
            loginjson.put("first_name",user?.displayName)
            loginjson.put("last_name"," ")
            loginjson.put("simple_local_avatar",user?.photoUrl)
            loginjson.put("device",AppPreferenceHalper.read(PreferenceConstantParam.DEVICE_IMEI,""))
            loginjson.put("token",AppPreferenceHalper.read(PreferenceConstantParam.DEVICE_TOKEN,""))
        }catch (e: Exception){
            e.printStackTrace()
        }
        var obj: JSONObject = loginjson
        var jsonParser: JsonParser = JsonParser()
        var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
        val callapi =apiInterface?.login(gsonObject)
        callapi?.enqueue(object : Callback<LogInApiResponse> {
            override fun onResponse(call: Call<LogInApiResponse>, response: Response<LogInApiResponse>) {
                //  if (progress.isShowing)
                //  progress.dismiss()
                customProgress.hideProgress()
                if(response.isSuccessful) {
                    if ( response.body()?.getCode().equals("success") ) {
                        AppPreferenceHalper.write(PreferenceConstantParam.isLogIn, true)
                        AppPreferenceHalper.write(PreferenceConstantParam.CUSTOMER_ID, response.body()!!.getID()!!)
                        //AppPreferenceHalper.write(PreferenceConstantParam.TOKEN, it) }
                        if (response.body()!!.getAvatarUrl()!=null)
                            AppPreferenceHalper.write(PreferenceConstantParam.CUSTOMER_IMAGE,response.body()!!.getAvatarUrl()!!.toString())
                        AppPreferenceHalper.write(PreferenceConstantParam.CUSTOMER_NAME,response.body()!!.getDisplayName()!!)
                        AppPreferenceHalper.write(PreferenceConstantParam.WISHLIST, response.body()!!.getWishlists()!!)
                        AppPreferenceHalper.write(PreferenceConstantParam.ORDER_IDS, response.body()!!.getPurchaseProductIds()!!)
                        AppPreferenceHalper.write(PreferenceConstantParam.CUSTOMER_EMAIL, response.body()!!.getUserEmail()!!)
                        AppPreferenceHalper.write(PreferenceConstantParam.CUSTOMER_MOBILE,response.body()!!.getBillingAddress()!!.getPhone().toString())

                        AppPreferenceHalper.write(PreferenceConstantParam.ISFOEM_LOGIN,true)

                        finish()
                    }else{
                        Toast.makeText(applicationContext,"Something Wrong.Please try again using another Account.",Toast.LENGTH_LONG).show()
                    }
                }else
                    Alert.showalert(this@SocialSignActivity,"Something Wrong.Please try again.")

            }

            override fun onFailure(call: Call<LogInApiResponse>, t: Throwable) {
                //   if (progress.isShowing)
                //    progress.dismiss()
                customProgress.hideProgress()
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()

        FirebaseAuth.getInstance().signOut()
        LoginManager.getInstance().logOut();
       // auth.currentUser!!.delete()
    }
}