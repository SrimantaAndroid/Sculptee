package com.sculptee.screens.cart

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sculptee.R
import com.sculptee.datastorage.CakeProduct
import com.sculptee.datastorage.RoomSingleton
import com.sculptee.network.ApiInterface
import com.sculptee.network.RetroFitApiService
import com.sculptee.screens.cart.adapter.CartListAdapter
import com.sculptee.utils.Alert
import com.sculptee.utils.bottomsheet.BottomSheetReclyScroll
import com.sculptee.utils.bottomsheet.BottomSheetReclyScrollweight
import com.sculptee.utils.bottomsheet.BottomSheetweight
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.sculptee.utils.preferenceconstant.PreferenceConstantParam
import com.sculptee.utils.sheardpreference.AppPreferenceHalper
import okhttp3.ResponseBody
import org.jetbrains.anko.doAsync
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat


class CartActivity:AppCompatActivity(),BottomSheetReclyScroll.Bottomsheetlistner,BottomSheetReclyScrollweight.Bottomsheetqeightlistner {



    var cartListViewBind:CartListViewBind?=null
     public  var cartListAdapter:CartListAdapter?=null
    var cartOnClick:CartOnClick?=null
    public  lateinit var mDb:RoomSingleton
    public var cartlist= ArrayList<CakeProduct>()
    var coupondiscount:Float=0F;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view:View=LayoutInflater.from(this).inflate(R.layout.activity_cart_layout,null)
        cartListViewBind= CartListViewBind(this,view)
        cartOnClick= CartOnClick(cartListViewBind!!,this)
        mDb = RoomSingleton.getInstance(applicationContext)
        setContentView(view)
        setadapter()
        getcartlist();
        callvalueforshipandgstvalue()
      //  updateprice()
       // callapiforcatlist()
      //  gettasklist()

    }

    override fun onweightClick(text: String, postition: Int) {
        cartlist.get(postition).weight=text.toInt()
        cartlist.get(postition).product_total_price= (cartlist.get(postition).weight*cartlist.get(postition).product_price)*cartlist.get(postition).quentity
        cartListAdapter!!.notifyDataSetChanged()
        showtotalprice()
        doAsync {
            // cartActivity.mDb.CakeProductDao().update(cartlist.get(position).product_id, holder.tv_count.text.toString().toInt())
            mDb.CakeProductDao().upadteprice(cartlist.get(postition).product_id, text.toInt() )
        }

    }
    override fun onOptionClick(text: String, postition: Int) {
        cartlist.get(postition).quentity=text.toInt()
        cartlist.get(postition).product_total_price= (cartlist.get(postition).weight*cartlist.get(postition).product_price)*cartlist.get(postition).quentity
        cartListAdapter!!.notifyDataSetChanged()
        showtotalprice()

        doAsync {
            // cartActivity.mDb.CakeProductDao().update(cartlist.get(position).product_id, holder.tv_count.text.toString().toInt())
            mDb.CakeProductDao().upadtequentity(cartlist.get(postition).product_id,text.toInt() )
        }
    }
    private fun callapiforcatlist() {
        val progress= ProgressDialog(this)
        progress?.setMessage("Please Wait...")
        progress?.show()
        val apiInterface= RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)
        val token:String="Bearer "+ AppPreferenceHalper.read(PreferenceConstantParam.TOKEN,"")
        val callapi =apiInterface?.getcartlist(token)
        callapi?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (progress.isShowing)
                    progress.dismiss()

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                if (progress.isShowing)
                    progress.dismiss()
            }
        })
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


                        if(cartlist.size>0) {
                            calculateprice()
                            showtotalprice()
                        }


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

    private fun calculateprice() {
        cartListViewBind!!.tv_postagepackval?.setText(String.format("%.02f",  AppPreferenceHalper.read(PreferenceConstantParam.PACKING_COST,"")!!.toFloat()!!))
        cartListViewBind!!.tv_shippingcostval?.setText(String.format("%.02f",  AppPreferenceHalper.read(PreferenceConstantParam.SHIPPINGCOST,"")!!.toFloat()!!))
        cartListViewBind!!.tv_tax?.setText("Tax GST("+String.format("%.02f",  AppPreferenceHalper.read(PreferenceConstantParam.ORDERGST,"")!!.toFloat())+"%) : ")
    }

    private fun getcartlist(){
        doAsync {
            val list = mDb.CakeProductDao().getcatlist(AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_ID,"0")!!)
            if (list.size > 0) {
                for (CakeProduct in list) {
                    val cakeProduct = CakeProduct(
                        CakeProduct.id,
                        CakeProduct.product_id,
                        CakeProduct.userid,
                        CakeProduct.product_name,
                        CakeProduct.message_on_cake,
                        CakeProduct.imgurl,
                        CakeProduct.product_price,
                        CakeProduct.flovour,
                        CakeProduct.product_type,
                        CakeProduct.weight,
                        CakeProduct.product_price*CakeProduct.weight,
                        CakeProduct.variation_id,
                        CakeProduct.variation_name,
                        CakeProduct.withegg_per_pound,
                        CakeProduct.eggless_per_pound,
                        CakeProduct.quentity

                    )
                    cartlist.add(cakeProduct)
                }
               // showtotalprice()
               // updateprice()
                cartListAdapter?.notifyDataSetChanged()
            }else{
                runOnUiThread {
                    //showalert()
                    cartlist.clear()
                    cartListAdapter?.notifyDataSetChanged()
                    Alert.showalertnocartitem(this@CartActivity,"No cart Item found.")
                }

            }
        }
    }
    fun showalert() {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(resources.getString(R.string.app_name))
        alertDialog.setMessage("No more cart-item.")
        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, "OK",
            DialogInterface.OnClickListener { dialog, which -> dialog.dismiss()
            finish()})
        alertDialog.show()
    }

    private fun setadapter() {
        cartListAdapter= CartListAdapter(this,cartlist)
        cartListViewBind?.rec_cart_list?.layoutManager=LinearLayoutManager(this)
        cartListViewBind?.rec_cart_list?.adapter=cartListAdapter
    }

    fun callapiforapplycoupon() {
       val customProgressDialog:CustomProgressDialog= CustomProgressDialog().getInstance()
        customProgressDialog.showProgress(this,resources.getString(R.string.pleasewait),false)
        val apiInterface= RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)

        val callapi =apiInterface?.getcoupandiscount(cartListViewBind!!.et_coupon!!.text.toString(), AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_ID, "")!!)
        callapi?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful) {
                    customProgressDialog.hideProgress()
                    val jsarray: JSONArray = JSONArray(response.body()?.string())
                    val jobj: JSONObject = jsarray.getJSONObject(0)
                    if (jobj.getString("exe_status").equals("success")) {
                        if (jobj.getString("curr_user_is_eligible").toBoolean() == true) {
                            val discount_type = jobj.getString("discount_type")
                            val amount: Float = jobj.getString("amount").toFloat()
                            val minimum_amount = jobj.getString("minimum_amount")

                            var totalprice: Int? = 0
                            var productcount: Int? = 0
                            for (i in 0 until cartlist?.size!!) {
                                totalprice = totalprice!! + cartlist?.get(i)?.product_total_price!!
                                productcount = productcount!! + 1
                                //  totalprice=totalprice!!+ cartlist.get(i).product_price*cartlist.get(i).weight
                            }
                            val calculatedpercentage: Float
                            if (discount_type.equals("percent")) {
                                coupondiscount = ((amount * totalprice!!) / 100).toFloat()
                                showtotalprice()
                                // cartListViewBind?.tv_subtotalval?.setText( String.format("%.02f",(totalprice-calculatedpercentage).toFloat()))
                                // cartListViewBind?.tv_total_price?.setText(String.format("%.02f",(totalprice-calculatedpercentage).toFloat()))
                            } else if (discount_type.equals("fixed_cart")) {
                                coupondiscount=amount!!.toFloat()
                                showtotalprice()
                               // totalprice = totalprice!! - amount!!.toInt()
                                //  cartListViewBind?.tv_subtotalval?.setText( DecimalFormat("#####.##").format(totalprice))
                                //  cartListViewBind?.tv_total_price?.setText(DecimalFormat("#####.##").format(totalprice))

                            } else if (discount_type.equals("fixed_product")) {
                               // val p: Int = (productcount!! * amount).toInt()
                                coupondiscount=(productcount!! * amount).toFloat()
                                showtotalprice()
                                // cartListViewBind?.tv_subtotalval?.setText( DecimalFormat("#####.##").format(totalprice!! -p))
                                //cartListViewBind?.tv_total_price?.setText(DecimalFormat("#####.##").format(totalprice!!-p))
                            }

                            Alert.showalert(this@CartActivity, "Coupon Successfully applied.")

                            //  AppPreferenceHalper.write(PreferenceConstantParam.TOTALVAL,totalprice!!)
                            // val totalpricepayprice=totalprice/2
                            // DecimalFormat("#####.##").format(totalprice)
                            //cartListViewBind?.tv_total_price?.setText(DecimalFormat("#####.##").format(calculatedpercentage))
                        }

                    }else
                        Alert.showalert(this@CartActivity,"Please enter a valid coupon-code")
                }else
                    Alert.showalert(this@CartActivity,"Something wrong. Try again")

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                customProgressDialog.hideProgress()
            }
        })
    }

    /*private fun gettasklist() {
        class GetTaskList: AsyncTask<Void, Void, List<Product>>(){
            override fun doInBackground(vararg p0: Void?): List<Product> {
                var   taskList:List<Product>?=null
                *//*   taskList?.let {
                       DataBaseClient.getInstance(applicationContext).getAppDatabase()
                           .taskDao()
                           .getAll()*//*

                // }
                taskList=DataBaseClient.getInstance(applicationContext).getAppDatabase().productDao().getAll()
                return taskList!!
            }

            override fun onPostExecute(result: List<Product>?) {
                super.onPostExecute(result)
                System.out.println("ASN"+result?.size)
            }
        }

        val gt = GetTaskList()
        gt.execute()
    }*/

    public fun showtotalprice(){
        if (cartlist?.size>0) {
            val pricerangevalue: Int = cartListViewBind!!.rangeSeekbar!!.getValue();
            var totalprice: Int? = 0
            for (i in 0 until cartlist?.size!!) {
                //  totalprice=cartlist.get(i).product_price*cartlist.get(i).weight
                totalprice = totalprice!! + cartlist.get(i).product_total_price
            }


            val  gestwithshippingbilling=(totalprice!!+AppPreferenceHalper.read(PreferenceConstantParam.PACKING_COST,"")!!.toFloat()!!+
                   AppPreferenceHalper.read(PreferenceConstantParam.SHIPPINGCOST,"")!!.toFloat()!! )
             val calculatetex:Float=(gestwithshippingbilling*AppPreferenceHalper.read(PreferenceConstantParam.ORDERGST,"")!!.toFloat()!!)/100

            // val totalpricepayprice=totalprice/2
          //  val calculatetex: Float = (totalprice!! * AppPreferenceHalper.read(PreferenceConstantParam.ORDERGST, "")!!.toFloat()) / 100

            cartListViewBind?.tv_itemval?.setText(String.format("%.02f", totalprice.toFloat()))
            cartListViewBind?.tv_gstval?.setText(String.format("%.02f", calculatetex))

            val calculatedval: Int = (totalprice + AppPreferenceHalper.read(PreferenceConstantParam.PACKING_COST, "")!!.toFloat() + AppPreferenceHalper.read(PreferenceConstantParam.SHIPPINGCOST, "")!!.toFloat() + calculatetex).toInt()
            cartListViewBind?.tv_subtotalval?.setText(String.format("%.02f", (calculatedval - coupondiscount).toFloat()))
            val calculatedpercentage: Float = ((pricerangevalue * calculatedval!!) / 100).toFloat()
            cartListViewBind?.tv_total_price?.setText(String.format("%.02f", (calculatedval - (calculatedpercentage + coupondiscount)).toFloat())
            )
            //cartListViewBind?.tv_total_price?.setText(DecimalFormat("#####.##").format(calculatedpercentage))
            cartListViewBind?.tv_advanceval?.setText(String.format("%.02f", calculatedpercentage))
            AppPreferenceHalper.write(
                PreferenceConstantParam.TOTALVAL,
                calculatedpercentage.toString()!!
            )
        }else{
            cartListViewBind!!.tv_postagepackval?.setText(String.format("%.02f", "0.00".toFloat()))
            cartListViewBind!!.tv_shippingcostval?.setText(String.format("%.02f", "0.00".toFloat()))
            cartListViewBind?.tv_itemval?.setText(String.format("%.02f", "0.00".toFloat()))
            cartListViewBind?.tv_gstval?.setText(String.format("%.02f", "0.00".toFloat()))
            cartListViewBind?.tv_subtotalval?.setText(String.format("%.02f", "0.00".toFloat()))
            cartListViewBind?.tv_total_price?.setText(String.format("%.02f", "0.00".toFloat()))
            cartListViewBind?.tv_advanceval?.setText(String.format("%.02f", "0.00".toFloat()))
            Alert.showalert(this@CartActivity,"No more cart-item ")

        }
    }

    override fun onResume() {
        super.onResume()
       // getcartlist()
    }


    public  fun updateprice(){
        val pricerangevalue:Int= cartListViewBind!!.rangeSeekbar!!.getValue();
        var totalprice:Int?=0
        for(i in 0 until cartlist?.size!!){
            totalprice=totalprice!!+ cartlist?.get(i)?.product_total_price!!
          //  totalprice=totalprice!!+ cartlist.get(i).product_price*cartlist.get(i).weight
        }
        AppPreferenceHalper.write(PreferenceConstantParam.TOTALVAL,totalprice!!)
       // val totalpricepayprice=totalprice/2
        val calculatedpercentage:Float= ((pricerangevalue*totalprice!!)/100).toFloat()

        val calculatetex:Float=(totalprice*AppPreferenceHalper.read(PreferenceConstantParam.ORDERGST,"")!!.toFloat())/100

        val calculatedval:Int=(calculatedpercentage+AppPreferenceHalper.read(PreferenceConstantParam.PACKING_COST,"")!!.toFloat()+AppPreferenceHalper.read(PreferenceConstantParam.SHIPPINGCOST,"")!!.toFloat()+
                calculatetex).toInt()
        cartListViewBind?.tv_total_price?.setText(String.format("%.02f",calculatedval.toFloat()))
        cartListViewBind?.tv_subtotalval?.setText( String.format("%.02f",calculatedval.toFloat()))
        cartListViewBind?.tv_itemval?.setText(String.format("%.02f",calculatedpercentage))
        cartListViewBind?.tv_gstval?.setText(String.format("%.02f",calculatetex))

        AppPreferenceHalper.write(PreferenceConstantParam.TOTALVAL,calculatedval!!)
       /* doAsync {
            val list = mDb.CakeProductDao().getcatlist(AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_ID,"0")!!)
            if (list.size > 0) {
                val pricerangevalue:Int= cartListViewBind!!.rangeSeekbar!!.getValue();
                var totalprice:Int?=0
                for (CakeProduct in list) {
                    totalprice=totalprice!!+CakeProduct.product_total_price
                }
                AppPreferenceHalper.write(PreferenceConstantParam.TOTALVAL,totalprice!!)
                // val totalpricepayprice=totalprice/2
                val calculatedpercentage:Float= ((pricerangevalue*totalprice!!)/100).toFloat()
                cartListViewBind?.tv_subtotalval?.setText(calculatedpercentage.toString())
                cartListViewBind?.tv_total_price?.setText(calculatedpercentage.toString())
             //   cartListViewBind?.tv_subtotalval?.setText( DecimalFormat("#####.##").format(calculatedpercentage))
               // cartListViewBind?.tv_total_price?.setText(DecimalFormat("#####.##").format(calculatedpercentage))
            }
        }*/



    /*    val amountaftership_packag:Float= (totalamount!!.toFloat()+AppPreferenceHalper.read(PreferenceConstantParam.SHIPPINGCOST,0)!!+ AppPreferenceHalper.read(PreferenceConstantParam.PACKING_COST,0)!!)
        val gst:Float=(totalamount.toFloat()*AppPreferenceHalper.read(PreferenceConstantParam.ORDERGST,0)!!)/100
        builder.setAmount( String.format("%.02f", amountaftership_packag+gst))*/
    }
}