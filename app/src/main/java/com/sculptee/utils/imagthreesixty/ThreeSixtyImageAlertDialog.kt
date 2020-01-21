package com.sculptee.utils.imagthreesixty

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.Window
import android.view.WindowManager
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.otaliastudios.zoom.ZoomImageView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.R
import com.sculptee.screens.home.HomeActivity
import com.sculptee.utils.AppUtils.Companion.fastblur
import com.sculptee.utils.AppUtils.Companion.takeScreenShot

class ThreeSixtyImageAlertDialog(val homeactivity: HomeActivity, val headertext:String, val imgurls:ArrayList<String>) : Dialog(homeactivity,R.style.blurthemeslide) {
   var deviceResolution:DeviceResolution?=null
    var arraylist_Url= ArrayList<String>()
    var  mViewFlipper: ViewFlipper?=null
    var mGestureDetector: GestureDetector? = null
    var imageListloaduscuss=ArrayList<Boolean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val view = homeactivity.getLayoutInflater().inflate(R.layout.three_sixty_alert_layout, null)
        setContentView(view)
        setCanceledOnTouchOutside(false)
        setCancelable(false)


       // window.setBackgroundDrawable(ColorDrawable(Color.alpha(0.1.toInt())))
        deviceResolution = DeviceResolution(homeactivity)
        val wmlp = window.attributes
        wmlp.width = deviceResolution?.getWidth(1.0)!!
        wmlp.height = deviceResolution?.getHeight(1.0)!!
        window.attributes = wmlp
        wmlp.dimAmount = 0.1f
       // getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
       // val image = view .findViewById(R.id.zoom_image) as ZoomImageView
        val rl_imageslider:RelativeLayout=view.findViewById(R.id.rl_imageslider)
        val tv_product_title = view .findViewById(R.id.tv_product_title) as TextView
        val mViewFlipper:ViewFlipper=view.findViewById(R.id.viewFlipper)
        tv_product_title.setTypeface(deviceResolution?.getMavenProBold(homeactivity))
        tv_product_title.setText(headertext)

        var  param: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(deviceResolution?.getWidth(0.85)!!,deviceResolution?.getHeight(1.0)!!/2.toInt())
        param.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
         param.setMargins(deviceResolution?.getHeight(0.004)!!,
             deviceResolution?.getHeight(0.05)!!, deviceResolution?.getHeight(0.004)!!, deviceResolution?.getHeight(0.12)!!

         )
        mViewFlipper?.layoutParams=param
         val map = homeactivity?.let { takeScreenShot(it) }
         val bmp = map?.let { fastblur(it, homeactivity.resources.getDimension(R.dimen._3sdp).toInt()) }
         val draw = BitmapDrawable(homeactivity?.getResources(), bmp)
        // draw.setAlpha(250);
         window.setBackgroundDrawable(draw)
         //getWindow().setBackgroundDrawable(ColorDrawable(0));
        rl_imageslider.setOnClickListener {
          //  dismiss()
        }

        for (i in 0 until imgurls.size) {
            val imageView = ImageView(homeactivity)
            imageListloaduscuss.add(false)
            val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(homeactivity)
                .asDrawable()
                .load(imgurls.get(i))
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ) = false

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        (imageView as? ImageView)?.setImageDrawable(resource)
                        mViewFlipper?.showNext()
                        return true
                    }
                })
                .submit()
            /*  Glide.with(this).
                  load(arraylist_Url.get(i))
                  .apply((requestOptions))
                  .listener(object : RequestListener<Drawable> {
                      override fun onLoadFailed(
                          e: GlideException?,
                          model: Any?,
                          target: Target<Drawable>?,
                          isFirstResource: Boolean
                      ): Boolean {
                          return false
                      }

                      override fun onResourceReady(
                          resource: Drawable?,
                          model: Any?,
                          target: Target<Drawable>?,
                          dataSource: DataSource?,
                          isFirstResource: Boolean
                      ): Boolean {
                          mViewFlipper?.setImageDrawable(resource)
                          return true
                      }
                  })

                  *//*.listener(object :RequestListener<Drawable>{
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        imageListloaduscuss.add(i,true)
                        for(j in 0 until imageListloaduscuss.size) {
                            if (imageListloaduscuss.get(j)==true && j==imageListloaduscuss.size-1){
                                progress?.dismiss()
                            }
                        }
                        return  true
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })*//*
                .into(imageView)*/
            mViewFlipper!!.addView(imageView)
        }

        val customGestureDetector = CustomGestureDetector()
        mGestureDetector = GestureDetector(homeactivity, customGestureDetector)
    }

    internal inner class CustomGestureDetector : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {

            // Swipe left (next)
            if (e1.getX() > e2.getX()) {

                swipenext()
            }

            // Swipe right (previous)
            if (e1.getX() < e2.getX()) {
                swipeprevious()
            }

            return super.onFling(e1, e2, velocityX, velocityY)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mGestureDetector?.onTouchEvent(event);
        return super.onTouchEvent(event)
    }

    fun swipenext(){
        mViewFlipper?.setInAnimation(homeactivity, R.anim.left_in);
        mViewFlipper?.setOutAnimation(homeactivity, R.anim.left_out);
        mViewFlipper?.showNext()
    }

    fun swipeprevious(){
        mViewFlipper?.setInAnimation(homeactivity, R.anim.right_in);
        mViewFlipper?.setOutAnimation(homeactivity, R.anim.right_out);
        mViewFlipper?.showPrevious()
    }


}