package com.sculptee.fragment.event

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.rts.commonutils_2_0.netconnection.ConnectionDetector
import com.sculptee.R
import com.sculptee.fragment.event.adapter.EventAdapter
import com.sculptee.model.events.EventModel
import com.sculptee.model.events.EventResponse
import com.sculptee.network.ApiInterface
import com.sculptee.network.RetroFitApiService
import com.sculptee.screens.home.HomeActivity
import com.sculptee.utils.Alert
import com.sculptee.utils.GridSpacesItemDecoration
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.sculptee.utils.itemclickinterface.OnItemClickInterface
import com.sculptee.utils.preferenceconstant.PreferenceConstantParam
import com.sculptee.utils.sheardpreference.AppPreferenceHalper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EventFragment: Fragment() {
    var homeActivity:HomeActivity?=null
    var eventFragmentViewBind:EventFragmentViewBind?=null
    var eventFragmentOnCLick:EventFragmentOnCLick?=null
    var eventAdapter:EventAdapter?=null
    var progress:ProgressDialog?=null
    var eventlist= ArrayList<EventModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater!!.inflate(R.layout.fragment_event,container,false)
        homeActivity= activity as HomeActivity?
      //  eventFragmentViewBind= homeActivity?.let { EventFragmentViewBind(it,view) }
        homeActivity!!.loadeventsidemenu()
        eventFragmentViewBind= homeActivity?.let { EventFragmentViewBind(it,view) }
        eventFragmentOnCLick= EventFragmentOnCLick(homeActivity,eventFragmentViewBind)
        homeActivity?.let { AppPreferenceHalper.init(it) }
        setadadpter();
        callapiforevent();
        return view
    }

    private fun callapiforevent() {
       /* progress= ProgressDialog(homeActivity)
        progress?.setMessage("loading..")
        progress?.show()*/
        if (ConnectionDetector.isConnectingToInternet(homeActivity)) {
            val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
            this!!.homeActivity?.let { customProgress.showProgress(it, "Please Wait..", false) }
            val apiInterface =
                RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)
            val callapi = apiInterface?.getevent()
            callapi?.enqueue(object : Callback<List<EventResponse>> {
                override fun onResponse(call: Call<List<EventResponse>>, response: Response<List<EventResponse>>) {
                    System.out.println("api" + response.code())
                    //  progress!!.dismiss()
                    customProgress.hideProgress();
                    if (response.isSuccessful) {
                        if (response != null) {
                            eventlist.clear()
                            try {
                                val rs: List<EventResponse> = response.body()!!
                                if (rs.size>0) {
                                    for (i in rs) {
                                        val eventmodel = EventModel(i.getId()!!, i.getName()!!, i.getImage()!!.getSrc()!!)
                                        eventlist.add(eventmodel)
                                    }
                                    eventAdapter?.notifyDataSetChanged()
                                }else
                                    Alert.showalert(homeActivity!!,"No Record found.")

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }else
                            Alert.showalert(homeActivity!!,"No Record found.")
                    }
                    else
                    {
                        Alert.showalertinternerservererror(homeActivity!!,"Internal server error.")
                    }
                    //else{
                    // Toast.makeText(homeActivity,response.body().toString(),Toast.LENGTH_LONG).show()
                    // }
                }

                override fun onFailure(call: Call<List<EventResponse>>, t: Throwable) {
                    //   progress!!.dismiss()
                    customProgress.hideProgress();
                    Alert.showalertinternerservererror(homeActivity!!,"Internal server error.")
                   // System.out.println("error api")
                }
            })
        }else
            Alert.showalertinternerservererror(homeActivity!!,homeActivity!!.resources.getString(R.string.nointernet))

    }

    private fun setadadpter() {
        eventAdapter= EventAdapter(homeActivity,eventlist,object :OnItemClickInterface{
            override fun OnClickItem(position: Int) {
               // super.OnClickItem(position)
             //   Toast.makeText(homeActivity,position.toString(), Toast.LENGTH_LONG).show()
                AppPreferenceHalper.write(PreferenceConstantParam.EVENTID,eventlist.get(position).id.toString())
                AppPreferenceHalper.write(PreferenceConstantParam.EVENTNAME,eventlist.get(position).name)
                homeActivity?.loadeventproductlistfragment(eventlist.get(position).name)
                homeActivity?.homeActivityViewBind?.tv_headertext?.setText(eventlist.get(position).name)
            }
        });
        val  spacingInPixels:Int = getResources().getDimensionPixelSize(R.dimen._10sdp);
        eventFragmentViewBind?.rec_event_list?.addItemDecoration(GridSpacesItemDecoration(spacingInPixels))
        eventFragmentViewBind?.rec_event_list?.layoutManager = GridLayoutManager(homeActivity, 2)
        eventFragmentViewBind?.rec_event_list?.adapter=eventAdapter;

    }
}