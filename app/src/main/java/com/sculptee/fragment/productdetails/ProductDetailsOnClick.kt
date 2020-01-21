package com.sculptee.fragment.productdetails

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sculptee.R
import com.sculptee.screens.home.HomeActivity
import com.sculptee.screens.imageslider.ImageSliderActivity
import com.sculptee.utils.AppConstants
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sculptee.screens.sociallogin.SocialSignActivity
import com.sculptee.utils.customdropdownpopupadapter.DropDownListAdapter
import com.sculptee.utils.itemclickinterface.OnItemClickInterface
import com.sculptee.utils.preferenceconstant.PreferenceConstantParam
import com.sculptee.utils.sheardpreference.AppPreferenceHalper
import com.sculptee.utils.zoomimagedialog.ZoomImageAlertDialog

import android.os.AsyncTask
import android.view.MotionEvent
import android.widget.RatingBar
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.rts.commonutils_2_0.netconnection.ConnectionDetector
import com.sculptee.datastorage.CakeProduct
import com.sculptee.datastorage.RoomSingleton
import com.sculptee.network.ApiInterface
import com.sculptee.network.RetroFitApiService
import com.sculptee.utils.Alert
import com.sculptee.utils.customprogress.CustomProgressDialog
import okhttp3.ResponseBody
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProductDetailsOnClick:View.OnClickListener, View.OnTouchListener {


    var homeActivity: HomeActivity?=null
    var productDetailsViewBind: ProductDetailsViewBind?=null
    var productDetailsFragment: ProductDetailsFragment?=null
    var producttype:String?="withegg"

    private lateinit var mDb:RoomSingleton
     var x1: Float = 0.toFloat()
    var x2: Float = 0.toFloat()
    var MIN_DISTANCE = 150
    constructor(productDetailsFragment: ProductDetailsFragment, homeActivity: HomeActivity?, productDetailsViewBind: ProductDetailsViewBind){
        this.productDetailsViewBind=productDetailsViewBind
        this.homeActivity=homeActivity
        this.productDetailsFragment=productDetailsFragment
        setonclick()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setonclick() {
        productDetailsViewBind?.tv_description?.setOnClickListener(this)
        productDetailsViewBind?.tv_ingradient?.setOnClickListener(this)
        productDetailsViewBind?.cake_img4?.setOnClickListener(this)
        productDetailsViewBind?.tv_flovour?.setOnClickListener(this)
        productDetailsViewBind?.tv_weight?.setOnClickListener(this)
        productDetailsViewBind?.cake_img1?.setOnClickListener(this)
        productDetailsViewBind?.cake_img2?.setOnClickListener(this)
        productDetailsViewBind?.cake_img3?.setOnClickListener(this)
        productDetailsViewBind?.rl_heart?.setOnClickListener(this)
        productDetailsViewBind?.rl_addtocart?.setOnClickListener(this)
        productDetailsViewBind?.img_share?.setOnClickListener(this)
        productDetailsViewBind?.rl_radio_eggbased?.setOnClickListener(this)
        productDetailsViewBind?.rl_radio_eggless?.setOnClickListener(this)
        productDetailsViewBind?.btn_submitreview?.setOnClickListener(this)
        productDetailsViewBind?.chk_eggbased?.isChecked=true
        productDetailsViewBind?.with_sugger?.isChecked=false
        val context = homeActivity
        productDetailsViewBind?.ratingbar!!.setOnRatingBarChangeListener(object :
            RatingBar.OnRatingBarChangeListener{
            override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {
              //  Toast.makeText(homeActivity, p1.toString(),Toast.LENGTH_SHORT).show();
            }

        })
      //  productDetailsViewBind?.rl_imagedetails!!.setOnTouchListener(OnSwipeTouchListener(context!!) {


       // });



        productDetailsViewBind?.chk_eggbased?.setOnClickListener {

                productDetailsViewBind?.chk_eggbased?.isChecked=true
                productDetailsViewBind?.with_sugger?.isChecked=false
                productDetailsFragment?.selectedvariationid=0
              producttype="withegg"
            productDetailsFragment?.productprice=productDetailsFragment?.withegg_per_pound
            productDetailsViewBind?.tv_price!!.setText(productDetailsFragment?.withegg_per_pound+ homeActivity?.resources?.getString(R.string.perpound))

        }

        productDetailsViewBind?.with_sugger?.setOnClickListener {
                productDetailsViewBind?.chk_eggbased?.isChecked=false
                productDetailsViewBind?.with_sugger?.isChecked=true
                productDetailsFragment?.selectedvariationid=1
                producttype="Egg-less"
            productDetailsFragment?.productprice=productDetailsFragment?.eggless_per_pound
            productDetailsViewBind?.tv_price!!.setText(productDetailsFragment?.eggless_per_pound+homeActivity?.resources?.getString(R.string.perpound))



        }

       /* productDetailsViewBind?.chk_eggbased?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (productDetailsViewBind?.chk_eggbased?.isChecked==true){
                productDetailsViewBind?.chk_eggbased?.isChecked=false
                productDetailsViewBind?.with_sugger?.isChecked=true
            }else{
                productDetailsViewBind?.chk_eggbased?.isChecked=true
                productDetailsViewBind?.with_sugger?.isChecked=false
            }

            productDetailsFragment?.selectedvariationid=0
            producttype="withegg"
          *//*  if ( productDetailsViewBind?.chk_eggbased?.isChecked==false)
                 productDetailsViewBind?.chk_eggbased?.isChecked=true*//*
        }
        productDetailsViewBind?.with_sugger?.setOnCheckedChangeListener { buttonView, isChecked ->
          //  productDetailsViewBind?.chk_eggbased?.isChecked=false
            productDetailsFragment?.selectedvariationid=1
            producttype="Egg-less"
           *//* if ( productDetailsViewBind?.with_sugger?.isChecked==false)
            productDetailsViewBind?.with_sugger?.isChecked=true*//*
        }*/
/*
        productDetailsViewBind?.et_message?.addTextChangedListener(object :TextWatcher{
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(count>30){
                    productDetailsViewBind?.et_message?.isEnabled=false
                }else
                    productDetailsViewBind?.et_message?.isEnabled=true
            }

            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
        })*/
    }

    override fun onTouch(p0: View?, motion: MotionEvent?): Boolean {
       /* when(motion!!.action) {
            MotionEvent.ACTION_DOWN -> {
                x1 = motion.getX()
              }
             MotionEvent.ACTION_UP-> {
                 x2 = motion.getX();
                val  deltaX = x2 -x1;
                 if (Math.abs(deltaX) > MIN_DISTANCE) {
                     if (x2 > x1) {
                            Toast.makeText(homeActivity, "Left to Right swipe [Next]", Toast.LENGTH_SHORT).show ();
                         if (productDetailsFragment!!.pos!! < productDetailsFragment!!.imagelist.size) {
                             Glide.with(homeActivity)
                                 .load(productDetailsFragment!!.imagelist.get(productDetailsFragment!!.pos!!))
                                 .into(productDetailsViewBind?.img_details)

                             productDetailsFragment!!.pos = productDetailsFragment!!.pos!! + 1

                         }
                     }


                     // Right to left swipe action
                     else
                     {
                         Toast.makeText(homeActivity, "Right to left swipe action", Toast.LENGTH_SHORT).show ();

                         //if (productDetailsFragment!!.pos!! < productDetailsFragment!!.imagelist.size) {
                             Glide.with(homeActivity)
                                 .load(productDetailsFragment!!.imagelist.get(productDetailsFragment!!.imagelist.size!!-1))
                                 .into(productDetailsViewBind?.img_details)

                             productDetailsFragment!!.pos = productDetailsFragment!!.pos!! + 1

                        // }
                     }
                 } else {

                 }
             }
        }*/


        return true
    }

    override fun onClick(v: View?) {
       when(v?.id){
           R.id.rl_radio_eggbased->{
               productDetailsViewBind?.with_sugger?.isChecked=false
               productDetailsViewBind?.chk_eggbased!!.isChecked=true
               productDetailsFragment?.selectedvariationid=0
               productDetailsFragment?.productprice=productDetailsFragment?.withegg_per_pound
               productDetailsViewBind?.tv_price!!.setText(productDetailsFragment?.withegg_per_pound+ homeActivity?.resources?.getString(R.string.perpound))
               producttype="withegg"
           }
           R.id.rl_radio_eggless->{
               productDetailsViewBind?.chk_eggbased?.isChecked=false
               productDetailsViewBind?.with_sugger?.isChecked=true
               productDetailsFragment?.selectedvariationid=1
               productDetailsFragment?.productprice=productDetailsFragment?.eggless_per_pound
               productDetailsViewBind?.tv_price!!.setText(productDetailsFragment?.eggless_per_pound+homeActivity?.resources?.getString(R.string.perpound))
               producttype="Egg-less"
           }
          R.id.tv_description->{
              productDetailsViewBind?.ll_ingradients?.visibility=View.GONE
              productDetailsViewBind?.ll_description?.visibility=View.VISIBLE
              productDetailsViewBind?.tv_description?.setBackgroundColor(homeActivity?.resources!!.getColor(R.color.desc_selected_bg))
              productDetailsViewBind?.tv_ingradient?.setBackgroundColor(homeActivity?.resources!!.getColor(R.color.desc_un_elected_bg))

              productDetailsViewBind?.tv_description?.setTextColor(homeActivity?.resources!!.getColor(R.color.light_yellow))
              productDetailsViewBind?.tv_ingradient?.setTextColor(homeActivity?.resources!!.getColor(R.color.black))
          }
           R.id.tv_ingradient->{
               productDetailsViewBind?.ll_ingradients?.visibility=View.VISIBLE
               productDetailsViewBind?.ll_description?.visibility=View.GONE
               productDetailsViewBind?.tv_description?.setBackgroundColor(homeActivity?.resources!!.getColor(R.color.desc_un_elected_bg))
               productDetailsViewBind?.tv_ingradient?.setBackgroundColor(homeActivity?.resources!!.getColor(R.color.desc_selected_bg))

               productDetailsViewBind?.tv_description?.setTextColor(homeActivity?.resources!!.getColor(R.color.black))
               productDetailsViewBind?.tv_ingradient?.setTextColor(homeActivity?.resources!!.getColor(R.color.light_yellow))

           }
           R.id.cake_img4->{
               if(productDetailsFragment?.Imagrarraylist?.size!! >0) {
                   val i = Intent(homeActivity, ImageSliderActivity::class.java)
                   i.putStringArrayListExtra(AppConstants.INTENTIMAGRLIST, productDetailsFragment?.Imagrarraylist)
                   i.putExtra(AppConstants.INTENT_productName, productDetailsViewBind?.tv_product_name?.text.toString())
                  homeActivity?.startActivity(i)
                  // homeActivity.let{ it?.let { it1 -> ThreeSixtyImageAlertDialog(it1,productDetailsViewBind?.tv_product_name?.text.toString(),productDetailsFragment?.Imagrarraylist!!) } }?.show()
               }else {
                   if(productDetailsViewBind?.cake_img4!!.visibility==View.VISIBLE)
                       homeActivity?.let { ZoomImageAlertDialog(it,productDetailsViewBind?.tv_product_name?.text.toString(),productDetailsFragment?.imgsrc4!!) }?.show()
                   // Alert.showalert(homeActivity!!,homeActivity!!.resources?.getString(R.string.no360image)!!)
                   // Toast.makeText(homeActivity,homeActivity?.resources?.getString(R.string.no360image),Toast.LENGTH_LONG).show()
               }
           }
         R.id.tv_flovour->{
             openpopupdrop_down()
         }

           R.id.tv_weight->{
               openpopupdrop_downfoeweight()
           }
           R.id.cake_img1->{
             //  val map = homeActivity?.let { takeScreenShot(it) }
              // val fast = map?.let { fastblur(it, 10) }
             //  val draw = BitmapDrawable(homeActivity?.getResources(), fast)
              //  homeActivity?.getWindow()?.setBackgroundDrawable(draw)
             //  productDetailsFragment?.showDialogimagezoom(productDetailsViewBind?.tv_product_name?.text.toString(),productDetailsFragment?.imgsrc1!!)
               if(productDetailsViewBind?.cake_img1!!.visibility==View.VISIBLE)
               homeActivity?.let { ZoomImageAlertDialog(it,productDetailsViewBind?.tv_product_name?.text.toString(),productDetailsFragment?.imgsrc1!!) }?.show()
           }
           R.id.cake_img2->{
               if(productDetailsViewBind?.cake_img2!!.visibility==View.VISIBLE)
               homeActivity?.let { ZoomImageAlertDialog(it,productDetailsViewBind?.tv_product_name?.text.toString(),productDetailsFragment?.imgsrc2!!) }?.show()

           }
           R.id.cake_img3->{
               if(productDetailsViewBind?.cake_img3!!.visibility==View.VISIBLE)
               homeActivity?.let { ZoomImageAlertDialog(it,productDetailsViewBind?.tv_product_name?.text.toString(),productDetailsFragment?.imgsrc3!!) }?.show()

           }
           R.id.rl_addtocart->{
            productDetailsFragment?.addtocart=true
               if(AppPreferenceHalper.read(PreferenceConstantParam.isLogIn,false)==false){
                  // callsavemethodinroom();
                  // homeActivity?.startActivity(Intent(homeActivity,SocialSignActivity::class.java))
                   //addcart()
                   addcartforgaust()

               }else{
                  // productDetailsFragment?.callAddtocartApi()
                   addcart()
               }


           }
           R.id.rl_heart->{
               productDetailsFragment?.addtofevorite=true
               if(AppPreferenceHalper.read(PreferenceConstantParam.isLogIn,false)==false){
                   homeActivity?.startActivity(Intent(homeActivity,SocialSignActivity::class.java))

               }
               else
                 productDetailsFragment?.callApiformakefavourite();
           }
           R.id.img_share->{
               productDetailsFragment?.share()
           }

           R.id.btn_submitreview->{
               val ratingvalue = productDetailsViewBind!!.ratingbar!!.getRating() as Float
               val review:String=productDetailsViewBind!!.et_review!!.text.toString()
             //  Toast.makeText(homeActivity, ratingvalue.toString(),Toast.LENGTH_SHORT).show();
               if (ratingvalue>0 ) {
                    if (!review.equals(""))
                       callApiforratingandreview(ratingvalue!!, review!!)
                   else
                        Alert.showalert(homeActivity!!,"Please Enter Review.")

               }else
                   Alert.showalert(homeActivity!!,"Enter Select Rating.")
           }

       }
    }

    private fun callApiforratingandreview(ratingvalue: Float, review: String) {
        if (ConnectionDetector.isConnectingToInternet(homeActivity)) {
            val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
            this!!.homeActivity?.let { customProgress.showProgress(it, "Please Wait..", false) }
            val apiInterface = RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)

            var loginjson = JSONObject();
            try {

                loginjson.put("product_id",AppPreferenceHalper.read(PreferenceConstantParam.PRODUCTID, 0))
                loginjson.put("review",review)
                loginjson.put("reviewer",AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_NAME, ""))
                loginjson.put("reviewer_email",AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_EMAIL, ""))
                loginjson.put("rating",ratingvalue)
                loginjson.put("status","hold")
                //  loginjson.put("billing_phone","")
            }catch (e: Exception){
                e.printStackTrace()
            }
            var obj: JSONObject = loginjson
            var jsonParser: JsonParser = JsonParser()
            var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
            val callapi = apiInterface?.reviewrating(gsonObject)
            callapi!!.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    customProgress.hideProgress()
                    if(response.isSuccessful){
                        productDetailsViewBind!!.et_review!!.setText("")
                        Alert.showalert(homeActivity!!,"Wait 24 hours for admin approval")

                    }else
                        Alert.showalert(homeActivity!!,"Internal server error.")
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    customProgress.hideProgress()
                   // Alert.showalert(homeActivity!!,"Internal server error.")
                }
            })
        }else
            Alert.showalert(homeActivity!!,homeActivity!!.resources.getString(R.string.nointernet))

    }

    private fun addcartforgaust() {
        if(productDetailsFragment?.selectedvariationid==0){
            productDetailsFragment?.vari_id= productDetailsFragment?.variationlist?.get(0)?.variation_id
            productDetailsFragment?.vari_name=productDetailsFragment?.variationlist?.get(0)?.attribute_pa_withegg_eggless
        }else if(productDetailsFragment?.selectedvariationid==1){
            productDetailsFragment?.vari_id=productDetailsFragment?.variationlist?.get(1)?.variation_id
            productDetailsFragment?.vari_name=productDetailsFragment?.variationlist?.get(1)?.attribute_pa_withegg_eggless
        }
        val rnds = (0..1000).random()
        mDb = RoomSingleton.getInstance(homeActivity!!.applicationContext)
        val cakeProduct=CakeProduct(rnds,productDetailsFragment?.product_id!!.toString(),AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_ID,"0")!!,productDetailsViewBind?.tv_product_name?.text.toString(),productDetailsViewBind?.et_message?.text.toString(),
            productDetailsFragment?.imgsrc1!!,productDetailsFragment?.productprice!!.toInt(),productDetailsViewBind?.tv_flovour?.text.toString(),producttype!! ,productDetailsFragment?.weight!!,productDetailsFragment?.productprice!!.toInt()*productDetailsFragment?.weight!!,
            productDetailsFragment?.vari_id!!,productDetailsFragment?.vari_name!!,productDetailsFragment?.withegg_per_pound!!,productDetailsFragment?.eggless_per_pound!!,1)
        doAsync {
            mDb.CakeProductDao().insert(cakeProduct)
            uiThread {
                Toast.makeText(homeActivity,"This Product is added in your cart.",Toast.LENGTH_LONG).show()
                homeActivity?.loadcartcountvalue()
                productDetailsFragment?.addtocart=false
            }
        }
    }

    private fun callsavemethodinroom() {
        class AddtoCart: AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void?): Void? {

               /* val product=Product()
                product.setproductid(9)
                product.setproductmessage("wqhgdiup")
                product.setproductname("wqhdWD")*/
                return  null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
            }
        }
        val addtoCart=AddtoCart()
        addtoCart.execute()

    }

    private fun openpopupdrop_downfoeweight() {
        val popUpView = homeActivity?.getLayoutInflater()?.inflate(com.sculptee.R.layout.drop_down_layout, null)
        val  listPopupWindow = PopupWindow(popUpView, homeActivity?.resources?.getDimension(R.dimen._90sdp)!!.toInt(), homeActivity?.resources?.getDimension(R.dimen._150sdp)!!.toInt(), true
           // ViewGroup.LayoutParams.WRAP_CONTENT, false
        )
        val reclyerview_dropdown:RecyclerView=popUpView!!.findViewById(R.id.reclyerview_dropdown)
        val dropDownListAdapter:DropDownListAdapter= DropDownListAdapter(homeActivity,productDetailsFragment?.cakeweightarraylist,object :OnItemClickInterface{
            override fun OnClickItem(position: Int) {
                super.OnClickItem(position)
                productDetailsFragment?.weight=position+1
                productDetailsViewBind?.tv_weight?.setText(productDetailsFragment?.cakeweightarraylist?.get(position))
              //  productDetailsViewBind?.tv_weightval?.visibility=View.VISIBLE
               // productDetailsViewBind?.tv_weightval?.setText("10 persons")
                listPopupWindow.dismiss()
            }
        })
        reclyerview_dropdown?.layoutManager=LinearLayoutManager(homeActivity)
        reclyerview_dropdown?.adapter=dropDownListAdapter
        listPopupWindow?.showAsDropDown(productDetailsViewBind?.tv_weight);

    }


    private fun openpopupdrop_down() {
        val popUpView = homeActivity?.getLayoutInflater()?.inflate(com.sculptee.R.layout.drop_down_layout, null)
        val  listPopupWindow = PopupWindow(popUpView, homeActivity?.resources?.getDimension(R.dimen._90sdp)!!.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT, true)
        val reclyerview_dropdown:RecyclerView=popUpView!!.findViewById(R.id.reclyerview_dropdown)
        val dropDownListAdapter:DropDownListAdapter= DropDownListAdapter(homeActivity,productDetailsFragment?.cakeweFLAVOURarraylist,object :OnItemClickInterface{
            override fun OnClickItem(position: Int) {
                super.OnClickItem(position)
                productDetailsFragment?.flavour=productDetailsFragment?.cakeweFLAVOURarraylist?.get(position);
                productDetailsViewBind?.tv_flovour?.setText(productDetailsFragment?.cakeweFLAVOURarraylist?.get(position))
                productDetailsViewBind?.tv_ingradient_text!!.setText(productDetailsFragment!!.flavourlist.get(position).description)
                productDetailsViewBind?.ll_ingradients?.visibility=View.VISIBLE
                productDetailsViewBind?.ll_description?.visibility=View.GONE
                productDetailsViewBind?.tv_description?.setBackgroundColor(homeActivity?.resources!!.getColor(R.color.desc_un_elected_bg))
                productDetailsViewBind?.tv_ingradient?.setBackgroundColor(homeActivity?.resources!!.getColor(R.color.desc_selected_bg))

                productDetailsViewBind?.tv_description?.setTextColor(homeActivity?.resources!!.getColor(R.color.black))
                productDetailsViewBind?.tv_ingradient?.setTextColor(homeActivity?.resources!!.getColor(R.color.light_yellow))
                listPopupWindow.dismiss()
            }
        })
        reclyerview_dropdown?.layoutManager=LinearLayoutManager(homeActivity)
        reclyerview_dropdown?.adapter=dropDownListAdapter
        listPopupWindow?.showAsDropDown(productDetailsViewBind?.tv_flovour);
    }

