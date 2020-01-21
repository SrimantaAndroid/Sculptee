package com.sculptee.fragment.eventlisting.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.R
import com.sculptee.model.eventlistingmodel.EventListModel
import com.sculptee.screens.home.HomeActivity
import com.sculptee.utils.itemclickinterface.OnItemClickInterface

class EvenProducttListAdapter(
    val homeActivity: HomeActivity?,
    val eventproductlist: ArrayList<EventListModel>,
    val onItemClickInterface: OnItemClickInterface

) : RecyclerView.Adapter<EvenProducttListAdapter.ViewHolder>() {
    var  deviceResolution: DeviceResolution?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        deviceResolution= DeviceResolution(homeActivity)
        val view=LayoutInflater.from(homeActivity).inflate(R.layout.event_product_item_layout,null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return eventproductlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var  param: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,deviceResolution?.getHeight(0.88)!!/2)
        holder.rl_evet_list.layoutParams=param
        holder.tv_product_name?.setTypeface(deviceResolution?.getMavenProBold(homeActivity))
        holder.tv_avalibility?.setTypeface(deviceResolution?.getMavenProBold(homeActivity))
        holder.tv_ratingcount?.setTypeface(deviceResolution?.getMavenProRegular(homeActivity))
        holder.tv_reviewcount?.setTypeface(deviceResolution?.getMavenProRegular(homeActivity))
        holder.tv_offprice?.setTypeface(deviceResolution?.getMavenProBold(homeActivity))
        holder.tv_avaragerating?.setTypeface(deviceResolution?.getMavenProBold(homeActivity))
        holder.tv_product_name.setText(eventproductlist.get(position).name)
        holder.tv_ratingcount.setText(eventproductlist.get(position).rating_count.toString()+" "+homeActivity?.resources?.getString(R.string.rating))
        holder.tv_product_currentprice.setTypeface(deviceResolution?.getMavenProBold(homeActivity))
        holder.tv_product_currentprice.setText(eventproductlist.get(position).price+homeActivity?.resources?.getString(R.string.perpound))

        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        requestOptions.centerCrop()
        if(!eventproductlist.get(position).imgsrc.equals("")) {
            Glide.with(homeActivity)
                .load(eventproductlist.get(position).imgsrc)
                .apply(
                    RequestOptions().transforms(
                        RoundedCorners(homeActivity?.resources?.getDimension(R.dimen._3sdp)!!.toInt())
                    )
                )
                .into(holder.img_cake)
        }
        holder.tv_avaragerating.setText(eventproductlist.get(position).average_rating)
        holder.tv_reviewcount.setText(eventproductlist.get(position).reviewcount.toString()+" "+homeActivity?.resources?.getString(R.string.review))
        if(eventproductlist.get(position).stock>0){
            holder.tv_avalibility?.setText(homeActivity?.resources?.getString(R.string.avalible))
            homeActivity?.resources?.getColor(R.color.light_green)?.let { holder.tv_avalibility.setTextColor(it) }

        }else{
            holder.tv_avalibility?.setText(homeActivity?.resources?.getString(R.string.not_avalible))
            homeActivity?.resources?.getColor(R.color.red)?.let { holder.tv_avalibility.setTextColor(it) }
        }
        holder.rl_evet_list.setOnClickListener { v: View? ->
            onItemClickInterface.OnClickItem(position)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tv_avalibility: TextView =itemView?.findViewById(R.id.tv_avalibility)
        val tv_product_name: TextView=itemView?.findViewById(R.id.tv_product_name)
        val tv_ratingcount:TextView=itemView?.findViewById(R.id.tv_ratingcount)
        val tv_reviewcount:TextView=itemView.findViewById(R.id.tv_reviewcount)
        val tv_offprice:TextView=itemView.findViewById(R.id.tv_offprice)
        val img_cake:ImageView=itemView.findViewById(R.id.img_cake)
        val rl_evet_list:RelativeLayout=itemView.findViewById(R.id.rl_evet_list)
        val tv_product_currentprice:TextView=itemView.findViewById(R.id.tv_product_currentprice)
        val tv_avaragerating:TextView=itemView.findViewById(R.id.tv_avaragerating)
    }
}