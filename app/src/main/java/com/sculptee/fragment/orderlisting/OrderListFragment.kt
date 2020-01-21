package com.sculptee.fragment.orderlisting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rts.commonutils_2_0.netconnection.ConnectionDetector
import com.sculptee.R
import com.sculptee.fragment.orderlisting.adapter.OrderListAdapter
import com.sculptee.fragment.orderlisting.adapter.PreviousOrderAdapter
import com.sculptee.model.orderlist.OrderListModel
import com.sculptee.network.ApiInterface
import com.sculptee.network.RetroFitApiService
import com.sculptee.screens.home.HomeActivity
import com.sculptee.utils.Alert
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.sculptee.utils.itemclickinterface.OnItemClickInterface
import com.sculptee.utils.preferenceconstant.PreferenceConstantParam
import com.sculptee.utils.sheardpreference.AppPreferenceHalper
import kotlinx.android.synthetic.main.header_layout.*
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class OrderListFragment : Fragment() {
    var homeActivity:HomeActivity?=null
    var orderListViewBind:OrderListViewBind?=null
    var orderListAdapter: OrderListAdapter?=null
    var previousOrderAdapter:PreviousOrderAdapter?=null
    var currentoderlist=ArrayList<OrderListModel>()
    var prevoiusorderlist=ArrayList<OrderListModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeActivity= activity as HomeActivity?
        homeActivity!!.homeActivityViewBind!!.tv_headertext?.setText(homeActivity?.resources?.getString(R.string.orderlist))
        homeActivity!!.homeActivityOnClick!!.openmyorder()
        val view=LayoutInflater.from(homeActivity).inflate(R.layout.order_listing_layout,null)
        homeActivity?.homeActivityViewBind?.tv_headertext?.setText("Order Listing")
        orderListViewBind= OrderListViewBind(homeActivity!!,view)

        setadapter()
      //  setpreviousadapter()
        //callvalueforshipandgstvalue()
        callapiforcatlist()
        return view;
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
                        callapiforcatlist()

                    }
                    // {"tax_rate":"18.0000","tax_calculate_type":"percent","shipping_cost":"200","package_cost":"50","code":"success"}

                }catch (e:Exception){
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // customProgress.hideProgress()
            }
        })

    }
    private fun setpreviousadapter() {
        previousOrderAdapter= PreviousOrderAdapter(homeActivity,object :OnItemClickInterface{

        })
        orderListViewBind?.rec_orderlist_previous?.layoutManager=LinearLayoutManager(homeActivity)
       // orderListViewBind?.rec_orderlist_previous?.adapter=previousOrderAdapter

    }

    private fun setadapter() {
        orderListAdapter= OrderListAdapter(homeActivity,currentoderlist,object :OnItemClickInterface{
            override fun OnClickItem(position: Int) {
                super.OnClickItem(position)
                AppPreferenceHalper.write(PreferenceConstantParam.PRODUCTID,currentoderlist.get(position).p_id)
                homeActivity?.loadeventproductDetailsfragment(currentoderlist.get(position).p_name);

            }
        })
        orderListViewBind?.rec_orderlist?.layoutManager=LinearLayoutManager(homeActivity)
        orderListViewBind?.rec_orderlist?.adapter=orderListAdapter
    }

    private fun callapiforcatlist() {
       /* val progress= ProgressDialog(homeActivity)
        progress?.setMessage("loading..")
        progress?.show()*/
        if (ConnectionDetector.isConnectingToInternet(activity)) {
            val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
            this!!.homeActivity?.let { customProgress.showProgress(it, "Please Wait...", false) }
            val apiInterface = RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)
            val token: String = "Bearer " + AppPreferenceHalper.read(PreferenceConstantParam.TOKEN, "")
            var customerid: String = AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_ID, "").toString()
            val callapi = apiInterface?.getevetcategoriwise(customerid)
            callapi?.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    customProgress.hideProgress()
                    currentoderlist.clear()
                    if (response.isSuccessful) {
                        try {
                            val jsonArray: JSONArray = JSONArray(response.body()?.string())
                            if(jsonArray.length()>0) {

                                for (i in 0 until jsonArray.length()) {
                                    var totalamountofproducts: Float = 0F
                                    val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                                    val line_items: JSONArray =
                                        jsonObject.getJSONArray("line_items")
                                    // val line_itemsobj:JSONObject=line_items.getJSONObject(0)
                                    val status = jsonObject.getString("status")
                                    val payment_method: String =
                                        jsonObject.getString("payment_method")
                                    val payment_method_title: String =
                                        jsonObject.getString("payment_method_title")
                                    val totalamount: Float = jsonObject.getString("total").toFloat()
                                    val order_invoice_url:String=jsonObject.getString("order_invoice_url");
/*
                        val name:String=line_itemsobj.getString("name")
                        val id:Int=line_itemsobj.getInt("product_id")
                        val quantity:Int=line_itemsobj.getInt("quantity")
                        val total:String=line_itemsobj.getString("total")
                        val meta_data:JSONArray=line_itemsobj.getJSONArray("meta_data")
                        val pa_withegg_eggless:String=meta_data.getJSONObject(0).getString("value")
                        val pa_flavour:String=meta_data.getJSONObject(1).getString("value")
                       */

                                    val billing: JSONObject = jsonObject.getJSONObject("billing")
                                    val address_1: String = billing.getString("address_1")
                                    val address_2: String = billing.getString("address_2")
                                    val address: String = address_1 + " " + address_2
                                    val city: String = billing.getString("city")
                                    val state: String = billing.getString("state")
                                    val postcode: String = billing.getString("postcode")


                                    val shipping: JSONObject = jsonObject.getJSONObject("shipping")

                                    val first_name: String = shipping.getString("first_name")
                                    val last_name: String = shipping.getString("last_name")
                                    val fullname: String = first_name + " " + last_name
                                    val address_s1: String = shipping.getString("address_1")
                                    val address_s2: String = shipping.getString("address_2")
                                    val addresss: String = address_s1 + " " + address_s2
                                    val citys: String = shipping.getString("city")
                                    val states: String = shipping.getString("state")
                                    val postcodes: String = shipping.getString("postcode")

                                    for (p in 0 until line_items.length()) {
                                        val line_itemsobj: JSONObject = line_items.getJSONObject(p)
                                        val name: String = line_itemsobj.getString("name")
                                        val id: Int = line_itemsobj.getInt("product_id")
                                        val quantity: Int = line_itemsobj.getInt("quantity")
                                        val total: String = line_itemsobj.getString("total")
                                        val prod_img: String = line_itemsobj.getString("prod_img")
                                        val meta_data: JSONArray = line_itemsobj.getJSONArray("meta_data")
                                        val pa_withegg_eggless: String = meta_data.getJSONObject(0).getString("value")
                                        val pa_flavour: String =
                                            meta_data.getJSONObject(1).getString("value")
                                        var islastitem: Boolean? = null
                                        totalamountofproducts =
                                            totalamountofproducts!! + total.toFloat()
                                        if (p == line_items.length() - 1) {
                                            islastitem = true
                                        } else
                                            islastitem = false

                                        val orderListModel = OrderListModel(prod_img, id, name, pa_withegg_eggless, pa_flavour,
                                            total, quantity, status,
                                            "", payment_method, payment_method_title, address, city, state, postcode, fullname,
                                            addresss, citys, states, postcodes, total,
                                            0,
                                            0,
                                            "",
                                            0,
                                            islastitem,
                                            totalamountofproducts,
                                            order_invoice_url
                                        )
                                        currentoderlist.add(orderListModel)
                                    }


                                }
                                orderListAdapter?.notifyDataSetChanged()
                            }else
                                Alert.showalert(homeActivity!!,"No Order List found.")
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }else{
                        Alert.showalert(homeActivity!!,"Internal server error.")
                    }

                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    customProgress.hideProgress()
                }
            })
        }else
            Alert.showalert(homeActivity!!,homeActivity!!.resources.getString(R.string.nointernet))
    }
}