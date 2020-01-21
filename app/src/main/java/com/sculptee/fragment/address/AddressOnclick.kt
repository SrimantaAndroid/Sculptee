package com.sculptee.fragment.address

import android.view.View
import android.widget.PopupWindow
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.rts.commonutils_2_0.netconnection.ConnectionDetector
import com.sculptee.R
import com.sculptee.datastorage.Address
import com.sculptee.datastorage.RoomSingleton
import com.sculptee.model.address.AddressModel
import com.sculptee.model.events.EventResponse
import com.sculptee.network.ApiInterface
import com.sculptee.network.RetroFitApiService
import com.sculptee.screens.home.HomeActivity
import com.sculptee.utils.Alert
import com.sculptee.utils.customdropdownpopupadapter.DropDownListAdapter
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.sculptee.utils.itemclickinterface.OnItemClickInterface
import com.sculptee.utils.preferenceconstant.PreferenceConstantParam
import com.sculptee.utils.sheardpreference.AppPreferenceHalper
import okhttp3.ResponseBody
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class AddressOnclick :View.OnClickListener{
    private lateinit var mDb: RoomSingleton
    var activity: HomeActivity?=null
    var addressViewBind: AddressViewBind?=null
    var  addressFragment: AddressFragment?=null


    constructor(
        activity: HomeActivity?,
        addressViewBind: AddressViewBind,
        addressFragment: AddressFragment){
        this.activity=activity
        this.addressViewBind=addressViewBind
        this.addressFragment=addressFragment
        mDb = RoomSingleton.getInstance(activity!!.applicationContext)
        setonclick()
    }
    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btn_submitaddress->{
                checkblank()
                //Alert.showalert(activity!!,"Under Development.")
            }
            R.id.rl_addaddress->{
                if (addressViewBind!!.ll_add_address!!.visibility==View.GONE){
                    addressViewBind!!.ll_add_address!!.visibility=View.VISIBLE
                }else
                    addressViewBind!!.ll_add_address!!.visibility=View.GONE
            }
            R.id.tv_addresstype->{
                openpopupdrop_downfoeweight()
            }
        }

    }

    private fun checkblank() {
        if (!addressViewBind!!.tv_addresstype!!.text.toString()!!.equals("")){
          //  if (!addressViewBind!!.et_companyname!!.text.toString().equals("")){
                if (!addressViewBind!!.et_fristname!!.text.toString().equals("")){
                    if (!addressViewBind!!.et_last_name!!.text.toString().equals("")){
                        if(!addressViewBind!!.et_streetaddress1!!.text.toString().equals("")){
                            if (!addressViewBind!!.et_town!!.text.toString().equals("")){
                                if (!addressViewBind!!.et_country!!.text.toString().equals("")){
                                    if (!addressViewBind!!.et_post!!.text.toString().equals("")) {
                                        if (addressFragment!!.iseditcall)
                                            updateaddress()
                                        else
                                           saveaddress()
                                       // Alert.showalert(activity!!,"Under Development.")
                                    }
                                    else
                                        Alert.showalert(activity!!,"Please enter Post-Code.")

                                }
                                else
                                    Alert.showalert(activity!!,"Please enter Country name.")

                            }
                            else
                                Alert.showalert(activity!!,"Please enter town name")
                        }
                        else
                            Alert.showalert(activity!!,"Please enter address.")
                    }
                    else
                        Alert.showalert(activity!!,"Please enter last name.")
                }
                else
                    Alert.showalert(activity!!,"Please enter frist name.")
          //  }

        }else
            Alert.showalert(activity!!,"Select address type.")
    }

    private fun updateaddress() {

        if (ConnectionDetector.isConnectingToInternet(activity)) {
            val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
            this!!.activity?.let { customProgress.showProgress(it, "Please Wait..", false) }
            val apiInterface = RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)


            var jobj: JSONObject? = null
            var addressobj:JSONObject?=null
            try {
                jobj = JSONObject();
                addressobj=JSONObject()
                //addressobj!!.put("uid", AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_ID,"")!!)
               // addressobj!!.put("address_type", addressViewBind!!.tv_addresstype!!.text.toString())
                addressobj.put("first_name", addressViewBind!!.et_fristname!!.text.toString())
                addressobj.put("last_name", addressViewBind!!.et_last_name!!.text.toString())
                addressobj.put("company", addressViewBind!!.et_companyname!!.text.toString())
                addressobj.put("email", AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_EMAIL,"")!!)
                addressobj.put("address_1", addressViewBind!!.et_streetaddress1!!.text.toString())
                addressobj.put("address_2", addressViewBind!!.et_streetaddress2!!.text.toString())
                //+","+addressViewBind!!.et_streetaddress2!!.text.toString())
                addressobj.put("city", addressViewBind!!.et_town!!.text.toString())
                addressobj.put("state", addressViewBind!!.et_country!!.text.toString())
                addressobj.put("postcode", addressViewBind!!.et_post!!.text.toString())
                addressobj.put("country","IN")

                jobj.put("userid",AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_ID,"")!!)
                jobj!!.put("address_type", addressViewBind!!.tv_addresstype!!.text.toString())
               // if (addressFragment!!.iseditcall==true)
                    jobj.put("address_index",addressFragment!!.addressindex)
                if (addressViewBind!!.chk_setdeafult!!.isChecked==true)
                    jobj.put("default","1")
                else
                    jobj.put("default","0")


                jobj.put("shipping",addressobj)

                var obj: JSONObject = jobj
                var jsonParser: JsonParser = JsonParser()
                var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;

                // var callapi=?null;

                //if (addressFragment!!.iseditcall)
                var   callapi = apiInterface?.updateaddress(gsonObject);
                // else
                // callapi = apiInterface?.saveaddress(gsonObject);


                addressFragment!!.iseditcall=false
                addressFragment!!.addressindex=""
                //   callapi!!.enqueue(object :Callback<List<>>)
                callapi!!.enqueue(object :Callback<List<AddressModel>>{

                    override fun onResponse(
                        call: Call<List<AddressModel>>,
                        response: Response<List<AddressModel>>
                    ) {
                        customProgress.hideProgress()
                        if (response.isSuccessful){
                            try {
                                // val jsonarray: JSONArray = JSONArray(response.body()!!.string())
                                val rs: List<AddressModel> = response.body()!!
                                addressFragment!!.addressList!!.clear()
                                addressFragment!!.addressAdapter!!.notifyDataSetChanged()
                                if (rs.size>0) {
                                    for (i in rs){
                                        var isdeafult:Boolean?=false
                                        if (i.getIsDefault().equals("1")){
                                            isdeafult=true
                                            AppPreferenceHalper.write(PreferenceConstantParam.DeafultAddressType,i.getAddressType()!!)
                                            AppPreferenceHalper.write(PreferenceConstantParam.Deafult_F_name,i.getFirstName()!!)
                                            AppPreferenceHalper.write(PreferenceConstantParam.Deafult_L_name,i.getLastName()!!)
                                            AppPreferenceHalper.write(PreferenceConstantParam.Deafult_Street_address1,i.getAddress1()!!)
                                            AppPreferenceHalper.write(PreferenceConstantParam.Deafult_Street_Address2,i.getAddress2()!!)
                                            AppPreferenceHalper.write(PreferenceConstantParam.Deafult_TownCity,i.getCity()!!)
                                            AppPreferenceHalper.write(PreferenceConstantParam.Deafult_state,i.getState()!!)
                                            AppPreferenceHalper.write(PreferenceConstantParam.Deafult_Pin,i.getPostcode()!!)
                                        }else
                                            isdeafult=false

                                        val address=Address(i.getUserId()!!,i.getID()!!,i.getAddressType()!!,i.getCompany()!!,i.getFirstName()!!
                                            ,i.getLastName()!!, i.getAddress1()!!,i.getAddress2()!!,
                                            i.getCity()!!,i.getState()!!,i.getPostcode()!!,isdeafult)

                                        addressFragment!!.addressList!!.add(address)
                                    }

                                }
                                /* for (i in 0 until response.)){
                                     val obj:JSONObject=jsonarray.getJSONObject(i)
                                     var isdeafult:Boolean?=false
                                     if (obj.optString("is_default").equals("1")){
                                         isdeafult=true
                                     }else
                                         isdeafult=false

                                     val address=Address(obj.optString("user_id"),obj.optString("address_type"),obj.optString("company"),obj.optString("first_name")
                                     ,obj.optString("last_name"),obj.optString("address_1")+" "+obj.optString("address_2"),obj.optString("city"),obj.optString("state"),obj.optString("postcode"),isdeafult)

                                     addressFragment!!.addressList!!.add(address)
                                 }*/

                                addressFragment!!.addressAdapter!!.notifyDataSetChanged()

                                addressViewBind!!.tv_addresstype!!.setText("")
                                addressViewBind!!.et_companyname!!.setText("")
                                addressViewBind!!.et_fristname!!.setText("")
                                addressViewBind!!.et_last_name!!.setText("")
                                addressViewBind!!.et_streetaddress1!!.setText("")
                                addressViewBind!!.et_streetaddress2!!.setText("")
                                addressViewBind!!.et_town!!.setText("")
                                addressViewBind!!.et_country!!.setText("West bengal")
                                addressViewBind!!.et_post!!.setText("")
                                    addressViewBind!!.chk_setdeafult!!.isChecked=false
                                addressViewBind!!.ll_add_address!!.visibility=View.GONE

                            }catch (e:Exception){
                                e.printStackTrace()
                            }


                        }else
                            Alert.showalert(activity!!,"Internal Server error")
                    }
                    override fun onFailure(call: Call<List<AddressModel>>, t: Throwable) {
                        customProgress.hideProgress()

                    }

                })
            }catch (e:Exception){
                e.printStackTrace()
            }

            /*doAsync {
               // mDb.CakeProductDao().insetaddress(address)

                uiThread {
                    Toast.makeText(activity,"Address added successfully.", Toast.LENGTH_LONG).show()

                }
            }*/
        }else
            Alert.showalert(activity!!,activity!!.resources.getString(R.string.nointernet))

    }

    private fun saveaddress() {
        val address=Address(AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_ID,"")!!,"2"
            ,addressViewBind!!.tv_addresstype!!.text.toString(),addressViewBind!!.et_companyname!!.text.toString(),
            addressViewBind!!.et_fristname!!.text.toString(),addressViewBind!!.et_last_name!!.text.toString(),
            addressViewBind!!.et_streetaddress1!!.text.toString(),addressViewBind!!.et_streetaddress2!!.text.toString(),
            addressViewBind!!.et_town!!.text.toString(),addressViewBind!!.et_country!!.text.toString(),addressViewBind!!.et_post!!.text.toString(),
            addressViewBind!!.chk_setdeafult!!.isChecked)

        if (ConnectionDetector.isConnectingToInternet(activity)) {
            val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
            this!!.activity?.let { customProgress.showProgress(it, "Please Wait..", false) }
            val apiInterface = RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)

            var jobj: JSONObject? = null
            var addressobj:JSONObject?=null
            try {
                jobj = JSONObject();
                addressobj=JSONObject()
                addressobj!!.put("address_type", addressViewBind!!.tv_addresstype!!.text.toString())
                addressobj.put("first_name", addressViewBind!!.et_fristname!!.text.toString())
                addressobj.put("last_name", addressViewBind!!.et_last_name!!.text.toString())
                addressobj.put("company", addressViewBind!!.et_companyname!!.text.toString())
                addressobj.put("email", AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_EMAIL,"")!!)
                addressobj.put("address_1", addressViewBind!!.et_streetaddress1!!.text.toString())
                addressobj.put("address_2", addressViewBind!!.et_streetaddress2!!.text.toString())
                        //+","+addressViewBind!!.et_streetaddress2!!.text.toString())
                addressobj.put("city", addressViewBind!!.et_town!!.text.toString())
                addressobj.put("state", addressViewBind!!.et_country!!.text.toString())
                addressobj.put("postcode", addressViewBind!!.et_post!!.text.toString())
                addressobj.put("country","IN")

                jobj.put("userid",AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_ID,"")!!)
                jobj!!.put("address_type", addressViewBind!!.tv_addresstype!!.text.toString())
              //  if (addressFragment!!.iseditcall==true)
                  //  jobj.put("address_index",addressFragment!!.addressindex)
               if (addressViewBind!!.chk_setdeafult!!.isChecked==true)
                  jobj.put("default","1")
                else
                   jobj.put("default","0")


                jobj.put("shipping",addressobj)

                var obj: JSONObject = jobj
                var jsonParser: JsonParser = JsonParser()
                var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;

               // var callapi=?null;

                //if (addressFragment!!.iseditcall)
                var   callapi = apiInterface?.saveaddress(gsonObject);
               // else
                   // callapi = apiInterface?.saveaddress(gsonObject);


              //  addressFragment!!.iseditcall=false
                //addressFragment!!.addressindex=""
             //   callapi!!.enqueue(object :Callback<List<>>)
                callapi!!.enqueue(object :Callback<List<AddressModel>>{

                    override fun onResponse(
                        call: Call<List<AddressModel>>,
                        response: Response<List<AddressModel>>
                    ) {
                        customProgress.hideProgress()
                        if (response.isSuccessful){
                            try {
                               // val jsonarray: JSONArray = JSONArray(response.body()!!.string())
                                val rs: List<AddressModel> = response.body()!!
                                addressFragment!!.addressList!!.clear()
                                addressFragment!!.addressAdapter!!.notifyDataSetChanged()
                                if (rs.size>0) {
                                    for (i in rs){
                                        var isdeafult:Boolean?=false
                                        if (i.getIsDefault().equals("1")){
                                            isdeafult=true
                                            AppPreferenceHalper.write(PreferenceConstantParam.DeafultAddressType,i.getAddressType()!!)
                                            AppPreferenceHalper.write(PreferenceConstantParam.Deafult_F_name,i.getFirstName()!!)
                                            AppPreferenceHalper.write(PreferenceConstantParam.Deafult_L_name,i.getLastName()!!)
                                            AppPreferenceHalper.write(PreferenceConstantParam.Deafult_Street_address1,i.getAddress1()!!)
                                            AppPreferenceHalper.write(PreferenceConstantParam.Deafult_Street_Address2,i.getAddress2()!!)
                                            AppPreferenceHalper.write(PreferenceConstantParam.Deafult_TownCity,i.getCity()!!)
                                            AppPreferenceHalper.write(PreferenceConstantParam.Deafult_state,i.getState()!!)
                                            AppPreferenceHalper.write(PreferenceConstantParam.Deafult_Pin,i.getPostcode()!!)
                                        }else
                                            isdeafult=false

                                        val address=Address(i.getUserId()!!,i.getID()!!,i.getAddressType()!!,i.getCompany()!!,i.getFirstName()!!
                                            ,i.getLastName()!!, i.getAddress1()!!,i.getAddress2()!!,
                                           i.getCity()!!,i.getState()!!,i.getPostcode()!!,isdeafult)

                                        addressFragment!!.addressList!!.add(address)
                                    }

                                }
                               /* for (i in 0 until response.)){
                                    val obj:JSONObject=jsonarray.getJSONObject(i)
                                    var isdeafult:Boolean?=false
                                    if (obj.optString("is_default").equals("1")){
                                        isdeafult=true
                                    }else
                                        isdeafult=false

                                    val address=Address(obj.optString("user_id"),obj.optString("address_type"),obj.optString("company"),obj.optString("first_name")
                                    ,obj.optString("last_name"),obj.optString("address_1")+" "+obj.optString("address_2"),obj.optString("city"),obj.optString("state"),obj.optString("postcode"),isdeafult)

                                    addressFragment!!.addressList!!.add(address)
                                }*/

                                addressFragment!!.addressAdapter!!.notifyDataSetChanged()
                                addressViewBind!!.tv_addresstype!!.setText("")
                                addressViewBind!!.et_companyname!!.setText("")
                                addressViewBind!!.et_fristname!!.setText("")
                                addressViewBind!!.et_last_name!!.setText("")
                                addressViewBind!!.et_streetaddress1!!.setText("")
                                addressViewBind!!.et_streetaddress2!!.setText("")
                                addressViewBind!!.et_town!!.setText("")
                                addressViewBind!!.et_country!!.setText("West bengal")
                                addressViewBind!!.et_post!!.setText("")
                                addressViewBind!!.chk_setdeafult!!.isChecked=false
                                addressViewBind!!.ll_add_address!!.visibility=View.GONE

                            }catch (e:Exception){
                                e.printStackTrace()
                            }


                        }else
                            Alert.showalert(activity!!,"Internal Server error")
                    }
                    override fun onFailure(call: Call<List<AddressModel>>, t: Throwable) {
                        customProgress.hideProgress()

                    }

                })
            }catch (e:Exception){
                e.printStackTrace()
            }

        /*doAsync {
           // mDb.CakeProductDao().insetaddress(address)

            uiThread {
                Toast.makeText(activity,"Address added successfully.", Toast.LENGTH_LONG).show()
                
            }
        }*/
        }else
            Alert.showalert(activity!!,activity!!.resources.getString(R.string.nointernet))

    }

    private fun setonclick() {
        addressViewBind!!.btn_submitaddress!!.setOnClickListener(this)
        addressViewBind!!.rl_addaddress!!.setOnClickListener (this)
        addressViewBind!!.tv_addresstype!!.setOnClickListener(this)
    }

    private fun openpopupdrop_downfoeweight() {
        var addresstype=ArrayList<String>()
        addresstype.add("home")
        addresstype.add("office")
        addresstype.add("work")
        val popUpView = activity?.getLayoutInflater()?.inflate(com.sculptee.R.layout.drop_down_layout, null)
        val  listPopupWindow = PopupWindow(popUpView, activity?.resources?.getDimension(R.dimen._90sdp)!!.toInt(), activity?.resources?.getDimension(R.dimen._90sdp)!!.toInt(), true
            // ViewGroup.LayoutParams.WRAP_CONTENT, false
        )
        val reclyerview_dropdown: RecyclerView =popUpView!!.findViewById(R.id.reclyerview_dropdown)
        val dropDownListAdapter: DropDownListAdapter = DropDownListAdapter(activity!!,addresstype!!,object :
            OnItemClickInterface {
            override fun OnClickItem(position: Int) {
                super.OnClickItem(position)
                // productDetailsViewBind?.tv_weightval?.setText("10 persons")
                addressViewBind!!.tv_addresstype!!.setText(addresstype.get(position))
                listPopupWindow.dismiss()
            }
        })
        reclyerview_dropdown?.layoutManager= LinearLayoutManager(activity)
        reclyerview_dropdown?.adapter=dropDownListAdapter
        listPopupWindow?.showAsDropDown(addressViewBind!!.tv_addresstype);

    }
}