package com.sculptee.screens.placeorder

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.payumoney.core.PayUmoneyConfig
import com.payumoney.core.PayUmoneyConstants
import com.payumoney.core.PayUmoneySdkInitializer
import com.payumoney.core.entity.TransactionResponse
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager
import com.payumoney.sdkui.ui.utils.ResultModel
import com.rts.commonutils_2_0.netconnection.ConnectionDetector
import com.sculptee.R
import com.sculptee.datastorage.RoomSingleton
import com.sculptee.network.ApiInterface
import com.sculptee.network.RetroFitApiService
import com.sculptee.screens.home.HomeActivity
import com.sculptee.utils.Alert
import com.sculptee.utils.AppConstants
import com.sculptee.utils.Constants
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.sculptee.utils.preferenceconstant.PreferenceConstantParam
import com.sculptee.utils.sheardpreference.AppPreferenceHalper
import okhttp3.ResponseBody
import org.jetbrains.anko.doAsync
import org.json.JSONArray
import org.json.JSONObject

import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import okhttp3.MediaType
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class PlaceOrderActivity : AppCompatActivity() {
    var placeorderViewBind: PlaceorderViewBind? = null
    var placeOrderOnclick: PlaceOrderOnclick? = null
    public lateinit var mDb: RoomSingleton
    var jsonParams: Map<String, Any> = HashMap()
    var cart_json = JSONObject();
    var merchantId = "5884494"
    var merchantkey = "WnWkb6be"  //   first test key only
    val TAG = "MainActivity : "
    var merchantHash:String?=null
    var totalprice: Int? = 0
    var Addressselection:Int?=1
    var order_product_ids=""
    private var mPaymentParams: PayUmoneySdkInitializer.PaymentParam? = null
    val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = LayoutInflater.from(this).inflate(R.layout.activity_check_out, null)
        mDb = RoomSingleton.getInstance(applicationContext)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        placeorderViewBind = PlaceorderViewBind(this, view)
        placeOrderOnclick = PlaceOrderOnclick(this, placeorderViewBind!!)
        setContentView(view)
        setdeafultaddress()

       // setValueforShipAddresshome()
       // callvalueforshipandgstvalue()
       // callapiforpaymentsucesstranscation("gjguh uu yuy o; y")


    }

    private fun setdeafultaddress() {

        if(AppPreferenceHalper.read(PreferenceConstantParam.DeafultAddressType,"")!!.equals("home")){
            placeorderViewBind?.tv_underline_office?.visibility = View.INVISIBLE
            placeorderViewBind?.tv_underline_home?.visibility = View.VISIBLE

                resources?.getColor(R.color.desc_un_elected_bg)?.let {
                    placeorderViewBind?.tv_ship_home?.setBackgroundColor(it)
                }
            resources?.getColor(R.color.card_bg_color)?.let {
                placeorderViewBind?.tv_ship_office?.setBackgroundColor(
                    it
                )
            }
        }else if(AppPreferenceHalper.read(PreferenceConstantParam.DeafultAddressType,"")!!.equals("office")){
            placeorderViewBind?.tv_underline_home?.visibility = View.INVISIBLE
            placeorderViewBind?.tv_underline_office?.visibility = View.VISIBLE
           resources?.getColor(R.color.desc_un_elected_bg)?.let {
                placeorderViewBind?.tv_ship_office?.setBackgroundColor(it)
            }

            resources?.getColor(R.color.card_bg_color)?.let {
                placeorderViewBind?.tv_ship_home?.setBackgroundColor(it
                )
            }
        }
        placeorderViewBind?.et_ship_fristname?.setText(AppPreferenceHalper.read(PreferenceConstantParam.Deafult_F_name,""))
        placeorderViewBind?.et_ship_last_name?.setText(AppPreferenceHalper.read(PreferenceConstantParam.Deafult_L_name,""))
       // placeorderViewBind?.et_ship_phone?.setText(AppPreferenceHalper.read(PreferenceConstantParam.O_MOB,""))

        placeorderViewBind?.et_ship_streetaddress1?.setText(AppPreferenceHalper.read(PreferenceConstantParam.Deafult_Street_address1,""))
        placeorderViewBind?.et_ship_streetaddress2?.setText(AppPreferenceHalper.read(PreferenceConstantParam.Deafult_Street_Address2,""))
        placeorderViewBind?.et_ship_town?.setText(AppPreferenceHalper.read(PreferenceConstantParam.Deafult_TownCity,""))
        placeorderViewBind?.et_ship_state?.setText(AppPreferenceHalper.read(PreferenceConstantParam.Deafult_state,""))
        placeorderViewBind?.et_ship_post?.setText(AppPreferenceHalper.read(PreferenceConstantParam.Deafult_Pin,""))
    }

    private fun callvalueforshipandgstvalue() {
        val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface =
            RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)
        val callapi = apiInterface?.getgestandshipping()
        callapi!!.enqueue(object :Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                customProgress.hideProgress()
                try {
                    val jsonObj:JSONObject = JSONObject(response.body()!!.string())
                    val code=jsonObj.getString("code")
                    if(code.equals("success")) {
                        val tax_rate = jsonObj.getString("tax_rate")
                        val shipping_cost = jsonObj.getString("shipping_cost")
                        val package_cost = jsonObj.getString("fee_package_cost")
                        val tax_calculate_type=jsonObj.getString("tax_calculate_type")
                        val shipping_method_id=jsonObj.getString("shipping_method_id")
                        val shipping_method_title=jsonObj.getString("shipping_method_title")
                        val fee_package_title=jsonObj.getString("fee_package_title")
                        val fee_tax_status=jsonObj.getString("fee_tax_status")

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
                        AppPreferenceHalper.write(PreferenceConstantParam.SHIPPINGCOST,shipping_cost)
                        AppPreferenceHalper.write(PreferenceConstantParam.PACKING_COST,package_cost)
                        AppPreferenceHalper.write(PreferenceConstantParam.ORDERGST,tax_rate)
                        AppPreferenceHalper.write(PreferenceConstantParam.SHIPMETHOD_ID,shipping_method_id)
                        AppPreferenceHalper.write(PreferenceConstantParam.SHIPMETHOD_TITLE,shipping_method_title)
                        AppPreferenceHalper.write(PreferenceConstantParam.FEE_PACKAGETITLE,fee_package_title)
                        AppPreferenceHalper.write(PreferenceConstantParam.FEE_TAX_STATUS,fee_tax_status)


                    }
                   // {"tax_rate":"18.0000","tax_calculate_type":"percent","shipping_cost":"200","package_cost":"50","code":"success"}

                }catch (e:Exception){
                    e.printStackTrace()
                }
                 }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                customProgress.hideProgress()
            }
        })

    }

    public fun setValueforShipAddresshome() {
        placeorderViewBind?.et_ship_fristname?.setText(AppPreferenceHalper.read(PreferenceConstantParam.H_F_NAME,""))
        placeorderViewBind?.et_ship_last_name?.setText(AppPreferenceHalper.read(PreferenceConstantParam.H_L_NAME,""))
        placeorderViewBind?.et_ship_phone?.setText(AppPreferenceHalper.read(PreferenceConstantParam.H_MOB,""))
        placeorderViewBind?.et_ship_streetaddress1?.setText(AppPreferenceHalper.read(PreferenceConstantParam.H_STREET1,""))
        placeorderViewBind?.et_ship_streetaddress2?.setText(AppPreferenceHalper.read(PreferenceConstantParam.H_STREET2,""))
        placeorderViewBind?.et_ship_post?.setText(AppPreferenceHalper.read(PreferenceConstantParam.H_ZIP,""))
       // placeorderViewBind?.et_ship_company_name?.setText(AppPreferenceHalper.read(PreferenceConstantParam.H_COMPANY,""))
        if (AppPreferenceHalper.read(PreferenceConstantParam.isLogIn, false) == true){

            placeorderViewBind?.et_ship_company_name!!.setText(AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_EMAIL, ""))
            placeorderViewBind?.et_ship_company_name!!.isEnabled=false

        }

    }

    public fun setValueforShipAddressOffice() {
        placeorderViewBind?.et_ship_fristname?.setText(AppPreferenceHalper.read(PreferenceConstantParam.O_F_NAME,""))
        placeorderViewBind?.et_ship_last_name?.setText(AppPreferenceHalper.read(PreferenceConstantParam.O_L_NAME,""))
        placeorderViewBind?.et_ship_phone?.setText(AppPreferenceHalper.read(PreferenceConstantParam.O_MOB,""))
        placeorderViewBind?.et_ship_streetaddress1?.setText(AppPreferenceHalper.read(PreferenceConstantParam.O_STREET1,""))
        placeorderViewBind?.et_ship_streetaddress2?.setText(AppPreferenceHalper.read(PreferenceConstantParam.O_STREET2,""))
        placeorderViewBind?.et_ship_post?.setText(AppPreferenceHalper.read(PreferenceConstantParam.O_ZIP,""))
       // placeorderViewBind?.et_ship_company_name?.setText(AppPreferenceHalper.read(PreferenceConstantParam.O_COMPANY,""))
        if (AppPreferenceHalper.read(PreferenceConstantParam.isLogIn, false) == true){

            placeorderViewBind?.et_ship_company_name!!.setText(AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_EMAIL, ""))
            placeorderViewBind?.et_ship_company_name!!.isEnabled=false

        }

    }

    public fun getcartlist() {

        doAsync {
            val list = mDb.CakeProductDao().getcatlist(AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_ID,"")!!)
            var obj: JSONObject? = null
            var metajsonArray: JSONArray? = null

            val line_itemsjsonArray = JSONArray()
            for (CakeProduct in list) {
                totalprice=0
                try {
                    obj = JSONObject()
                    metajsonArray = JSONArray()

                    totalprice = totalprice!! + CakeProduct.weight*CakeProduct?.product_total_price!!
                    obj.put("product_id", CakeProduct.product_id)
                    obj.put("quantity", CakeProduct.weight)
                    obj.put("variation_id", CakeProduct.variation_id)
                    obj.put("subtotal", totalprice.toString())
                    obj.put("total", totalprice.toString())

                    order_product_ids= ","+CakeProduct.product_id

                    val meta_data1 = JSONObject()
                    meta_data1.put("key", "pa_withegg-eggless")
                    meta_data1.put("value", CakeProduct.product_type)

                    val meta_data2 = JSONObject()
                    meta_data2.put("key", "pa_flavour")
                    meta_data2.put("value", CakeProduct.flovour)

                    val meta_data3 = JSONObject()
                    meta_data3.put("key", "pa_size")
                    meta_data3.put("value", CakeProduct.weight)


                    val meta_data4 = JSONObject()
                    meta_data4.put("key", "Message on cakes")
                    meta_data4.put("value", CakeProduct.message_on_cake)

                    val meta_data5 = JSONObject()
                    meta_data5.put("key", "sculptee_hid_withegg_per_pound")
                    meta_data5.put("value", CakeProduct.withegg_per_pound)

                    val meta_data6 = JSONObject()
                    meta_data6.put("key", "sculptee_hid_eggless_per_pound")
                    meta_data6.put("value", CakeProduct.eggless_per_pound)



                    metajsonArray.put(meta_data1)
                    metajsonArray.put(meta_data2)
                    metajsonArray.put(meta_data3)
                    metajsonArray.put(meta_data4)
                    metajsonArray.put(meta_data5)
                    metajsonArray.put(meta_data6)
                    obj.put("meta_data", metajsonArray)
                    line_itemsjsonArray.put(obj)
                    System.out.println(obj.toString())




                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }

            cart_json.put("line_items", line_itemsjsonArray)


            setmetadata(totalprice!!)

            System.out.println(cart_json.toString())

        }
        callorderApi()

    }

    fun craetecartlistorder() {
        try {
            if(AppPreferenceHalper.read(PreferenceConstantParam.isLogIn, false) == true)
               cart_json.put("customer_id", AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_ID, ""))
            else
                cart_json.put("customer_id", "0")

            cart_json.put("customer_note", placeorderViewBind?.et_ordernotes?.text.toString())
            cart_json.put("payment_method", "bacs")
            cart_json.put("payment_method_title", "Direct Bank Transfer")
            cart_json.put("set_paid", false)
            cart_json.put("status", "processing")
            addfeelineshippingline()
            cart_json.put("currency", "INR".toUpperCase())
            cart_json.put("billing", setbillingaddress())
            cart_json.put("shipping", getshippingaddress())

            //   jsonParams.pu
            getcartlist()
            //  setmetadata()
            //  cart_json.put("line_items",getcartlist())
            System.out.println(cart_json.toString())


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun addfeelineshippingline() {
        var shipping_lines: JSONArray? = null
        var fee_lines: JSONArray? = null
        try {
            shipping_lines = JSONArray()
            val shipping_lines_obj = JSONObject()
            shipping_lines_obj.put("method_id", AppPreferenceHalper.read(PreferenceConstantParam.SHIPMETHOD_ID,""))
            shipping_lines_obj.put("method_title", AppPreferenceHalper.read(PreferenceConstantParam.SHIPMETHOD_TITLE,""))
            shipping_lines_obj.put("total", AppPreferenceHalper.read(PreferenceConstantParam.SHIPPINGCOST,"").toString())
            shipping_lines.put(shipping_lines_obj)
            cart_json.put("shipping_lines",shipping_lines)

            fee_lines=JSONArray()
            val fee_lines_0bj = JSONObject()
            fee_lines_0bj.put("name", AppPreferenceHalper.read(PreferenceConstantParam.FEE_PACKAGETITLE,""))
            fee_lines_0bj.put("tax_status", AppPreferenceHalper.read(PreferenceConstantParam.FEE_TAX_STATUS,""))
            fee_lines_0bj.put("total", AppPreferenceHalper.read(PreferenceConstantParam.PACKING_COST,"").toString())
            fee_lines.put(fee_lines_0bj)
            cart_json.put("fee_lines",fee_lines)


        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun setmetadata(totalprice1: Int) {

        var metajArray: JSONArray? = null
        try {
            metajArray = JSONArray()
            val meta_data1 = JSONObject()
            meta_data1.put("key", "sculptee_hid_cart_total")
            meta_data1.put("value", totalprice1)
            /*  val meta_data2 = JSONObject()
              meta_data2.put("key","sculptee_hid_withegg_per_pound")
              meta_data2.put("value",444)

              val meta_data3 = JSONObject()
              meta_data3.put("key","sculptee_hid_eggless_per_pound")
              meta_data3.put("value",155)*/

            val meta_data3 = JSONObject()
            meta_data3.put("key","_shipping_phone")
            meta_data3.put("value",placeorderViewBind?.et_ship_phone?.text.toString())


            metajArray?.put(meta_data1)
             metajArray?.put(meta_data3)
            //metajArray?.put(meta_data1)
            cart_json.put("meta_data", metajArray)


        } catch (e: JSONException) {

        }
    }

    private fun getshippingaddress(): JSONObject? {
        val shipping = JSONObject()
        try {
            shipping.put("first_name", placeorderViewBind?.et_ship_fristname?.text.toString())
            shipping.put("last_name", placeorderViewBind?.et_ship_last_name?.text.toString())
          //  shipping.put("company", placeorderViewBind?.et_ship_company_name?.text.toString())
            shipping.put("address_1", placeorderViewBind?.et_ship_streetaddress1?.text.toString())
            shipping.put("address_2", placeorderViewBind?.et_ship_streetaddress2?.text.toString())
            shipping.put("city", placeorderViewBind?.et_ship_town?.text.toString())
         //   shipping.put("state", placeorderViewBind?.et_ship_state?.text.toString())
            shipping.put("state", "WB")
            shipping.put("email",placeorderViewBind?.et_ship_company_name!!.text.toString())

            shipping.put("postcode", placeorderViewBind?.et_ship_post?.text.toString())
           // shipping.put("country", placeorderViewBind?.et_ship_country?.text.toString())
            shipping.put("country", "IN")

            /* shipping.put("email","")
             shipping.put("phone","")*/


        } catch (e: JSONException) {
            e.printStackTrace()
            return shipping
        }
        return shipping
    }

    private fun setbillingaddress(): JSONObject {
        val billingobj = JSONObject()
        try {
            billingobj.put("first_name", placeorderViewBind?.et_fristname?.text.toString())
            billingobj.put("last_name", placeorderViewBind?.et_last_name?.text.toString())
           // billingobj.put("company", placeorderViewBind?.et_company_name?.text.toString())
            billingobj.put("address_1", placeorderViewBind?.et_streetaddress1?.text.toString())
            billingobj.put("address_2", placeorderViewBind?.et_streetaddress2?.text.toString())
            billingobj.put("city", placeorderViewBind?.et_town?.text.toString())
          //  billingobj.put("state", placeorderViewBind?.et_state?.text.toString())
            billingobj.put("state","WB")

            billingobj.put("postcode", placeorderViewBind?.et_post?.text.toString())
          //  billingobj.put("country", placeorderViewBind?.et_country?.text.toString())

            billingobj.put("country","IN")
            billingobj.put("email", placeorderViewBind?.et_billemail_name?.text.toString())
            billingobj.put("phone", placeorderViewBind?.et_phone?.text.toString())


        } catch (e: JSONException) {
            e.printStackTrace()
            return billingobj
        }
        return billingobj
    }

    fun callorderApi() {
        /* val progress= ProgressDialog(this)
         progress?.setMessage("loading..")
         progress?.show()*/
        val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface =
            RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)
        val JSON = MediaType.parse("application/json; charset=utf-8")
        // put your json here
        var obj: JSONObject = cart_json
        var jsonParser: JsonParser = JsonParser()
        var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
        //  val token:String="Bearer "+ AppPreferenceHalper.read(PreferenceConstantParam.TOKEN,"")
        val callapi = apiInterface?.createorder(gsonObject)
        callapi?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                // if (progress.isShowing)
                //   progress.dismiss()
                customProgress.hideProgress()
                if(response.isSuccessful) {
                    val jsonObj: JSONObject = JSONObject(response.body()?.string())
                    var orderid: Int = jsonObj.optInt("id")
                    AppPreferenceHalper.write(PreferenceConstantParam.ORDERID, orderid)
                    var order_key: String = jsonObj.optString("order_key")
                    val ordr_product=AppPreferenceHalper.read(PreferenceConstantParam.ORDER_IDS,"")+order_product_ids
                    AppPreferenceHalper.write(PreferenceConstantParam.ORDER_IDS,ordr_product!!)
                    payment()
                   doAsync {
                        mDb.CakeProductDao().deletecartrecord()
                    }

                }else{
                    Alert.showalert(this@PlaceOrderActivity,"Something wrong.Try again later")
                }
               // Toast.makeText(this@PlaceOrderActivity,"Your order Submitted. Please Make Payment.",Toast.LENGTH_LONG).show()

                /*  doAsync {
                      mDb.CakeProductDao().deletecartrecord()
                      val intent: Intent = Intent(this@PlaceOrderActivity, CartActivity::class.java)
                      startActivity(intent)
                      finish()
                  }*/


            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // if (progress.isShowing)
                //    progress.dismiss()
                customProgress.hideProgress()
            }
        })

    }

    fun callApifropincodechecking() {
        if(ConnectionDetector.isConnectingToInternet(this)) {
            val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
            customProgress.showProgress(this, "Please Wait..", false)
            val apiInterface =
                RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)
            val callapi =
                apiInterface?.checkpostcode(placeorderViewBind?.et_ship_post?.text.toString())
            callapi?.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    customProgress.hideProgress()
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    customProgress.hideProgress()
                    val jsonObj: JSONObject = JSONObject(response.body()?.string())
                    if (jsonObj.getString("is_exists").equals("true")) {
                        craetecartlistorder()
                        if (Addressselection == 1)
                            saveshippingaddresshome()
                        else
                            saveaddressoffice()
                    } else {
                        Alert.showalert(
                            this@PlaceOrderActivity,
                            "This order is not available in this pin code"
                        )
                        placeorderViewBind!!.et_ship_post!!.requestFocus()
                    }

                }
            })
        }else
            Alert.showalert(this!!,this!!.resources.getString(R.string.nointernet))

    }

    public fun saveaddressoffice() {
        AppPreferenceHalper.write(PreferenceConstantParam.O_F_NAME,placeorderViewBind?.et_ship_fristname?.text.toString())
        AppPreferenceHalper.write(PreferenceConstantParam.O_L_NAME,placeorderViewBind?.et_ship_last_name?.text.toString())
        AppPreferenceHalper.write(PreferenceConstantParam.O_MOB,placeorderViewBind?.et_ship_phone?.text.toString())
        AppPreferenceHalper.write(PreferenceConstantParam.O_STREET1,placeorderViewBind?.et_ship_streetaddress1?.text.toString())
        AppPreferenceHalper.write(PreferenceConstantParam.O_STREET2,placeorderViewBind?.et_ship_streetaddress2?.text.toString())
        AppPreferenceHalper.write(PreferenceConstantParam.O_ZIP,placeorderViewBind?.et_ship_post?.text.toString())
        AppPreferenceHalper.write(PreferenceConstantParam.O_COMPANY,placeorderViewBind?.et_ship_company_name?.text.toString())


    }

    public fun saveshippingaddresshome() {

        AppPreferenceHalper.write(PreferenceConstantParam.H_F_NAME,placeorderViewBind?.et_ship_fristname?.text.toString())
        AppPreferenceHalper.write(PreferenceConstantParam.H_L_NAME,placeorderViewBind?.et_ship_last_name?.text.toString())
        AppPreferenceHalper.write(PreferenceConstantParam.H_MOB,placeorderViewBind?.et_ship_phone?.text.toString())
        AppPreferenceHalper.write(PreferenceConstantParam.H_STREET1,placeorderViewBind?.et_ship_streetaddress1?.text.toString())
        AppPreferenceHalper.write(PreferenceConstantParam.H_STREET2,placeorderViewBind?.et_ship_streetaddress2?.text.toString())
        AppPreferenceHalper.write(PreferenceConstantParam.H_ZIP,placeorderViewBind?.et_ship_post?.text.toString())
        AppPreferenceHalper.write(PreferenceConstantParam.H_COMPANY,placeorderViewBind?.et_ship_company_name?.text.toString())



    }

    fun payment() {

        val payUmoneyConfig = PayUmoneyConfig.getInstance()
        //Use this to set your custom text on result screen button
        payUmoneyConfig.doneButtonText = "Done "
        //Use this to set your custom title for the activity
        payUmoneyConfig.payUmoneyActivityTitle = "Payment"
        payUmoneyConfig.disableExitConfirmation(false)
        val builder = PayUmoneySdkInitializer.PaymentParam.Builder()
        var amount = 0.0
        try {
            amount = java.lang.Double.parseDouble(totalprice.toString())

        } catch (e: Exception) {
            e.printStackTrace()
        }

        val txnId = System.currentTimeMillis().toString() + ""
        //String txnId = "TXNID720431525261327973";
        val phone = placeorderViewBind!!.et_ship_phone!!.text.toString()
        val productName = resources.getString(R.string.app_name)
        val firstName =placeorderViewBind!!.et_fristname!!.text.toString()
        var email:String?=""
        if (AppPreferenceHalper.read(PreferenceConstantParam.isLogIn, false) == true) {
             email = AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_EMAIL, "")
        }else{
            email= placeorderViewBind?.et_ship_company_name!!.text.toString()
        }
        val totalamount = AppPreferenceHalper.read(PreferenceConstantParam.PAYVAL, "")
        val udf1 = ""
        val udf2 = ""
        val udf3 = ""
        val udf4 = ""
        val udf5 = ""
        val udf6 = ""
        val udf7 = ""
        val udf8 = ""
        val udf9 = ""
        val udf10 = ""

      //  val amountaftership_packag:Float= (totalamount!!.toFloat()+AppPreferenceHalper.read(PreferenceConstantParam.SHIPPINGCOST,"")!!.toInt()!!+ AppPreferenceHalper.read(PreferenceConstantParam.PACKING_COST,"")!!.toInt()!!)
       // val gst:Float=(totalamount.toFloat()*AppPreferenceHalper.read(PreferenceConstantParam.ORDERGST,"")!!.toFloat())/100
     //  AppPreferenceHalper.write(PreferenceConstantParam.PAY_AMOUNT,String.format("%.02f", amountaftership_packag+gst))
       // builder.setAmount( String.format("%.02f", amountaftership_packag+gst))
       val  payval=AppPreferenceHalper.read(PreferenceConstantParam.PAYVAL,"0.00")
        builder.setAmount("1")
            .setTxnId(txnId)
            .setPhone(phone)
            .setProductName(productName)
            .setFirstName(firstName)
            .setEmail(email)
            .setsUrl(Constants.SURL)
            .setfUrl(Constants.FURL)
            .setUdf1(udf1)
            .setUdf2(udf2)
            .setUdf3(udf3)
            .setUdf4(udf4)
            .setUdf5(udf5)
            .setUdf6(udf6)
            .setUdf7(udf7)
            .setUdf8(udf8)
            .setUdf9(udf9)
            .setUdf10(udf10)
            .setIsDebug(true)
            .setKey(AppConstants.MARCHENTKEY)
            .setMerchantId(AppConstants.MARCHENTID)

        try {
            mPaymentParams = builder.build()
          //  generateHashFromServer(mPaymentParams!!);
            /*
            * Hash should always be generated from your server side.
            * */
            //    generateHashFromServer(mPaymentParams);


            mPaymentParams = calculateServerSideHashAndInitiatePayment1(mPaymentParams!!)
          //  generateHashFromServer(mPaymentParams)
               PayUmoneyFlowManager.startPayUMoneyFlow(
                    mPaymentParams, this@PlaceOrderActivity, R.style.AppTheme, false
                )


        } catch (e: Exception) {
            // some exception occurred
            Alert.showalert(this, e.message.toString())

        }


        /* val payUmoneyConfig = PayUmoneyConfig.getInstance()
        payUmoneyConfig.payUmoneyActivityTitle = "Buy" + "giugkhgkj"
        payUmoneyConfig.doneButtonText = "Pay " + "8787"

        val builder = PayUmoneySdkInitializer.PaymentParam.Builder()
        builder.setAmount("44444")
            .setTxnId(System.currentTimeMillis().toString() + "")
            .setPhone(Constants.MOBILE)
            .setProductName(" gsdg gDGF")
            .setFirstName(Constants.FIRST_NAME)
            .setEmail(Constants.EMAIL)
            .setsUrl(Constants.SURL)
            .setfUrl(Constants.FURL)
            .setUdf1("")
            .setUdf2("")
            .setUdf3("")
            .setUdf4("")
            .setUdf5("")
            .setUdf6("")
            .setUdf7("")
            .setUdf8("")
            .setUdf9("")
            .setUdf10("")
            .setIsDebug(Constants.DEBUG)
            .setKey(Constants.MERCHANT_KEY)
            .setMerchantId(Constants.MERCHANT_ID)

        try {
            val mPaymentParams = builder.build()
            calculateHashInServer(mPaymentParams)
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()

        }*/

    }

    /*private fun calculateHashInServer(mPaymentParams: PayUmoneySdkInitializer.PaymentParam?) {
        val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val url = Constants.MONEY_HASH
        val request = object : StringRequest(
            Request.Method.POST, url,

            com.android.volley.Response.Listener { response ->
                customProgress.hideProgress()
                var merchantHash = ""

                try {
                    val jsonObject = JSONObject(response)
                    merchantHash = jsonObject.getString("result")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }



                if (merchantHash.isEmpty() || merchantHash == "") {
                    Toast.makeText(this@PlaceOrderActivity, "Could not generate hash", Toast.LENGTH_SHORT).show()
                } else {
                    mPaymentParams!!.setMerchantHash(merchantHash)
                    PayUmoneyFlowManager.startPayUMoneyFlow(
                        mPaymentParams,
                        this@PlaceOrderActivity, R.style.AppTheme, true
                    )
                }
            },

            com.android.volley.Response.ErrorListener { error ->

                if (error is NoConnectionError) {
                    Toast.makeText(
                        this@PlaceOrderActivity,
                        "Connect to internet Volley",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(this@PlaceOrderActivity, error.message, Toast.LENGTH_SHORT).show()
                }
                customProgress.hideProgress()
            }) {
            override fun getParams(): Map<String, String> {
                return mPaymentParams!!.getParams()
            }
        }
        Volley.newRequestQueue(this).add(request)

    }*/

    private fun convertStringToDouble(str: String): Double {
        return java.lang.Double.parseDouble(str)
    }

    private fun calculateServerSideHashAndInitiatePayment1(mPaymentParams: PayUmoneySdkInitializer.PaymentParam?): PayUmoneySdkInitializer.PaymentParam? {
        val stringBuilder = StringBuilder()
        val params = mPaymentParams!!.getParams()
        stringBuilder.append(params.get(PayUmoneyConstants.KEY) + "|")
        stringBuilder.append(params.get(PayUmoneyConstants.TXNID) + "|")
        stringBuilder.append(params.get(PayUmoneyConstants.AMOUNT) + "|")
        stringBuilder.append(params.get(PayUmoneyConstants.PRODUCT_INFO) + "|")
        stringBuilder.append(params.get(PayUmoneyConstants.FIRSTNAME) + "|")
        stringBuilder.append(params.get(PayUmoneyConstants.EMAIL) + "|")
        stringBuilder.append(params.get(PayUmoneyConstants.UDF1) + "|")
        stringBuilder.append(params.get(PayUmoneyConstants.UDF2) + "|")
        stringBuilder.append(params.get(PayUmoneyConstants.UDF3) + "|")
        stringBuilder.append(params.get(PayUmoneyConstants.UDF4) + "|")
        stringBuilder.append(params.get(PayUmoneyConstants.UDF5) + "||||||")
        stringBuilder.append(AppConstants.MARCHENTSALT)

        val hash = hashCal(stringBuilder.toString())
        mPaymentParams!!.setMerchantHash(hash)

        return mPaymentParams
    }

    private fun hashCal(toString: String): String? {
        val hashseq = toString.toByteArray()
        val hexString = StringBuilder()
        try {
            val algorithm = MessageDigest.getInstance("SHA-512")
            algorithm.reset()
            algorithm.update(hashseq)
            val messageDigest = algorithm.digest()
            for (aMessageDigest in messageDigest) {
                val hex = Integer.toHexString(0xFF and aMessageDigest.toInt())
                if (hex.length == 1) {
                    hexString.append("0")
                }
                hexString.append(hex)
            }
        } catch (ignored: NoSuchAlgorithmException) {
        }

        return hexString.toString()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data != null) {

            val transactionResponse =
                data.getParcelableExtra<TransactionResponse>(PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE)
            val resultModel = data.getParcelableExtra<ResultModel>(PayUmoneyFlowManager.ARG_RESULT)

            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {

                if (transactionResponse.transactionStatus == TransactionResponse.TransactionStatus.SUCCESSFUL) {
                   // showAlert("Payment Successful")
                    customProgress.showProgress(this, "Please Wait..", false)
                    doAsync {
                        val  payuResponse:String = transactionResponse.getPayuResponse();
                      // Response from SURl and FURL
                        val merchantResponse:String = transactionResponse.getTransactionDetails();
                        System.out.print(merchantResponse)
                        Alert.showalertforPaymentSucess(this@PlaceOrderActivity,"Order successfully Submitted.",payuResponse)
                       // mDb.CakeProductDao().deletecartrecord()
                       // callapiforpaymentsucesstranscation(payuResponse)
                    }

                } else if (transactionResponse.transactionStatus == TransactionResponse.TransactionStatus.CANCELLED) {
                    Alert.showalert(this,"Payment Cancelled.")
                } else if (transactionResponse.transactionStatus == TransactionResponse.TransactionStatus.FAILED) {
                    Alert.showalert(this,"Payment Failed.")
                }

            } else if (resultModel != null && resultModel.error != null) {
                Toast.makeText(this, "Error check log.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Both objects are null.", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_CANCELED) {
            Alert.showalert(this,"Payment Cancelled.")

        }
    }

    public fun callapiforpaymentsucesstranscation(payuResponse: String) {
        if(ConnectionDetector.isConnectingToInternet(this)) {
          //  val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
         //   customProgress.showProgress(this, "Please Wait..", false)
            val apiInterface = RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)
            var jobj:JSONObject?=null
            var jpbojpaymentresponse:JSONObject?=null
            var metadataarray:JSONArray?=null
            var meta_paymentpart:JSONObject?=null
            var partjsonarray:JSONArray?=null
            var partjsonobj:JSONObject?=null
            var metapaymenthistory:JSONObject?=null

            try {
                jobj =JSONObject();
               // jpbojpaymentresponse=JSONObject()
                metadataarray=JSONArray()
                meta_paymentpart=JSONObject()
                partjsonarray=JSONArray()
                partjsonobj=JSONObject()
                metapaymenthistory=JSONObject()
                 jpbojpaymentresponse=JSONObject(payuResponse)
                 var result:JSONObject= jpbojpaymentresponse?.get("result") as JSONObject
                 var txnid=result.get("txnid")

                 jobj.put("status","processing")
                 jobj.put("transaction_id",txnid)
                  var tsLong:Long = System.currentTimeMillis()/1000;
                  val ts:String = tsLong.toString();
                meta_paymentpart!!.put("index","1")
                partjsonobj!!.put("amount",AppPreferenceHalper.read(PreferenceConstantParam.PAY_AMOUNT,""))
                meta_paymentpart!!.put("date",ts)
                partjsonarray!!.put(partjsonobj)
                meta_paymentpart!!.put("value",partjsonarray)

                metadataarray!!.put(meta_paymentpart)
                metapaymenthistory!!.put("key","sc_payment_history")
                metapaymenthistory.put("value",jpbojpaymentresponse)
                metadataarray!!.put(metapaymenthistory)
                jobj.put("meta_data",metadataarray)
             //   val callapi = apiInterface?.updatePaymentinfp(jobj)
                var obj: JSONObject = jobj
                var jsonParser: JsonParser = JsonParser()
                var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
                val callapi = apiInterface?.Updateorderinfo(AppPreferenceHalper.read(PreferenceConstantParam.ORDERID,"").toString()!!,gsonObject)
                callapi!!.enqueue(object: Callback<ResponseBody>{
                    override fun onResponse(
                        call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        customProgress.hideProgress()
                        if (response.isSuccessful) {

                            val intent: Intent = Intent(this@PlaceOrderActivity, HomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)
                            finish()
                        }else
                            Alert.showalert(this@PlaceOrderActivity,"Something Wrong.Try Again" )
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        customProgress.hideProgress()

                    }
                })

            } catch ( e:java.lang.Exception) {
                e.printStackTrace();
            }


        }


    }

    private fun  showAlert(msg: String) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setMessage(msg)
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton("Ok") {
                dialogInterface, i ->
                dialogInterface.dismiss()
                /* val intent: Intent = Intent(this@PlaceOrderActivity, HomeActivity::class.java)
                 intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
                  startActivity(intent)
                  finish()*/
        }
        alertDialog.show()
    }

    /* private fun calculateServerSideHashAndInitiatePayment1(paymentParam: PayUmoneySdkInitializer.PaymentParam): PayUmoneySdkInitializer.PaymentParam {
         val stringBuilder = StringBuilder()
         val params = paymentParam.params
         stringBuilder.append(params[PayUmoneyConstants.KEY] + "|")
         stringBuilder.append(params[PayUmoneyConstants.TXNID] + "|")
         stringBuilder.append(params[PayUmoneyConstants.AMOUNT] + "|")
         stringBuilder.append(params[PayUmoneyConstants.PRODUCT_INFO] + "|")
         stringBuilder.append(params[PayUmoneyConstants.FIRSTNAME] + "|")
         stringBuilder.append(params[PayUmoneyConstants.EMAIL] + "|")
         stringBuilder.append(params[PayUmoneyConstants.UDF1] + "|")
         stringBuilder.append(params[PayUmoneyConstants.UDF2] + "|")
         stringBuilder.append(params[PayUmoneyConstants.UDF3] + "|")
         stringBuilder.append(params[PayUmoneyConstants.UDF4] + "|")
         stringBuilder.append(params[PayUmoneyConstants.UDF5] + "||||||")
         stringBuilder.append(SANDBOX)

         val hash = hashCal(stringBuilder.toString())
         paymentParam.setMerchantHash(hash)

         return paymentParam
     }*/
    /*fun generateHashFromServer(paymentParam: PayUmoneySdkInitializer.PaymentParam) {
        //nextButton.setEnabled(false); // lets not allow the user to click the button again and again.

        val params = paymentParam.params!!

        // lets create the post params
        val postParamsBuffer = StringBuffer()
        postParamsBuffer.append(
            concatParams(
                PayUmoneyConstants.KEY,
                params[PayUmoneyConstants.KEY]!!
            )
        )
        postParamsBuffer.append(
            concatParams(
                PayUmoneyConstants.AMOUNT,
                params[PayUmoneyConstants.AMOUNT]!!
            )
        )
        postParamsBuffer.append(
            concatParams(
                PayUmoneyConstants.TXNID,
                params[PayUmoneyConstants.TXNID]!!
            )
        )
        postParamsBuffer.append(
            concatParams(
                PayUmoneyConstants.EMAIL,
                params[PayUmoneyConstants.EMAIL]!!
            )
        )
        postParamsBuffer.append(
            concatParams(
                "productinfo",
                params[PayUmoneyConstants.PRODUCT_INFO]!!
            )
        )
        postParamsBuffer.append(concatParams("firstname", params[PayUmoneyConstants.FIRSTNAME]!!))
        postParamsBuffer.append(
            concatParams(
                PayUmoneyConstants.UDF1,
                params[PayUmoneyConstants.UDF1]!!
            )
        )
        postParamsBuffer.append(
            concatParams(
                PayUmoneyConstants.UDF2,
                params[PayUmoneyConstants.UDF2]!!
            )
        )
        postParamsBuffer.append(
            concatParams(
                PayUmoneyConstants.UDF3,
                params[PayUmoneyConstants.UDF3]!!
            )
        )
        postParamsBuffer.append(
            concatParams(
                PayUmoneyConstants.UDF4,
                params[PayUmoneyConstants.UDF4]!!
            )
        )
        postParamsBuffer.append(
            concatParams(
                PayUmoneyConstants.UDF5,
                params[PayUmoneyConstants.UDF5]!!
            )
        )

        val postParams =
            if (postParamsBuffer[postParamsBuffer.length - 1] == '&') postParamsBuffer.substring(
                0,
                postParamsBuffer.length - 1
            ).toString() else postParamsBuffer.toString()

        // lets make an api call
        val getHashesFromServerTask = GetHashesFromServerTask()
        getHashesFromServerTask.execute(postParams)
    }*/

  /*  private inner class GetHashesFromServerTask : AsyncTask<String, String, String>() {
        private var progressDialog: ProgressDialog? = null

        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog = ProgressDialog(this@PlaceOrderActivity)
            progressDialog!!.setMessage("Please wait...")
            progressDialog!!.show()
        }

        override fun doInBackground(vararg postParams: String): String {

            var merchantHash = ""
            try {
                //TODO Below url is just for testing purpose, merchant needs to replace this with their server side hash generation url
                val url = URL("https://payu.herokuapp.com/get_hash")

                val postParam = postParams[0]

                val postParamsByte = postParam.toByteArray(charset("UTF-8"))

                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                conn.setRequestProperty("Content-Length", postParamsByte.size.toString())
                conn.doOutput = true
                conn.outputStream.write(postParamsByte)

                val responseInputStream = conn.inputStream
                val responseStringBuffer = StringBuffer()
                val byteContainer = ByteArray(1024)
                var i: Int=0
                while (responseInputStream.read(byteContainer)!=-1) {
                    responseStringBuffer.append(String(byteContainer, 0, i))
                }

                val response = JSONObject(responseStringBuffer.toString())

                val payuHashIterator = response.keys()
                while (payuHashIterator.hasNext()) {
                    val key = payuHashIterator.next()
                    when (key) {
                        *//**
                         * This hash is mandatory and needs to be generated from merchant's server side
                         *
                         *//*
                        "payment_hash" -> merchantHash = response.getString(key)
                        else -> {
                        }
                    }
                }

            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: ProtocolException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return merchantHash
        }

        override fun onPostExecute(merchantHash: String) {
            super.onPostExecute(merchantHash)

            progressDialog!!.dismiss()


            if (merchantHash.isEmpty() || merchantHash == "") {
                Toast.makeText(this@PlaceOrderActivity, "Could not generate hash", Toast.LENGTH_SHORT)
                    .show()
            } else {
                mPaymentParams!!.setMerchantHash(merchantHash)


                    PayUmoneyFlowManager.startPayUMoneyFlow(
                        mPaymentParams,
                        this@PlaceOrderActivity,
                      R.style.AppTheme,
                        true
                    )

            }
        }
    }*/

    protected fun concatParams(key: String, value: String): String {
        return "$key=$value&"
    }

  /*  fun generateHashFromServer(paymentParam: PayUmoneySdkInitializer.PaymentParam) {
        //nextButton.setEnabled(false); // lets not allow the user to click the button again and again.

        val params = paymentParam.params

        // lets create the post params
        val postParamsBuffer = StringBuffer()
        postParamsBuffer.append(
            concatParams(PayUmoneyConstants.KEY, params[PayUmoneyConstants.KEY]!!)
        )
        postParamsBuffer.append(
            concatParams(
                PayUmoneyConstants.AMOUNT, params[PayUmoneyConstants.AMOUNT]!!)
        )
        postParamsBuffer.append(
            concatParams(
                PayUmoneyConstants.TXNID,
                params[PayUmoneyConstants.TXNID]!!
            )
        )
        postParamsBuffer.append(
            concatParams(
                PayUmoneyConstants.EMAIL,
                params[PayUmoneyConstants.EMAIL]!!
            )
        )
        postParamsBuffer.append(
            concatParams(
                "productinfo",
                params[PayUmoneyConstants.PRODUCT_INFO]!!
            )
        )
        postParamsBuffer.append(concatParams("firstname", params[PayUmoneyConstants.FIRSTNAME]!!))
        postParamsBuffer.append(
            concatParams(
                PayUmoneyConstants.UDF1,
                params[PayUmoneyConstants.UDF1]!!
            )
        )
        postParamsBuffer.append(
            concatParams(
                PayUmoneyConstants.UDF2,
                params[PayUmoneyConstants.UDF2]!!
            )
        )
        postParamsBuffer.append(
            concatParams(
                PayUmoneyConstants.UDF3,
                params[PayUmoneyConstants.UDF3]!!
            )
        )
        postParamsBuffer.append(
            concatParams(
                PayUmoneyConstants.UDF4,
                params[PayUmoneyConstants.UDF4]!!
            )
        )
        postParamsBuffer.append(
            concatParams(
                PayUmoneyConstants.UDF5,
                params[PayUmoneyConstants.UDF5]!!
            )
        )

        val postParams =
            if (postParamsBuffer[postParamsBuffer.length - 1] == '&') postParamsBuffer.substring(
                0,
                postParamsBuffer.length - 1
            ).toString() else postParamsBuffer.toString()

        // lets make an api call
        val getHashesFromServerTask = GetHashesFromServerTask()
        getHashesFromServerTask.execute(postParams)
    }
*/


}