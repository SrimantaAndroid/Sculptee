package com.sculptee.screens.imageslider.adapter

import android.view.LayoutInflater
import android.view.View
import androidx.viewpager.widget.PagerAdapter
import android.widget.LinearLayout
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sculptee.R
import com.sculptee.screens.imageslider.ImageSliderActivity



class MyCustomPagerAdapter(
   val imageSliderActivity: ImageSliderActivity,
   val  arraylist_Url: ArrayList<String>
) :PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun getCount(): Int {
       return arraylist_Url.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = LayoutInflater.from(imageSliderActivity).inflate(R.layout.pager_item, container, false)

        val imageView = itemView.findViewById(R.id.imageView) as ImageView
        //imageView.setImageResource(arraylist_Url.get(position))
        Glide.with(imageSliderActivity)
            .load(arraylist_Url.get(position))
            //  .apply(requestOptions)
            .into(imageView)
        container.addView(itemView)

        //listening to image click
        imageView.setOnClickListener(View.OnClickListener {
            Toast.makeText(
                imageSliderActivity,
                "you clicked image " + (position + 1),
                Toast.LENGTH_LONG
            ).show()
        })

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

}