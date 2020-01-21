package com.sculptee.fragment.eventlisting

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.netconnection.ConnectionDetector
import com.sculptee.R
import com.sculptee.fragment.eventlisting.adapter.EvenProducttListAdapter
import com.sculptee.model.eventlistingmodel.EventListModel
import com.sculptee.network.ApiInterface
import com.sculptee.network.RetroFitApiService
import com.sculptee.screens.home.HomeActivity
import com.sculptee.utils.Alert
import com.sculptee.utils.AppConstants
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.sculptee.utils.itemclickinterface.OnItemClickInterface
import com.sculptee.utils.preferenceconstant.PreferenceConstantParam
import com.sculptee.utils.sheardpreference.AppPreferenceHalper
import kotlinx.android.synthetic.main.header_layout.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.json.JSONArray







class EventListingFragment:Fragment() {
    var eventListViewBind:EventListViewBind?=null
    var homeActivity:HomeActivity?=null
    var progress: ProgressDialog?=null
    var eventProductitemAdapter:EvenProducttListAdapter?=null
    var loading = false
    var pastVisiblesItems: Int = 0
    var visibleItemCount:Int = 0
    var totalItemCount:Int = 0
    var eventproductlist=ArrayList<EventListModel>()
    var total_item:Int=3
    var totalpage:Int=0
    var currentpage:Int=1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater?.inflate(R.layout.fragment_event_list,container,false)
        homeActivity=activity as HomeActivity?
        homeActivity!!.loadeventsidemenu()
        homeActivity!!.homeActivityViewBind!!.tv_headertext?.setText(AppPreferenceHalper.read(PreferenceConstantParam.EVENTNAME,""))
        PreferenceConstantParam.EVENTNAME
        eventListViewBind= EventListViewBind(homeActivity,view)
        homeActivity?.let { AppPreferenceHalper.init(it) }
        setadapter()
        if(eventproductlist.size>0)
            eventproductlist.clear()
        currentpage=1
        callapiforeventlist();
       // homeActivity?.let { Alert.showalert(it,"hellow sDFWEG Wer ER wer RWEWER R EWRFewr WER") }
        return view
    }

    private fun callapiforeventlist() {
        //progress= ProgressDialog(homeActivity)
      //  progress?.setMessage("loading..")
      //  progress?.show()
        if(ConnectionDetector.isConnectingToInternet(homeActivity)) {
            val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
            this!!.homeActivity?.let { customProgress.showProgress(it, "Please Wait...", false) }
            val apiInterface = RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)

            val callapi = apiInterface?.getevetcategoriwise(AppPreferenceHalper.read(PreferenceConstantParam.EVENTID, "").toString(), currentpage, AppConstants.PERPAGE_ITEM)
            callapi?.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    loading = true
                    // progress?.dismiss()
                    customProgress.hideProgress()
                    if (response.isSuccessful) {
                        if (response != null) {
                            //  eventproductlist.clear()
                            try {
                                System.out.println("header" + response.headers().toString())
                                total_item = response.headers().get("X-WP-Total")!!.toInt()
                                totalpage = response.headers().get("X-WP-TotalPages")!!.toInt()
                                val jsonArray = JSONArray(response.body()?.string())
                                for (i in 0 until jsonArray.length()) {
                                    val jsonObject = jsonArray.getJSONObject(i)
                                    val imgJsonArray = jsonObject.getJSONArray("images")
                                    var imgsrc: String? = ""
                                    if (imgJsonArray.length() > 0) {
                                        var imgobj = imgJsonArray.getJSONObject(0)
                                        imgsrc = imgobj.getString("src")
                                    }
                                    val eventproductmodel = EventListModel(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("name"),
                                        //  jsonObject.getInt("stock_quantity"),
                                        3,
                                        jsonObject.getString("average_rating"),
                                        jsonObject.getInt("rating_count"),
                                        imgsrc, jsonObject.getInt("product_reviews_count"),
                                        jsonObject.getString("withegg_per_pound")
                                    )
                                    eventproductlist.add(eventproductmodel)
                                }
                                if (eventproductlist.size > 0)
                                    eventProductitemAdapter?.notifyDataSetChanged()
                                else
                                    eventListViewBind?.tv_noitem?.visibility = View.VISIBLE

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                     else{
                        Alert.showalert(homeActivity!!,"Internal server error.")
                     // Toast.makeText(homeActivity,response.body().toString(), Toast.LENGTH_LONG).show()
                  }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    customProgress.hideProgress()
                    Alert.showalert(homeActivity!!,"Internal server error.")
                }

            })
        }else
            Alert.showalert(homeActivity!!,homeActivity!!.resources.getString(R.string.nointernet))

    }

    private fun setadapter() {
        eventProductitemAdapter= EvenProducttListAdapter(homeActivity,eventproductlist,object :OnItemClickInterface{
            override fun OnClickItem(position: Int) {
                super.OnClickItem(position)
                AppPreferenceHalper.write(PreferenceConstantParam.PRODUCTID,eventproductlist.get(position).id)
                homeActivity?.loadeventproductDetailsfragment(homeActivity?.tv_headertext?.text.toString());
            }
        })
        eventListViewBind?.rec_category_itemlist?.layoutManager=LinearLayoutManager(homeActivity)
        eventListViewBind?.rec_category_itemlist?.adapter=eventProductitemAdapter
        eventListViewBind?.rec_category_itemlist?.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                var linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if(dy>0) {
                    visibleItemCount = linearLayoutManager?.getChildCount()!!
                    totalItemCount = linearLayoutManager?.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition()
                    if (loading)
                    {
                       // if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        if (total_item>eventproductlist.size)
                        {
                            currentpage++
                            loading = false;
                            callapiforeventlist()
                        }
                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

    }

    override fun onResume() {
        super.onResume()
      //  eventproductlist.clear()
    }
}