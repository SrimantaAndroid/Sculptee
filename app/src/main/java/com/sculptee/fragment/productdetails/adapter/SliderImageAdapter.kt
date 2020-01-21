package com.sculptee.fragment.productdetails.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.sculptee.R
import com.sculptee.screens.home.HomeActivity
import com.sculptee.utils.zoomimagedialog.ZoomImageAlertDialog
import kotlin.collections.ArrayList

class SliderImageAdapter(val homeActivity: HomeActivity,val imagelist: ArrayList<String>) :PagerAdapter()

{
    private var inflater: LayoutInflater? = LayoutInflater.from(homeActivity);

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return imagelist.size

    }

   override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

   override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val imageLayout = inflater!!.inflate(R.layout.image_gallery_layout, view, false)!!

        val imageView = imageLayout.findViewById(R.id.img_image_slider) as ImageView
       // imageView.setImageResource(IMAGES.get(position))
       Glide.with(homeActivity)
           .load(imagelist.get(position))
           //  .apply(requestOptions
           .into(imageView)
        view.addView(imageLayout, 0)
       imageLayout.setOnClickListener {
         //  Toast.makeText(homeActivity,imagelist.get(position),Toast.LENGTH_LONG).show()
           ZoomImageAlertDialog(homeActivity,"",imagelist.get(position)) ?.show()
       }

        return imageLayout
    }

}