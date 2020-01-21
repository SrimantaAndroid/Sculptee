package com.sculptee.utils.customdropdownpopupadapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.R
import com.sculptee.screens.home.HomeActivity
import com.sculptee.utils.itemclickinterface.OnItemClickInterface

class DropDownListAdapter(
    val homeActivity: Activity?,
    val list:ArrayList<String>?,
    val onItemClickInterface: OnItemClickInterface) : RecyclerView.Adapter<DropDownListAdapter.ViewHolder>() {
    var  deviceResolution: DeviceResolution?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DropDownListAdapter.ViewHolder {
        deviceResolution= DeviceResolution(homeActivity)
        val view= LayoutInflater.from(homeActivity).inflate(R.layout.drop_down_item,null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_itemname?.setTypeface(deviceResolution?.getMavenProRegular(homeActivity))
        holder.tv_itemname?.setText(list?.get(position))
        holder.ll_item?.setOnClickListener(View.OnClickListener {
            onItemClickInterface.OnClickItem(position)
        })
    }

    class ViewHolder(itemview:View): RecyclerView.ViewHolder(itemview){
        val tv_itemname:TextView?=itemview.findViewById(R.id.tv_itemname)
        val ll_item:LinearLayout?=itemview.findViewById(R.id.ll_item)


    }
}