package com.sculptee.screens.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.facebook.login.LoginManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.rts.commonutils_2_0.netconnection.ConnectionDetector
import com.sculptee.R
import com.sculptee.datastorage.CakeProduct
import com.sculptee.datastorage.RoomSingleton
import com.sculptee.fragment.address.AddressFragment
import com.sculptee.fragment.about.AboutUsFragment
import com.sculptee.fragment.contactus.ContactUsFragment
import com.sculptee.fragment.event.EventFragment
import com.sculptee.fragment.eventlisting.EventListingFragment
import com.sculptee.fragment.faq.FaqFragment
import com.sculptee.fragment.myaccount.MyAccountFragment
import com.sculptee.fragment.orderlisting.OrderListFragment
import com.sculptee.fragment.productdetails.ProductDetailsFragment
import com.sculptee.fragment.shopevent.ShopEvent
import com.sculptee.fragment.todaydeal.TodayDealFeagment
import com.sculptee.fragment.wishlist.WishListFragment
import com.sculptee.network.ApiInterface
import com.sculptee.network.RetroFitApiService
import com.sculptee.screens.profile.ProfileActivity
import com.sculptee.screens.sociallogin.SocialSignActivity
import com.sculptee.utils.Alert
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.sculptee.utils.exceptionhandler.DefaultExceptionHandler
import com.sculptee.utils.preferenceconstant.PreferenceConstantParam
import com.sculptee.utils.sheardpreference.AppPreferenceHalper
import okhttp3.ResponseBody
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class HomeActivity : AppCompatActivity() {
    var view: View? = null
    var homeActivityViewBind: HomeActivityViewBind? = null
    var homeActivityOnClick: HomeActivityOnClick? = null
    public lateinit var mDb: RoomSingleton
    public var wishlistclick: Boolean = false
    public var addcartclick = false
    public var orderclick = false
    var currentVersion: String? = null
    var latestVersion: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = LayoutInflater.from(this).inflate(R.layout.activity_home, null)
        homeActivityViewBind = HomeActivityViewBind(this, view)
        homeActivityOnClick = HomeActivityOnClick(this, homeActivityViewBind!!)
        mDb = RoomSingleton.getInstance(applicationContext)
        AppPreferenceHalper.init(this)
        Thread.setDefaultUncaughtExceptionHandler(
            DefaultExceptionHandler(this, this, HomeActivity::class.java)
        )
        setContentView(view)
        // loadeventfragment();
        homeActivityOnClick!!.openhome()
        loadcartcountvalue()
        callvalueforshipandgstvalue()
        getCurrentVersion()
      //  printHashKey(this)

    }

    private fun getCurrentVersion() {
        val pm = this.packageManager
        var pInfo: PackageInfo? = null

        try {
            pInfo = pm.getPackageInfo(this.packageName, 0)
        } catch (e1: PackageManager.NameNotFoundException) {
            // TODO Auto-generated catch block
            e1.printStackTrace()
        }
        currentVersion = pInfo!!.versionName
        GetVersionCode().execute()

    }


    internal inner class GetVersionCode : AsyncTask<Void, String, String>() {

        override fun doInBackground(vararg voids: Void): String? {

            var newVersion: String? = null

            try {
                val document =
                    Jsoup.connect("https://play.google.com/store/apps/details?id=" + this@HomeActivity.packageName + "&hl=en")
                        //  Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + "com.app.astrolab"  + "&hl=en")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                if (document != null) {
                    val element = document!!.getElementsContainingOwnText("Current Version")
                    for (ele in element) {
                        if (ele.siblingElements() != null) {
                            val sibElemets = ele.siblingElements()
                            for (sibElemet in sibElemets) {
                                newVersion = sibElemet.text()
                            }
                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return newVersion

        }


        override fun onPostExecute(onlineVersion: String?) {

            super.onPostExecute(onlineVersion)

            if (onlineVersion != null && !onlineVersion.isEmpty()) {

                if (java.lang.Float.valueOf(currentVersion) < java.lang.Float.valueOf(onlineVersion)) {
                    //show anything
                    showUpdateDialog()
                }

            }

            // Log.d("update", "Current version " + currentVersion + "playstore version " + onlineVersion);

        }
    }

    private fun showUpdateDialog() {
         var dialog: Dialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.new_version_avalible))
        builder.setPositiveButton(
            resources.getText(R.string.update)
        ) { dialog, which ->
            /* startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse
                            ("market://details?id=com.wecompli")));*/
            val appPackageName = packageName // getPackageName() from Context or Activity object
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$appPackageName")
                    )
                )
            } catch (anfe: android.content.ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                )
            }

            dialog.dismiss()
        }

        builder.setNegativeButton(
            resources.getString(R.string.cancel),
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    dialog.dismiss()
                }
            })

        builder.setCancelable(true)
        dialog = builder.show()
        dialog.setCancelable(false)
    }


    fun printHashKey(pContext: Context) {

        try {
            val info = pContext.packageManager.getPackageInfo(
                pContext.packageName,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                println(hashKey)
                Log.i("keyhash", "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e("keyhash", "printHashKey()", e)
        } catch (e: Exception) {
            Log.e("keyhash", "printHashKey()", e)
        }

    }
    public fun loadcartcountvalue() {
        // if(AppPreferenceHalper.read(PreferenceConstantParam.isLogIn,false)==true) {
        doAsync {
            val listofcaret = mDb.CakeProductDao()
                .getcatlist(AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_ID, "0")!!)
            if (listofcaret.size > 0) {
                runOnUiThread {
                    showcart(listofcaret)
                }

            } else
                homeActivityViewBind?.tv_cart_count?.visibility = View.INVISIBLE

        }
        // }
    }

    private fun showcart(listofcaret: List<CakeProduct>) {
        homeActivityViewBind?.tv_cart_count?.visibility = View.VISIBLE
        homeActivityViewBind?.tv_cart_count?.text = listofcaret.size.toString()
    }

    public fun loadeventfragment() {
        homeActivityViewBind?.tv_headertext?.setText(resources.getString(R.string.choose_event))
        val eventFragment = EventFragment()
        val manager = supportFragmentManager
        // Begin the fragment transition using support fragment manager
        val transaction = manager.beginTransaction()
        // Replace the fragment on container
        transaction.replace(R.id.content_frame, eventFragment, resources.getString(R.string.choose_event))
        // transaction.addToBackStack(null)
        // Finishing the transition
        transaction.commit()
    }


    private fun callvalueforshipandgstvalue() {
        //  val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        // customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface =
            RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)
        val callapi = apiInterface?.getgestandshipping()
        callapi!!.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                // customProgress.hideProgress()
                try {
                    val jsonObj: JSONObject = JSONObject(response.body()!!.string())
                    val code = jsonObj.getString("code")
                    if (code.equals("success")) {
                        val tax_rate = jsonObj.getString("tax_rate")
                        val shipping_cost = jsonObj.getString("shipping_cost")
                        val package_cost = jsonObj.getString("fee_package_cost")
                        val tax_calculate_type = jsonObj.getString("tax_calculate_type")
                        val shipping_method_id = jsonObj.getString("shipping_method_id")
                        val shipping_method_title = jsonObj.getString("shipping_method_title")
                        val fee_package_title = jsonObj.getString("fee_package_title")
                        val fee_tax_status = jsonObj.getString("fee_tax_status")

                        /*  {
                              "tax_rate": "18.0000",
                              "tax_calculate_type": "percent",
                              "shipping_method_id": "flat_rate",
                              "shipping_method_title": "Flat rate",
                              "shipping_cost": "70",
                              "fee_package_title": "Packaging",
                              "fee_tax_status": "taxable",
                              "fee_package_cost": "75",
                              "code": "success"
                          }*/
                        AppPreferenceHalper.write(
                            PreferenceConstantParam.SHIPPINGCOST,
                            shipping_cost
                        )
                        AppPreferenceHalper.write(
                            PreferenceConstantParam.PACKING_COST,
                            package_cost
                        )
                        AppPreferenceHalper.write(PreferenceConstantParam.ORDERGST, tax_rate)
                        AppPreferenceHalper.write(
                            PreferenceConstantParam.SHIPMETHOD_ID,
                            shipping_method_id
                        )
                        AppPreferenceHalper.write(
                            PreferenceConstantParam.SHIPMETHOD_TITLE,
                            shipping_method_title
                        )
                        AppPreferenceHalper.write(
                            PreferenceConstantParam.FEE_PACKAGETITLE,
                            fee_package_title
                        )
                        AppPreferenceHalper.write(
                            PreferenceConstantParam.FEE_TAX_STATUS,
                            fee_tax_status
                        )


                    }
                    // {"tax_rate":"18.0000","tax_calculate_type":"percent","shipping_cost":"200","package_cost":"50","code":"success"}

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // customProgress.hideProgress()
            }
        })

    }

    override fun onBackPressed() {
        /*  val eventFragment:EventFragment = fragmentManager.findFragmentByTag(resources.getString(R.string.choose_event)) as EventFragment
          if (eventFragment != null && eventFragment!!.isVisible()) {
              homeActivityViewBind?.tv_headertext?.setText(resources.getString(R.string.choose_event))
          }*/
        if (homeActivityViewBind?.drawer_layout?.isDrawerOpen(GravityCompat.START)!!) {

        }
        val fragment: Fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame)!!
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0)
            super.onBackPressed()
        else {
            // homeActivityViewBind?.
            getSupportFragmentManager().popBackStack();
        }
        if (count == 1)
            homeActivityViewBind?.tv_headertext?.setText(resources.getText(R.string.choose_event))

    }

    public fun loadeventproductlistfragment(name: String) {
        // homeActivityViewBind?.tv_headertext?.setText(resources.getString(R.string.choose_event))
        val weddingFragment = EventListingFragment()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.content_frame, weddingFragment, name)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    public fun loadeventproductDetailsfragment(name: String) {
        homeActivityViewBind?.tv_headertext?.setText(name)
        val productdetailsfragment = ProductDetailsFragment()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.content_frame, productdetailsfragment, name)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    public fun loadOrderListingfragment(name: String) {
        //  homeActivityViewBind?.tv_headertext?.setText(resources.getString(R.string.choose_event))
        val orderListFragment = OrderListFragment()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.content_frame, orderListFragment, name)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    public fun loadFaqfragment(name: String) {
        //  homeActivityViewBind?.tv_headertext?.setText(resources.getString(R.string.choose_event))
        val faqFragment = FaqFragment()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.content_frame, faqFragment, name)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    public fun loadAboutusfragment(name: String) {
        //  homeActivityViewBind?.tv_headertext?.setText(resources.getString(R.string.choose_event))
        val aboutUsFragment = AboutUsFragment()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.content_frame, aboutUsFragment, name)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    public fun loadconatctfragment(name: String) {
        //  homeActivityViewBind?.tv_headertext?.setText(resources.getString(R.string.choose_event))
        val contactUsFragment = ContactUsFragment()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.content_frame, contactUsFragment, name)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    public fun loadaddressfragment(name: String) {
        //  homeActivityViewBind?.tv_headertext?.setText(resources.getString(R.string.choose_event))
        val addressFragment = AddressFragment()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.content_frame, addressFragment, name)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onResume() {
        super.onResume()
        if (AppPreferenceHalper.read(PreferenceConstantParam.isLogIn, false) == true && AppPreferenceHalper.read(PreferenceConstantParam.ISFOEM_LOGIN,
                false) == true) {
            callvalueforshipandgstvalue()
            homeActivityOnClick!!.openhome();
            loadcartcountvalue()
            AppPreferenceHalper.write(PreferenceConstantParam.ISFOEM_LOGIN, false)

        } else {
            loadeventsidemenu()
            // homeActivityOnClick!!.openhome();
            loadcartcountvalue()
        }

        /* if(wishlistclick==true){
             if(AppPreferenceHalper.read(PreferenceConstantParam.isLogIn,false)==true){
                 loadwishlistfragment("wishlist")
                 loadcartcountvalue()
                 wishlistclick=false
             }
         }else if(addcartclick==true) {
             if (AppPreferenceHalper.read(PreferenceConstantParam.isLogIn, false) == true) {
                 loadcartcountvalue()
                 addcartclick=false
             }
         }else if (orderclick==true){
             if (AppPreferenceHalper.read(PreferenceConstantParam.isLogIn, false) == true) {
                 loadOrderListingfragment("Order")
                 loadcartcountvalue()
                 orderclick=false
             }
         }
         else {
             if (AppPreferenceHalper.read(PreferenceConstantParam.isLogIn, false) == true) {
                 loadcartcountvalue()
               //  loadeventfragment()
             }
         }*/

    }

    fun openshopeventfragment(name: String) {
        val shop = ShopEvent()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.content_frame, shop, name)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun opentodaydealFragment(name: String) {
        val todayDealFeagment = TodayDealFeagment()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.content_frame, todayDealFeagment, name)
        transaction.addToBackStack(name)
        transaction.commit()
    }

    fun loadwishlistfragment(s: String) {
        homeActivityViewBind!!.drawer_layout!!.closeDrawer(Gravity.LEFT)
        val wishListFragment = WishListFragment()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.content_frame, wishListFragment, s)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun loadmyaccountfragment(s: String) {
        val myAccountFragment = MyAccountFragment()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.content_frame, myAccountFragment, s)
        transaction.addToBackStack(null)
        transaction.commit()

    }

    @SuppressLint("MissingPermission")
    fun alertyesfuncation() {

        FirebaseAuth.getInstance().signOut()
       // FirebaseAuth.getInstance().
        LoginManager.getInstance().logOut();
        AppPreferenceHalper.clear()
        AppPreferenceHalper.write(PreferenceConstantParam.isLogIn, false)
        AppPreferenceHalper.write(PreferenceConstantParam.WISHLIST, "")
        AppPreferenceHalper.write(PreferenceConstantParam.CUSTOMER_ID, "0")

        val TelephonyMgr = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val imei = TelephonyMgr.deviceId
        AppPreferenceHalper.write(PreferenceConstantParam.DEVICE_IMEI, imei.toString())
        gettokenfromfcm()
        // calllogoutdeleteusertoken()
        homeActivityOnClick!!.openhome()
        loadcartcountvalue()
        Alert.showalert(this, " Logout Successfully.")
        loadeventfragment();

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
                // Log and toast
                // val msg = getString("tdsd", token)
                // Log.d(TAG, token)
                // Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
            })
    }

    fun calllogoutdeleteusertoken() {
        val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        if (ConnectionDetector.isConnectingToInternet(this)) {

            customProgress.showProgress(this, "Please Wait..", false)
            val apiInterface =
                RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)
            var tokendeletejson = JSONObject();
            try {
                tokendeletejson.put(
                    "user_id",
                    AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_ID, "")
                )
                tokendeletejson.put(
                    "device",
                    AppPreferenceHalper.read(PreferenceConstantParam.DEVICE_IMEI, "")
                )
                tokendeletejson.put(
                    "token",
                    AppPreferenceHalper.read(PreferenceConstantParam.DEVICE_TOKEN, "")
                )
                //  loginjson.put("billing_phone","")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            var obj: JSONObject = tokendeletejson
            var jsonParser: JsonParser = JsonParser()
            var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
            val callapi = apiInterface?.deletetoken(gsonObject)
            callapi!!.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    customProgress.hideProgress()
                    if (response.isSuccessful) {
                        alertyesfuncation()
                    } else {
                        Alert.showalert(this@HomeActivity, "Internet Server error.")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    customProgress.hideProgress()
                }
            })
        } else
            Alert.showalert(this, "No Internet connection.")
    }

    fun alertnofuncation() {
        homeActivityViewBind?.drawer_layout?.closeDrawer(Gravity.LEFT)
    }

    fun openmyaccountactivity() {
        if (AppPreferenceHalper.read(PreferenceConstantParam.isLogIn, false) == true) {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        } else {

            startActivity(Intent(this, SocialSignActivity::class.java))
        }

    }

    public fun loadeventsidemenu() {
        homeActivityViewBind?.rl_home?.setBackgroundColor(resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_shop?.setBackgroundColor(resources?.getColor(R.color.menuselectcolor)!!)
        homeActivityViewBind?.rl_todaydeal?.setBackgroundColor(resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_myorder?.setBackgroundColor(resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_lovelist?.setBackgroundColor(resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_myaccount?.setBackgroundColor(resources?.getColor(R.color.white)!!)

        homeActivityViewBind?.rl_faq?.setBackgroundColor(resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_aboutus?.setBackgroundColor(resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_contactus?.setBackgroundColor(resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_profile_address?.setBackgroundColor(resources?.getColor(R.color.white)!!)

        if(AppPreferenceHalper.read(PreferenceConstantParam.isLogIn,false)==true) {
            homeActivityViewBind?.rl_lgout?.visibility=View.VISIBLE
        }
        else
            homeActivityViewBind?.rl_lgout?.visibility=View.GONE
    }


}