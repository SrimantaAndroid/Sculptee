package com.sculptee.screens.imageslider

import android.app.ProgressDialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import com.sculptee.R

import com.bumptech.glide.Glide
import android.view.MotionEvent
import android.view.GestureDetector
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.MotionEventCompat
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.screens.imageslider.adapter.MyCustomPagerAdapter
import com.sculptee.utils.AppConstants
import com.sculptee.utils.customviewview.ProductShowCaseWebView
import kotlinx.android.synthetic.main.activity_image_slide.*


class ImageSliderActivity:AppCompatActivity() {
    var arraylist_Url= ArrayList<String>()
    var  mViewFlipper:ViewFlipper?=null
    var mGestureDetector: GestureDetector? = null
    var imageListloaduscuss=ArrayList<Boolean>()
    var progress: ProgressDialog?=null
    var tv_product_title:TextView?=null
    var img_back:ImageView?=null
    var rlviewflipper:RelativeLayout?=null
    var deviceResolution:DeviceResolution?=null
     var myCustomPagerAdapter: MyCustomPagerAdapter?=null;
    var mStartX: Int? = 0
    var mStartY: Int? = 0

    var mEndX: Int? = 0
    var mEndY: Int? = 0
    var tv_loading:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val convertview=LayoutInflater.from(this).inflate(R.layout.activity_image_slide,null)
        setContentView(convertview)
       // getWindow().setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        deviceResolution= DeviceResolution(this)
        tv_product_title=findViewById(R.id.tv_product_title)
        tv_loading=findViewById(R.id.tv_loading)
        img_back=findViewById(R.id.img_back)
        rlviewflipper=findViewById(R.id.rlviewflipper)
        arraylist_Url=this.intent.getStringArrayListExtra(AppConstants.INTENTIMAGRLIST)
        tv_product_title?.setTypeface(deviceResolution?.getMavenProBold(this))
        tv_loading?.setTypeface(deviceResolution?.getMavenProBold(this))
        val productname:String=this.intent.getStringExtra(AppConstants.INTENT_productName)
        tv_product_title?.setText(productname)
        img_back?.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
              finish()
            }

        })
        val rl_slide:RelativeLayout=findViewById(R.id.rl_slide)
        rl_slide.setOnClickListener { v: View? ->
            finish()
        }
        rlviewflipper!!.setOnClickListener {
            finish()
        }
/*
        var  param: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(deviceResolution?.getWidth(1.0)!!,deviceResolution?.getHeight(1.0)!!/2.toInt())
        param.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        param.setMargins(deviceResolution?.getHeight(0.004)!!,
            deviceResolution?.getHeight(0.05)!!, deviceResolution?.getHeight(0.004)!!, deviceResolution?.getHeight(0.12)!!

        )
        mViewFlipper?.layoutParams=param*/

        progress= ProgressDialog(this)
        progress?.setMessage("loading..")
       // progress?.show()
        mViewFlipper = convertview?. findViewById(R.id.viewFlipper)!!
        for (i in 0 until arraylist_Url.size) {
            val imageView = ImageView(this)
            imageListloaduscuss.add(false)

            val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(this)
                .asDrawable()
                .load(arraylist_Url.get(i))
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
                        if(i==arraylist_Url.size-1){
                            tv_loading!!.visibility=View.GONE
                            mViewFlipper?.visibility=View.VISIBLE
                        }
                       // mViewFlipper?.showNext()
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

      //  setpagerview()
       // showinwebvview()
    }

    private fun showinwebvview() {
        var imagesTag360:String ?=null
        val wv = findViewById(R.id.product_show) as ProductShowCaseWebView
        for (i in 0 until arraylist_Url.size) {
            imagesTag360= arraylist_Url.get(i)
        }


        /* For Showing Images from image url just use the image url in the src field*/

//        for(int i=0;i<imageLength;i++)
//        {
//            imagesTag360=imagesTag360+"<img src=\"http://imageurl.com/image1_.jpg\"/>" ;
//        }





        wv.loadDataWithBaseURL(
            "", imagesTag360!!, "text/html", "UTF-8", null
        )

    }

    private fun setpagerview() {
        var viewpager:ViewPager=findViewById(R.id.viewpager)
        myCustomPagerAdapter= MyCustomPagerAdapter(this,arraylist_Url)
        viewpager.adapter=myCustomPagerAdapter

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
      // mGestureDetector?.onTouchEvent(event);
      //  val  action:Int = MotionEventCompat.getActionMasked(event);
      // return super.onTouchEvent(event)

       val action = MotionEventCompat.getActionMasked(event)
        when (action) {
            MotionEvent.ACTION_DOWN -> {

                mStartX = event!!.x.toInt()
                mStartY = event.y.toInt()
                return true
            }

            MotionEvent.ACTION_MOVE -> {

                if (event != null) {
                    mEndX = event.x.toInt()
                }
                mEndY = event!!.y.toInt()

                if (mEndX!! - mStartX!! > 5) {
         /*    mImageIndex=mImageIndex!!+1
                    if (mImageIndex!! > imageArray.size-1)
                        mImageIndex = 0*/
                    swipenext()
                    //shownextimage(mImageIndex!!)
                    // m360DegreeImageView!!.setImageLevel(mImageIndex!!)

                }
                if (mEndX!! - mStartX!! < -5) {
             /*   mImageIndex=mImageIndex!!-1
                    if (mImageIndex!! < 0)
                        mImageIndex = imageArray.size-1*/
                    swipeprevious()
                    // showprevious(mImageIndex!!)
                    // m360DegreeImageView!!.setImageLevel(mImageIndex!!)

                }
                mStartX = event.x.toInt()
                mStartY = event.y.toInt()
                return true
            }

            MotionEvent.ACTION_UP -> {
                mEndX = event!!.x.toInt()
                mEndY = event.y.toInt()

                return true
            }

            MotionEvent.ACTION_CANCEL -> return true

            MotionEvent.ACTION_OUTSIDE -> return true

            else -> return super.onTouchEvent(event)
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