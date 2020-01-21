package com.sculptee.utils

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.view.MotionEvent

import android.os.Bundle
import android.view.GestureDetector
import android.widget.ImageView
import android.widget.ViewFlipper
import androidx.core.view.MotionEventCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.sculptee.R


class Launcher3DViewActivity:AppCompatActivity() {
    var mStartX: Int? = 0
    var mStartY: Int? = 0

    var mEndX: Int? = 0
    var mEndY: Int? = 0
    var mImageIndex: Int? = 0
    var m360DegreeImageView:ImageView?=null
    var  mViewFlipper:ViewFlipper?=null
    var imageArray= arrayOf("http://13.234.121.130/wp-content/uploads/2019/04/Cake-1_39.png",
        "http://13.234.121.130/wp-content/uploads/2019/04/Cake-1_37.png",
        "http://13.234.121.130/wp-content/uploads/2019/04/Cake-1_35.png",
        "http://13.234.121.130/wp-content/uploads/2019/04/Cake-1_33.png",
        "http://13.234.121.130/wp-content/uploads/2019/04/Cake-1_31.png",
        "http://13.234.121.130/wp-content/uploads/2019/04/Cake-1_29.png",
        "http://13.234.121.130/wp-content/uploads/2019/04/Cake-1_27.png",
        "http://13.234.121.130/wp-content/uploads/2019/04/Cake-1_25.png",
        "http://13.234.121.130/wp-content/uploads/2019/04/Cake-1_23.png",
        "http://13.234.121.130/wp-content/uploads/2019/04/Cake-1_21.png",
        "http://13.234.121.130/wp-content/uploads/2019/04/Cake-1_19.png",
        "http://13.234.121.130/wp-content/uploads/2019/04/Cake-1_17.png",
        "http://13.234.121.130/wp-content/uploads/2019/04/Cake-1_15.png",
        "http://13.234.121.130/wp-content/uploads/2019/04/Cake-1_13.png",
        "http://13.234.121.130/wp-content/uploads/2019/04/Cake-1_11.png",
        "http://13.234.121.130/wp-content/uploads/2019/04/Cake-1_09.png",
        "http://13.234.121.130/wp-content/uploads/2019/04/Cake-1_07.png",
        "http://13.234.121.130/wp-content/uploads/2019/04/Cake-1_05.png",
        "http://13.234.121.130/wp-content/uploads/2019/04/Cake-1_03.png",
        "http://13.234.121.130/wp-content/uploads/2019/04/Cake-1_01.png")

    var mGestureDetector: GestureDetector? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       // mContext = this
        setContentView(R.layout.activity_3d_view)
        m360DegreeImageView = findViewById(R.id.santafe3dview) as ImageView


         mViewFlipper =  findViewById(R.id.viewFlipper)
        for (i in 0 until imageArray.size) {
            val imageView = ImageView(this)

            val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(this)
                .asDrawable()
                .load(imageArray.get(i))
                .apply(
                    RequestOptions().transforms(
                        RoundedCorners(resources?.getDimension(R.dimen._3sdp)!!.toInt())
                    )
                )
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
        mGestureDetector = GestureDetector(this, customGestureDetector)
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = MotionEventCompat.getActionMasked(event)
        when (action) {
            MotionEvent.ACTION_DOWN -> {

                mStartX = event.x.toInt()
                mStartY = event.y.toInt()
                return true
            }

            MotionEvent.ACTION_MOVE -> {

                mEndX = event.x.toInt()
                mEndY = event.y.toInt()

                if (mEndX!! - mStartX!! > 10) {
                    mImageIndex=mImageIndex!!+1
                    if (mImageIndex!! > imageArray.size-1)
                        mImageIndex = 0
                        swipenext()
                        //shownextimage(mImageIndex!!)
                   // m360DegreeImageView!!.setImageLevel(mImageIndex!!)

                }
                if (mEndX!! - mStartX!! < -7) {
                    mImageIndex=mImageIndex!!-1
                    if (mImageIndex!! < 0)
                        mImageIndex = imageArray.size-1
                        swipeprevious()
                   // showprevious(mImageIndex!!)
                   // m360DegreeImageView!!.setImageLevel(mImageIndex!!)

                }
                mStartX = event.x.toInt()
                mStartY = event.y.toInt()
                return true
            }

            MotionEvent.ACTION_UP -> {
                mEndX = event.x.toInt()
                mEndY = event.y.toInt()

                return true
            }

            MotionEvent.ACTION_CANCEL -> return true

            MotionEvent.ACTION_OUTSIDE -> return true

            else -> return super.onTouchEvent(event)
        }
    }

    private fun shownextimage(mImageIndex: Int) {
        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(this)
            .load(imageArray.get(mImageIndex))
              .apply(requestOptions)
            .apply(
                RequestOptions().transforms(
                    CenterCrop(),
                    RoundedCorners(resources.getDimension(R.dimen._6sdp).toInt())
                )
            )
            .into(m360DegreeImageView)
    }

    private fun showprevious(mImageIndex: Int) {
        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(this)
            .load(imageArray.get(mImageIndex))
              .apply(requestOptions)
            .apply(
                RequestOptions().transforms(
                    CenterCrop(),
                    RoundedCorners(resources.getDimension(R.dimen._6sdp).toInt())
                )
            )
            .into(m360DegreeImageView)
    }

    internal inner class CustomGestureDetector : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {

            // Swipe left (next)
            if (e1.getX() > e2.getX()) {

               // swipenext()
            }

            // Swipe right (previous)
            if (e1.getX() < e2.getX()) {
               // swipeprevious()
            }

            return super.onFling(e1, e2, velocityX, velocityY)
        }
    }

    fun swipenext(){
        mViewFlipper?.setInAnimation(applicationContext, R.anim.left_in);
        mViewFlipper?.setOutAnimation(applicationContext, R.anim.left_out);
        mViewFlipper?.showNext()
    }

    fun swipeprevious(){
        mViewFlipper?.setInAnimation(applicationContext, R.anim.right_in);
        mViewFlipper?.setOutAnimation(applicationContext, R.anim.right_out);
        mViewFlipper?.showPrevious()
    }
}