package com.sculptee.screens.imageslider.adapter

import android.view.LayoutInflater
import android.view.View
import androidx.viewpager.widget.PagerAdapter
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.sculptee.R
import com.sculptee.screens.imageslider.ImageSliderActivity





class ImageAdapter(var param: ImageSliderActivity?, val arraylist_Url: ArrayList<String>) :PagerAdapter() {
    var inflater: LayoutInflater? = null
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ViewGroup
    }

    override fun getCount(): Int {
       return arraylist_Url.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(param).inflate(R.layout.row_imageadapter, null)
        val image:ImageView=view.findViewById(R.id.imageview_machin_image)
        Glide.with(param).load(arraylist_Url.get(position)).into(image)
        container.addView(view)
        return view

    }


}