package com.sculptee.screens.home

import android.content.Intent
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.sculptee.R
import com.sculptee.screens.cart.CartActivity
import com.sculptee.utils.preferenceconstant.PreferenceConstantParam
import com.sculptee.utils.sheardpreference.AppPreferenceHalper
import android.content.DialogInterface

import android.app.AlertDialog
import com.rts.commonutils_2_0.netconnection.ConnectionDetector
import com.sculptee.network.ApiInterface
import com.sculptee.network.RetroFitApiService
import com.sculptee.screens.sociallogin.SocialSignActivity
import com.sculptee.utils.Alert
import com.sculptee.utils.customprogress.CustomProgressDialog
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class HomeActivityOnClick :View.OnClickListener {
    var homeActivity: HomeActivity?=null
    var homeActivityViewBind: HomeActivityViewBind?=null
    constructor(homeActivity: HomeActivity, homeActivityViewBind: HomeActivityViewBind){
        this.homeActivity=homeActivity
        this.homeActivityViewBind=homeActivityViewBind
        setonclick();
    }

    private fun setonclick() {
        homeActivityViewBind?.imgmenu?.setOnClickListener(this)
        homeActivityViewBind?.img_cart?.setOnClickListener(this)
       // homeActivityViewBind?.ll_oeders?.setOnClickListener(this)
        homeActivityViewBind?.ll_logout?.setOnClickListener(this)
        homeActivityViewBind?.rl_home?.setOnClickListener(this)
        homeActivityViewBind?.rl_shop?.setOnClickListener(this)
        homeActivityViewBind?.rl_lovelist?.setOnClickListener(this)
        homeActivityViewBind?.rl_myorder?.setOnClickListener(this)
        homeActivityViewBind?.rl_myaccount?.setOnClickListener(this)
        homeActivityViewBind?.rl_todaydeal?.setOnClickListener(this)

        homeActivityViewBind?.rl_faq?.setOnClickListener(this)
        homeActivityViewBind?.rl_aboutus?.setOnClickListener(this)
        homeActivityViewBind?.rl_contactus?.setOnClickListener(this)
        homeActivityViewBind?.rl_menu?.setOnClickListener(this)
        homeActivityViewBind?.rl_lgout?.setOnClickListener(this)
        homeActivityViewBind?.rl_profile_address?.setOnClickListener(this)
        homeActivityViewBind?.rl_home?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.menuselectcolor)!!)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.rl_menu->{
                homeActivityViewBind?.drawer_layout?.openDrawer(Gravity.LEFT)
            }

            R.id.imgmenu->{
                homeActivityViewBind?.drawer_layout?.openDrawer(Gravity.LEFT)
            }

            R.id.img_cart->{
                val intent = Intent(homeActivity, CartActivity::class.java)
                homeActivity?.startActivity(intent)
              /*  if(AppPreferenceHalper.read(PreferenceConstantParam.isLogIn,false)==true) {
                    val intent = Intent(homeActivity, CartActivity::class.java)
                    homeActivity?.startActivity(intent)
                }else{
                    //Toast.makeText(homeActivity,"Please Login First",Toast.LENGTH_LONG).show()
                  //  Alert.showalert(homeActivity!!,"Please Login First to view your cart-item")
                    homeActivity!!.addcartclick=true
                   homeActivity?.startActivity(Intent(homeActivity, SocialSignActivity::class.java))
                }*/
            }
           /* R.id.ll_oeders->{
                homeActivityViewBind?.drawer_layout?.closeDrawer(Gravity.LEFT)
                homeActivity?.loadOrderListingfragment("OrderListing")
            }*/
            R.id.rl_lgout->{
              //  showconfirmationalert();
                homeActivityViewBind?.drawer_layout?.closeDrawer(Gravity.LEFT)
                if(AppPreferenceHalper.read(PreferenceConstantParam.isLogIn,false)==true) {
                  //  Alert.showyesnoalert(homeActivity!!, "Are you want to logout?")
                    Alert.showyesnoalert(homeActivity!!, "Are you want to logout?")
                }
                else
                    Alert.showalert(homeActivity!!,"You already logged-out.")

            }
            R.id.rl_home->{
                openhome()

           }
            R.id.rl_shop->{
                val fm = homeActivity!!.getSupportFragmentManager()
                for (i in 0 until fm.getBackStackEntryCount()) {
                    fm.popBackStack()
                }
                openshop()
                homeActivityViewBind?.tv_headertext?.setText(homeActivity?.resources?.getString(R.string.shopbyevent))
                homeActivity?.loadeventfragment()

            }
            R.id.rl_todaydeal->{
                Alert.showalert(homeActivity!!,"Under development.")
               // opentodatdeal()
               // homeActivityViewBind?.tv_headertext?.setText(homeActivity?.resources?.getString(R.string.todaydeal))
               // homeActivity?.opentodaydealFragment("Today Deal")

            }
            R.id.rl_myorder->{
                if(AppPreferenceHalper.read(PreferenceConstantParam.isLogIn,false)==true) {
                    if (ConnectionDetector.isConnectingToInternet(homeActivity)) {
                        val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
                        this!!.homeActivity?.let { customProgress.showProgress(it, "Please Wait..", false)
                        }
                        val apiInterface = RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)
                        val token: String = "Bearer " + AppPreferenceHalper.read(PreferenceConstantParam.TOKEN, "")
                        var customerid: String = AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_ID, "").toString()
                        val callapi = apiInterface?.getevetcategoriwise(customerid)
                        callapi!!.enqueue(object :Callback<ResponseBody>{
                            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                customProgress.hideProgress()

                                try {
                                    val jsonArray: JSONArray = JSONArray(response.body()?.string())
                                    if (jsonArray.length() > 0) {
                                        openmyorder()
                                        homeActivityViewBind?.tv_headertext?.setText(homeActivity?.resources?.getString(R.string.orderlist))
                                        homeActivity?.loadOrderListingfragment("OrderListing")

                                    }else
                                        Alert.showalert(homeActivity!!,"You don't have any Order.")

                                }catch (e:Exception){
                                    e.printStackTrace()
                                }

                            }

                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                customProgress.hideProgress()
                            }

                        })
                    }



                }else{
                    //Toast.makeText(homeActivity,"Please Login First",Toast.LENGTH_LONG).show()
                   // Alert.showalert(homeActivity!!,"Please Login First to view your cart-item")
                    openmyorder()
                    homeActivity?.orderclick=true
                    homeActivity?.startActivity(Intent(homeActivity,SocialSignActivity::class.java))
                }

            }
            R.id.rl_lovelist->{
                if(AppPreferenceHalper.read(PreferenceConstantParam.isLogIn,false)==true) {
                    if(!AppPreferenceHalper.read(PreferenceConstantParam.WISHLIST,"").equals("")) {
                        openmylovelist()
                        homeActivityViewBind?.tv_headertext?.setText(homeActivity?.resources?.getString(R.string.wishlist))
                        homeActivity?.loadwishlistfragment("wishlist");
                    }else
                      {
                        Alert.showalert(homeActivity!!,"You don't have any wish-item.")
                      }
                    }else {
                        //Toast.makeText(homeActivity,"Please Login First",Toast.LENGTH_LONG).show()
                        openmylovelist()
                        homeActivity!!.wishlistclick=true
                        homeActivity?.startActivity(Intent(homeActivity,SocialSignActivity::class.java))
                       // Alert.showalert(homeActivity!!, "You don't have any wish-item.")
                    }
               /* }else{
                    //Toast.makeText(homeActivity,"Please Login First",Toast.LENGTH_LONG).show()
                    Alert.showalert(homeActivity!!,"Please Login First to view your Wish-item.")
                }*/



            }
            R.id.rl_myaccount->{
                if(AppPreferenceHalper.read(PreferenceConstantParam.isLogIn,false)==true) {
                    openmyaccount()
                    // homeActivity!!.openmyaccountactivity()
                    homeActivityViewBind?.tv_headertext?.setText(
                        homeActivity?.resources?.getString(
                            R.string.myacccount
                        )
                    )
                    homeActivity?.loadmyaccountfragment("myaccount")
                }else
                    homeActivity?.startActivity(Intent(homeActivity,SocialSignActivity::class.java))
            }
            R.id.rl_faq->{
               // homeActivityViewBind?.drawer_layout?.closeDrawer(Gravity.LEFT)
                openfaq()
                homeActivity?.loadFaqfragment("FAQ")
            }
            R.id.rl_contactus->{
                opencontactus()
                homeActivity?.loadconatctfragment("contactus")
               // homeActivityViewBind?.drawer_layout?.closeDrawer(Gravity.LEFT)
            }
            R.id.rl_aboutus->{
                openaboutus()
                homeActivity?.loadAboutusfragment("aboutus")
              //  homeActivityViewBind?.drawer_layout?.closeDrawer(Gravity.LEFT)
            }

            R.id.rl_profile_address1->{
                //Alert.showalert(homeActivity!!,"Under Development.")
                if(AppPreferenceHalper.read(PreferenceConstantParam.isLogIn,false)==true) {
                    openaddress()
                    homeActivity?.loadaddressfragment("address")
                }
                else
                    homeActivity?.startActivity(Intent(homeActivity,SocialSignActivity::class.java))

            }
        }

    }

    public fun openaddress() {

        homeActivityViewBind?.rl_myaccount?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_lovelist?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_myorder?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_todaydeal?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_home?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_shop?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)

        homeActivityViewBind?.rl_faq?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_aboutus?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)

        homeActivityViewBind?.rl_contactus?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_profile_address?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.menuselectcolor)!!)
        homeActivityViewBind?.drawer_layout?.closeDrawer(Gravity.LEFT)
        homeActivityViewBind?.tv_headertext?.setText(homeActivity?.resources?.getString(R.string.address))


    }

    public fun openaboutus() {
        homeActivityViewBind?.rl_myaccount?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_lovelist?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_myorder?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_todaydeal?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_home?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_shop?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)

        homeActivityViewBind?.rl_faq?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_aboutus?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.menuselectcolor)!!)
        homeActivityViewBind?.rl_contactus?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_profile_address?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)

        homeActivityViewBind?.drawer_layout?.closeDrawer(Gravity.LEFT)
        homeActivityViewBind?.tv_headertext?.setText(homeActivity?.resources?.getString(R.string.aboutus))

    }

    public fun opencontactus() {
        homeActivityViewBind?.rl_myaccount?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_lovelist?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_myorder?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_todaydeal?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_home?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_shop?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)

        homeActivityViewBind?.rl_faq?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_aboutus?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_contactus?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.menuselectcolor)!!)
        homeActivityViewBind?.rl_profile_address?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)


        homeActivityViewBind?.drawer_layout?.closeDrawer(Gravity.LEFT)
        homeActivityViewBind?.tv_headertext?.setText(homeActivity?.resources?.getString(R.string.conatctus))
    }

    public fun openfaq() {
        homeActivityViewBind?.rl_myaccount?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_lovelist?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_myorder?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_todaydeal?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_home?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_shop?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)

        homeActivityViewBind?.rl_faq?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.menuselectcolor)!!)
        homeActivityViewBind?.rl_aboutus?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_contactus?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_profile_address?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)


        homeActivityViewBind?.drawer_layout?.closeDrawer(Gravity.LEFT)
        homeActivityViewBind?.tv_headertext?.setText(homeActivity?.resources?.getString(R.string.faq))

    }

    public fun openmyaccount() {
        homeActivityViewBind?.rl_myaccount?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.menuselectcolor)!!)
        homeActivityViewBind?.rl_lovelist?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_myorder?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_todaydeal?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_home?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_shop?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)

        homeActivityViewBind?.rl_faq?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_aboutus?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_contactus?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_profile_address?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)


        homeActivityViewBind?.drawer_layout?.closeDrawer(Gravity.LEFT)
    }

    public fun openmylovelist() {
        homeActivityViewBind?.rl_lovelist?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.menuselectcolor)!!)
        homeActivityViewBind?.rl_myorder?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_todaydeal?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_home?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_shop?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_myaccount?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.drawer_layout?.closeDrawer(Gravity.LEFT)

        homeActivityViewBind?.rl_faq?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_aboutus?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_contactus?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_profile_address?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)

    }

    public fun openmyorder() {
        homeActivityViewBind?.rl_myorder?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.menuselectcolor)!!)
        homeActivityViewBind?.rl_todaydeal?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_home?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_shop?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_lovelist?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_myaccount?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.drawer_layout?.closeDrawer(Gravity.LEFT)


        homeActivityViewBind?.rl_faq?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_aboutus?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_contactus?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_profile_address?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)


    }

    private fun opentodatdeal() {
        homeActivityViewBind?.rl_todaydeal?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.menuselectcolor)!!)
        homeActivityViewBind?.rl_home?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_shop?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_myorder?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_lovelist?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_myaccount?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)

        homeActivityViewBind?.drawer_layout?.closeDrawer(Gravity.LEFT)


        homeActivityViewBind?.rl_faq?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_aboutus?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_contactus?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_profile_address?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)

    }

    public fun openshop() {
        homeActivityViewBind?.rl_shop?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.menuselectcolor)!!)
        homeActivityViewBind?.rl_home?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_todaydeal?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_myorder?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_lovelist?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_myaccount?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)

        homeActivityViewBind?.rl_faq?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_aboutus?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_contactus?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_profile_address?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)


        homeActivityViewBind?.drawer_layout?.closeDrawer(Gravity.LEFT)
        homeActivity?.loadeventfragment();
    }

    public fun openhome() {
        homeActivityViewBind?.rl_home?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_shop?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.menuselectcolor)!!)
        homeActivityViewBind?.rl_todaydeal?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_myorder?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_lovelist?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_myaccount?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)


        homeActivityViewBind?.rl_faq?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_aboutus?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_contactus?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)
        homeActivityViewBind?.rl_profile_address?.setBackgroundColor(homeActivity?.resources?.getColor(R.color.white)!!)



        homeActivityViewBind?.tv_headertext!!.text=homeActivity!!.resources.getString(R.string.choose_event)
        homeActivityViewBind?.drawer_layout?.closeDrawer(Gravity.LEFT)
        homeActivity?.loadeventfragment();
        if(AppPreferenceHalper.read(PreferenceConstantParam.isLogIn,false)==true) {
            homeActivityViewBind?.rl_lgout?.visibility=View.VISIBLE
        }else
            homeActivityViewBind?.rl_lgout?.visibility=View.GONE
    }

   /* private fun showconfirmationalert() {
        val builder = AlertDialog.Builder(homeActivity)

        builder.setTitle("Confirm")
        builder.setMessage("Are you sure?")

        builder.setPositiveButton("YES", DialogInterface.OnClickListener { dialog, which ->
            // Do nothing but close the dialog
            AppPreferenceHalper.write(PreferenceConstantParam.isLogIn,false)
            homeActivityViewBind?.drawer_layout?.closeDrawer(Gravity.LEFT)
            dialog.dismiss()
        })

        builder.setNegativeButton("NO", DialogInterface.OnClickListener { dialog, which ->
            // Do nothing
            homeActivityViewBind?.drawer_layout?.closeDrawer(Gravity.LEFT)
            dialog.dismiss()
        })

        val alert = builder.create()
        alert.show()
    }*/

}