public fun addcart(){
    if(productDetailsFragment?.selectedvariationid==0){
        productDetailsFragment?.vari_id= productDetailsFragment?.variationlist?.get(0)?.variation_id
        productDetailsFragment?.vari_name=productDetailsFragment?.variationlist?.get(0)?.attribute_pa_withegg_eggless
    }else if(productDetailsFragment?.selectedvariationid==1){
        productDetailsFragment?.vari_id=productDetailsFragment?.variationlist?.get(1)?.variation_id
        productDetailsFragment?.vari_name=productDetailsFragment?.variationlist?.get(1)?.attribute_pa_withegg_eggless
    }
    val rnds = (0..1000).random()
    mDb = RoomSingleton.getInstance(homeActivity!!.applicationContext)
    val cakeProduct=CakeProduct(rnds,productDetailsFragment?.product_id!!.toString(),AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_ID,"0")!!,productDetailsViewBind?.tv_product_name?.text.toString(),productDetailsViewBind?.et_message?.text.toString(),
        productDetailsFragment?.imgsrc1!!,productDetailsFragment?.productprice!!.toInt(),productDetailsViewBind?.tv_flovour?.text.toString(),producttype!! ,productDetailsFragment?.weight!!,productDetailsFragment?.productprice!!.toInt()*productDetailsFragment?.weight!!,
        productDetailsFragment?.vari_id!!,productDetailsFragment?.vari_name!!,productDetailsFragment?.withegg_per_pound!!,productDetailsFragment?.eggless_per_pound!!,1)
    doAsync {
        mDb.CakeProductDao().insert(cakeProduct)

        uiThread {
            Toast.makeText(homeActivity,"  This Product is added in your cart.",Toast.LENGTH_LONG).show()
            homeActivity?.loadcartcountvalue()
            productDetailsFragment?.addtocart=false
        }
    }
    }


}