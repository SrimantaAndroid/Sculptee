package com.sculptee.fragment.productdetails.adapter

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.R
import com.sculptee.fragment.orderlisting.adapter.OrderListAdapter
import com.sculptee.model.review.ProductCommand
import com.sculptee.screens.home.HomeActivity

class CommandListAdapter(
    val homeActivity: HomeActivity,
    val commandArrayList: ArrayList<ProductCommand>):
    RecyclerView.Adapter<CommandListAdapter.ViewHolder>() {
    var deviceResolution: DeviceResolution?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        deviceResolution= DeviceResolution(homeActivity)
        val view= LayoutInflater.from(homeActivity).inflate(R.layout.command_item,null)
        return CommandListAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
      return   commandArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!commandArrayList.get(position).comment_author.equals(""))
        holder.cmd_authorname!!.setText(commandArrayList.get(position).comment_author)
        else
            holder.cmd_authorname!!.setText("Anonymous")

        if (!commandArrayList.get(position).rating.equals(""))
            holder.tv_rating!!.setText(commandArrayList.get(position).rating)
        else
            holder.tv_rating!!.setText("0")


        holder.message!!.setText(commandArrayList.get(position).comment_content)

        holder.tv_datetime!!.setText(commandArrayList.get(position).comment_date)

    }


    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val cmd_authorname:TextView=itemview.findViewById(R.id.cmd_authorname)
        val message:TextView=itemview.findViewById(R.id.message)
        val tv_rating:TextView=itemview.findViewById(R.id.tv_rating)
        val tv_datetime:TextView=itemview.findViewById(R.id.tv_datetime)

    }
}