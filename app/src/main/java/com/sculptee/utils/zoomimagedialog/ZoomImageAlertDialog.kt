package com.sculptee.utils.zoomimagedialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.otaliastudios.zoom.ZoomImageView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.R
import com.sculptee.screens.home.HomeActivity
import com.sculptee.utils.AppUtils.Companion.fastblur
import com.sculptee.utils.AppUtils.Companion.takeScreenShot

class ZoomImageAlertDialog(val homeactivity: HomeActivity,val headertext:String,val imgurl:String) : Dialog(homeactivity,R.style.Transparent) {
   var deviceResolution:DeviceResolution?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val view = homeactivity.getLayoutInflater().inflate(R.layout.custom_image_zoom_layout, null)
        setContentView(view)
        setCanceledOnTouchOutside(true)
        setCancelable(true)
       // window.setBackgroundDrawable(ColorDrawable(Color.alpha(0.1.toInt())))
        deviceResolution = DeviceResolution(homeactivity)
        val wmlp = window.attributes
        wmlp.width = deviceResolution?.getWidth(1.0)!!
        wmlp.height = deviceResolution?.getHeight(1.0)!!
        window.attributes = wmlp
        wmlp.dimAmount = 1.8f
       // getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        val image = view .findViewById(R.id.zoom_image) as ZoomImageView
        val rl_zoomimage:RelativeLayout=view.findViewById(R.id.rl_zoomimage)
       // rl_zoomimage.alpha=.5F
       // rl_zoomimage.getBackground().setAlpha(100);
        val tv_product_title = view .findViewById(R.id.tv_product_title) as TextView
        tv_product_title.setTypeface(deviceResolution?.getMavenProBold(homeactivity))
        tv_product_title.setText(headertext)
        val img_back = view .findViewById(R.id.img_back) as ImageView
        Glide.with(homeactivity).load(imgurl)
                .apply(
                 RequestOptions().transforms(CenterCrop(), RoundedCorners(homeactivity.resources.getDimension(R.dimen._6sdp).toInt())))
                .into(image)

        var  param: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(deviceResolution?.getWidth(0.85)!!,deviceResolution?.getHeight(1.0)!!/2.toInt())
        param.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
         param.setMargins(deviceResolution?.getHeight(0.004)!!,
             deviceResolution?.getHeight(0.05)!!, deviceResolution?.getHeight(0.004)!!, deviceResolution?.getHeight(0.12)!!)
         image.layoutParams=param
         val map = homeactivity?.let { takeScreenShot(it) }
         val bmp = map?.let { fastblur(it, homeactivity.resources.getDimension(R.dimen._3sdp).toInt()) }
         val draw = BitmapDrawable(homeactivity?.getResources(), bmp)
         draw.setAlpha(255);
         window.setBackgroundDrawable(draw)
        //draw.getBackground().setAlpha(100);
         //getWindow().setBackgroundDrawable(ColorDrawable(0));
        rl_zoomimage.setOnClickListener {
            dismiss()
        }


    }


}