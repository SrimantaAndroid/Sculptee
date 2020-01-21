package com.sculptee.fragment.address


import android.app.Dialog
import android.os.Bundle
import android.preference.Preference
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.rts.commonutils_2_0.netconnection.ConnectionDetector

import com.sculptee.R
import com.sculptee.datastorage.Address
import com.sculptee.fragment.address.adapter.AddressAdapter
import com.sculptee.model.address.AddressModel
import com.sculptee.network.ApiInterface
import com.sculptee.network.RetroFitApiService
import com.sculptee.screens.home.HomeActivity
import com.sculptee.utils.Alert
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.sculptee.utils.itemclickinterface.OnItemClickInterface
import com.sculptee.utils.preferenceconstant.PreferenceConstantParam
import com.sculptee.utils.sheardpreference.AppPreferenceHalper
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Appendable
import java.lang.Exception


class AddressFragment : Fragment() {
    var addressViewBind:AddressViewBind?=null
    var addressOnclick:AddressOnclick?=null
    var homeActivity:HomeActivity?=null
    public var addressAdapter:AddressAdapter?=null
    public var addressList=ArrayList<Address>()
    public  var iseditcall=false
    public  var addressindex=""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeActivity= activity as HomeActivity?
        val view:View=LayoutInflater.from(homeActivity).inflate(R.layout.fragment_address,container,false)
        homeActivity!!.homeActivityViewBind!!.tv_headertext?.setText(homeActivity?.resources?.getString(R.string.address))
        homeActivity!!.homeActivityOnClick!!.openaddress()
        addressViewBind= AddressViewBind(homeActivity,view!!)
        addressOnclick=AddressOnclick(homeActivity,addressViewBind!!,this)
        getActivity()!!.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setadapter()
        fetuseraddressapi()
        return view

    }

    private fun fetuseraddressapi() {
        if (ConnectionDetector.isConnectingToInternet(activity)) {
            val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
            this!!.activity?.let { customProgress.showProgress(it, "Please Wait..", false) }
            val apiInterface = RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)
            var jobj: JSONObject? = null

            try {
                jobj = JSONObject();
                //addressobj!!.put("uid", AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_ID,"")!!)
                jobj!!.put("userid", AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_ID,"")!!)

                var obj: JSONObject = jobj
                var jsonParser: JsonParser = JsonParser()
                var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;

                var   callapi = apiInterface?.getuseraddress(AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_ID,"")!!);
                callapi!!.enqueue(object : Callback<List<AddressModel>> {

                    override fun onResponse(call: Call<List<AddressModel>>, response: Response<List<AddressModel>>) {
                        customProgress.hideProgress()
                        if (response.isSuccessful){
                            try {
                                val rs: List<AddressModel> = response.body()!!
                                addressList.clear()
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

                                      addressList!!.add(address)
                                    }

                                }
                                addressAdapter!!.notifyDataSetChanged()
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

            }
            catch (e:Exception){
            e.printStackTrace()
                customProgress.hideProgress()}

        }
    }

    private fun setadapter() {
        addressAdapter= AddressAdapter(homeActivity!!,addressList,this,object :
            OnItemClickInterface{
            override fun OnClickItem(position: Int) {
                super.OnClickItem(position)
                iseditcall=true
                addressindex=addressList.get(position).addid
                addressViewBind!!.ll_add_address!!.visibility=View.VISIBLE
                addressViewBind!!.et_fristname!!.requestFocus()
                addressViewBind!!.tv_addresstype!!.setText(addressList.get(position).addresstype)
                addressViewBind!!.et_companyname!!.setText(addressList.get(position).companyname)
                addressViewBind!!.et_fristname!!.setText(addressList.get(position).fname)
                addressViewBind!!.et_last_name!!.setText(addressList.get(position).lname)
                addressViewBind!!.et_streetaddress1!!.setText(addressList.get(position).address1)
                addressViewBind!!.et_streetaddress2!!.setText(addressList.get(position).address2)
                addressViewBind!!.et_town!!.setText(addressList.get(position).town)
                addressViewBind!!.et_country!!.setText(addressList.get(position).state)
                addressViewBind!!.et_post!!.setText(addressList.get(position).pin)
                if (addressList.get(position).default==true)
                addressViewBind!!.chk_setdeafult!!.isChecked=true
                else
                    addressViewBind!!.chk_setdeafult!!.isChecked=false


            }
        })
        addressViewBind?.reclyerview_address?.layoutManager= LinearLayoutManager(homeActivity)
        addressViewBind?.reclyerview_address?.adapter=addressAdapter
    }

    fun calldeleteaddress(position: Int) {
        showalertfordeleteitem(position)
      // deleteddressapi(position)
       // addressList.removeAt(position)
           // addressAdapter!!.notifyDataSetChanged()

    }

    private fun showalertfordeleteitem(position: Int) {
        var deviceResolution= DeviceResolution(homeActivity)
        val alertDialog = Dialog(homeActivity,R.style.Transparent)
        /*alertDialog.setTitle(activity.resources.getString(R.string.app_name))
        alertDialog.setMessage(message)*/
        alertDialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
        val view: View =LayoutInflater.from(homeActivity).inflate(R.layout.alert_layout_yesno,null)
        alertDialog.setContentView(view)
        alertDialog .setCancelable(false)
        val tv_message: TextView =view.findViewById(R.id.tv_message)
        val btn_ok: Button =view.findViewById(R.id.btn_ok)
        val btn_no: Button =view.findViewById(R.id.btn_no)
        btn_ok.typeface=deviceResolution.getMavenProRegular(homeActivity)
        btn_no.typeface=deviceResolution.getMavenProRegular(homeActivity)
        tv_message.typeface=deviceResolution.getMavenProRegular(homeActivity)
        btn_ok.setOnClickListener {
            alertDialog.dismiss()
            deleteddressapi(position)
           /* doAsync {
                cartActivity.mDb.CakeProductDao().deleterecord(cartlist.get(position))
                cartlist.removeAt(position)
                // cartActivity.cartlist.removeAt(position)
                cartActivity.runOnUiThread {

                    cartActivity.showtotalprice()
                    notifyItemRemoved(position)

                }

            }*/

        }
        btn_no.setOnClickListener {
            alertDialog.dismiss()
        }
        tv_message.setText("Are you want to Romove from Your Address list?")
        alertDialog.show()
    }

    private fun deleteddressapi(position: Int) {
        if (ConnectionDetector.isConnectingToInternet(activity)) {
            val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
            this!!.activity?.let { customProgress.showProgress(it, "Please Wait..", false) }
            val apiInterface = RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)
            var jobj: JSONObject? = null
            var addressobj: JSONObject?=null
            try {
                jobj = JSONObject();

                jobj!!.put("userid", AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_ID,"")!!)
                jobj!!.put("address_index", addressList.get(position).addid)

                var obj: JSONObject = jobj
                var jsonParser: JsonParser = JsonParser()
                var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;

                var   callapi = apiInterface?.deleteddress(AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_ID,"")!!,addressList.get(position).addid);
                callapi!!.enqueue(object : Callback<List<AddressModel>> {

                    override fun onResponse(call: Call<List<AddressModel>>, response: Response<List<AddressModel>>) {
                        customProgress.hideProgress()
                        if (response.isSuccessful){
                            try {
                                val rs: List<AddressModel> = response.body()!!
                                addressList!!.clear()
                                //addressAdapter!!.notifyDataSetChanged()
                                if (rs.size>0) {
                                    for (i in rs){
                                        var isdeafult:Boolean?=false
                                        if (i.getIsDefault().equals("1")){
                                            isdeafult=true
                                        }else
                                            isdeafult=false

                                        val address=Address(i.getUserId()!!,i.getID()!!,i.getAddressType()!!,i.getCompany()!!,i.getFirstName()!!
                                            ,i.getLastName()!!, i.getAddress1()!!,i.getAddress2()!!,
                                            i.getCity()!!,i.getState()!!,i.getPostcode()!!,isdeafult)

                                        addressList!!.add(address)
                                    }

                                }
                                addressAdapter!!.notifyDataSetChanged()
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

            }
            catch (e:Exception){
                e.printStackTrace()}
        }
    }

    fun calleditaddress(position:Int) {
        iseditcall=true
        addressindex=addressList.get(position).addid
        addressViewBind!!.ll_add_address!!.visibility=View.VISIBLE
        addressViewBind!!.et_fristname!!.requestFocus()
        addressViewBind!!.tv_addresstype!!.setText(addressList.get(position).addresstype)
        addressViewBind!!.et_companyname!!.setText(addressList.get(position).companyname)
        addressViewBind!!.et_fristname!!.setText(addressList.get(position).fname)
        addressViewBind!!.et_last_name!!.setText(addressList.get(position).lname)
        addressViewBind!!.et_streetaddress1!!.setText(addressList.get(position).address1)
        addressViewBind!!.et_streetaddress2!!.setText(addressList.get(position).address2)
        addressViewBind!!.et_town!!.setText(addressList.get(position).town)
        addressViewBind!!.et_country!!.setText(addressList.get(position).state)
        addressViewBind!!.et_post!!.setText(addressList.get(position).pin)
        if (addressList.get(position).default==true)
            addressViewBind!!.chk_setdeafult!!.isChecked=true
        else
            addressViewBind!!.chk_setdeafult!!.isChecked=false

    }

}
