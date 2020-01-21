package com.sculptee.fragment.event.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.R
import com.sculptee.model.events.EventModel
import com.sculptee.screens.home.HomeActivity
import com.sculptee.utils.itemclickinterface.OnItemClickInterface
import android.view.animation.LinearInterpolator
import android.view.animation.Animation
import android.view.animation.TranslateAnimation


class EventAdapter(val homeActivity: HomeActivity?, var eventlist: ArrayList<EventModel>,  var onitemClickInterFace: OnItemClickInterface?) : RecyclerView.Adapter<EventAdapter.EventItemViewHolder>() {
    var  deviceResolution:DeviceResolution?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventItemViewHolder {
        deviceResolution= DeviceResolution(homeActivity)
        val view=LayoutInflater.from(homeActivity).inflate(R.layout.event_list_item,null)
        return EventItemViewHolder(view)
    }

    override fun getItemCount(): Int {
       return  eventlist.size
    }

    override fun onBindViewHolder(holder: EventItemViewHolder, position: Int) {
        holder.tv_event_name?.setTypeface(deviceResolution?.getMavenProBold(homeActivity))
        holder.tv_event_name.setText(eventlist.get(position).name)
        Glide.with(homeActivity).load(eventlist.get(position).imgurl).into( holder.img_event)
        holder.rl_itembg.setOnClickListener(View.OnClickListener {
            onitemClickInterFace?.OnClickItem(position)
        })
        var mAnimation :Animation= TranslateAnimation(
            TranslateAnimation.ABSOLUTE, 0f,
            TranslateAnimation.ABSOLUTE, 0.023f,
            TranslateAnimation.RELATIVE_TO_PARENT, 0f,
            TranslateAnimation.RELATIVE_TO_PARENT, 0.023f
        )
        mAnimation.setDuration(500)
        mAnimation.setRepeatCount(-1)
        mAnimation.setRepeatMode(Animation.REVERSE)
        mAnimation.setInterpolator(LinearInterpolator())
        holder.img_event.animation=mAnimation

        var mAnimationshad :Animation= TranslateAnimation(
            TranslateAnimation.ABSOLUTE, 0.03f,
            TranslateAnimation.ABSOLUTE, 0f,
            TranslateAnimation.RELATIVE_TO_PARENT, 0.03f,
            TranslateAnimation.RELATIVE_TO_PARENT, 0f
        )
        mAnimationshad.setDuration(500)
        mAnimationshad.setRepeatCount(-1)
        mAnimationshad.setRepeatMode(Animation.REVERSE)
        mAnimationshad.setInterpolator(LinearInterpolator())
        holder.imgshadow.animation=mAnimationshad
    }

    class EventItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val img_event:ImageView=itemView.findViewById(R.id.img_event)
        val rl_itembg:RelativeLayout=itemView.findViewById(R.id.rl_itembg)
        var tv_event_name:TextView=itemView.findViewById(R.id.tv_event_name)
        var imgshadow:ImageView=itemView.findViewById(R.id.imgshadow)


    }
}