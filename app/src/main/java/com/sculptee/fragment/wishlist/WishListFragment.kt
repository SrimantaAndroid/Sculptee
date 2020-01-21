package com.sculptee.fragment.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rts.commonutils_2_0.netconnection.ConnectionDetector
import com.sculptee.R
import com.sculptee.model.wishlistmodel.WishListmodel
import com.sculptee.network.ApiInterface
import com.sculptee.network.RetroFitApiService
import com.sculptee.screens.home.HomeActivity
import com.sculptee.utils.Alert
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.sculptee.utils.itemclickinterface.OnItemClickInterface
import com.sculptee.utils.preferenceconstant.PreferenceConstantParam
import com.sculptee.utils.sheardpreference.AppPreferenceHalper
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.ArrayList

class WishListFragment:Fragment() {
    var homeActivity:HomeActivity?=null
    var wishListViewBind:WishListViewBind?=null
    var wishListOnClick:WishListOnClick?=null
    var wishListAdapter:WishListAdapter?=null
    var currentwishlist= ArrayList<WishListmodel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):View? {
        homeActivity= activity as HomeActivity?
        homeActivity!!.homeActivityViewBind!!.tv_headertext?.setText(homeActivity?.resources?.getString(R.string.wishlist))
        homeActivity!!.homeActivityOnClick!!.openmylovelist()
        val view=LayoutInflater.from(homeActivity).inflate(R.layout.wish_list_latout,null)
        wishListViewBind= WishListViewBind(homeActivity,view)
        wishListOnClick=WishListOnClick(homeActivity,wishListViewBind)
        setadapter()
        if (!AppPreferenceHalper.read(PreferenceConstantParam.WISHLIST, "").equals(""))
           callapiforwishlist()
        else
            Alert.showalert(homeActivity!!,"You don't have any wish-item")

        return view
    }

    private fun setadapter() {
        wishListAdapter= WishListAdapter(homeActivity,currentwishlist,object :OnItemClickInterface{
            override fun OnClickItem(position: Int) {
                super.OnClickItem(position)
                AppPreferenceHalper.write(PreferenceConstantParam.PRODUCTID,currentwishlist.get(position).p_id)
                homeActivity?.loadeventproductDetailsfragment(currentwishlist.get(position).p_name);
            }
        })
        wishListViewBind?.rec_wishlist?.layoutManager= LinearLayoutManager(homeActivity)
        wishListViewBind?.rec_wishlist?.adapter=wishListAdapter
    }

    private fun callapiforwishlist() {
        if (ConnectionDetector.isConnectingToInternet(activity)) {
            val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
            this!!.homeActivity?.let { customProgress.showProgress(it, "Please Wait..", false) }
            val apiInterface = RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)
            val callApi = apiInterface?.getfevouriteList(AppPreferenceHalper.read(PreferenceConstantParam.WISHLIST, "0"))
            callApi?.enqueue(object : retrofit2.Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    //  progress?.dismiss()
                    customProgress.hideProgress()
                    if (response.isSuccessful) {
                        if (response != null) {
                            try {
                                //  val jsonObject = JSONObject(response?.body().toString())
                                val jsonarray: JSONArray = JSONArray(response.body()?.string())
                                currentwishlist.clear()
                                for( i in 0 until jsonarray.length()){
                                     val jsonobj:JSONObject= jsonarray.get(i) as JSONObject
                                    val name: String = jsonobj.getString("name")
                                    val id: Int = jsonobj.getInt("id")
                                    val price: String = jsonobj.getString("price")
                                    val rating_count:Int=jsonobj.getInt("rating_count")
                                    val average_rating:String=jsonobj.getString("average_rating")
                                    val product_reviews_count:String= jsonobj.getString("product_reviews_count")
                                    val withegg_per_pound:String=jsonobj.getString("withegg_per_pound")
                                    val eggless_per_pound:String=jsonobj.getString("eggless_per_pound")

                                    var imgsrc1:String?=null
                                    val imgJsonArray = jsonobj.getJSONArray("images")
                                    //  val meta_data=jsonObj.getJSONArray("meta_data")
                                    //  var matadatarray = meta_data.getJSONObject(3)
                                    if (imgJsonArray.length() > 0) {
                                        var imgobj1 = imgJsonArray.getJSONObject(0)
                                        imgsrc1 = imgobj1.getString("src")
                                    }
                                    var type:String?=""
                                    var flovoue:String?=""
                                    var size:String?="1"

                                    val default_attributes:JSONArray=jsonobj.getJSONArray("default_attributes")
                                    if(default_attributes.length()>0) {
                                        val default_attributes1: JSONObject = default_attributes.getJSONObject(0)
                                            type = default_attributes1.getString("option")
                                        if(default_attributes.length()>=1) {
                                            val default_attributes2: JSONObject = default_attributes.getJSONObject(1)
                                            flovoue = default_attributes2.getString("option")
                                        }
                                        if(default_attributes.length()>=2) {
                                            val default_attributes3: JSONObject = default_attributes.getJSONObject(2)
                                            size = default_attributes3.getString("option")

                                        }

                                    }
                                    val wishitem=WishListmodel(imgsrc1!!,id,name,type!!,flovoue!!,price,size!!.toInt(),rating_count,average_rating,product_reviews_count,withegg_per_pound,
                                        eggless_per_pound)
                                    currentwishlist.add(wishitem)

                                }
                                if (currentwishlist.size>0)
                                   wishListAdapter!!.notifyDataSetChanged()
                                else
                                    Alert.showalert(homeActivity!!,"You don't have any wish-item")

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }else
                        Alert.showalert(homeActivity!!,"Internal server error.")
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    customProgress.hideProgress()
                }
            })
        }else
            Alert.showalert(homeActivity!!,homeActivity!!.resources.getString(R.string.nointernet))


    }
}