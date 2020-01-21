package com.sculptee.fragment.productdetails

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.rts.commonutils_2_0.netconnection.ConnectionDetector
import com.sculptee.R
import com.sculptee.fragment.productdetails.adapter.CommandListAdapter
import com.sculptee.fragment.productdetails.adapter.SliderImageAdapter
import com.sculptee.model.flavour.FlavourModel
import com.sculptee.model.review.ProductCommand
import com.sculptee.model.variation.ProductVariation
import com.sculptee.network.ApiInterface
import com.sculptee.network.RetroFitApiService
import com.sculptee.screens.home.HomeActivity
import com.sculptee.utils.Alert
import com.sculptee.utils.apprater.AppRater
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.sculptee.utils.preferenceconstant.PreferenceConstantParam
import com.sculptee.utils.sheardpreference.AppPreferenceHalper
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class ProductDetailsFragment:Fragment() {
    var homeActivity:HomeActivity?=null
    var progress:ProgressDialog?=null
    var productDetailsViewBind:ProductDetailsViewBind?=null
    var productDetailsOnClick:ProductDetailsOnClick?=null
    public val Imagrarraylist=ArrayList<String>()
    public val cakeweightarraylist=ArrayList<String>()
    public val cakeweFLAVOURarraylist=ArrayList<String>()
    public var flavourlist=ArrayList<FlavourModel>()
    public var imgsrc1: String? = ""
    public var imgsrc2: String? = ""
    public  var imgsrc3: String? = ""
    public var imgsrc4: String? = ""
    public  var product_id:Int?=0
    public  var productprice:String?=""
   // public var eggless_per_pound_price:String?=""
   // public var withegg_per_pound:String?=""

    public var weight:Int?=1
    public var flavour:String?=""
    public var withegg_per_pound:String?=""
    public var eggless_per_pound:String?=""
    val variationlist=ArrayList<ProductVariation>()
    public var imagelist=ArrayList<String>()
    public var selectedvariationid:Int=0
    public  var vari_id:Int?=0
    public   var vari_name:String?=""
    var addtocart:Boolean=false
    var addtofevorite:Boolean=false
    var commandArrayList=ArrayList<ProductCommand>()
    var commandListAdapter:CommandListAdapter?=null

    var pos:Int?=0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeActivity=activity as HomeActivity?
        homeActivity!!.loadeventsidemenu()
        val view=LayoutInflater.from(homeActivity).inflate(R.layout.product_details_layout,null)
        homeActivity!!.homeActivityViewBind!!.tv_headertext?.setText(AppPreferenceHalper.read(PreferenceConstantParam.EVENTNAME,""))
        productDetailsViewBind= ProductDetailsViewBind(homeActivity,view)
        productDetailsOnClick= ProductDetailsOnClick(this,homeActivity, productDetailsViewBind!!)
        callApiforflavour()
        callApiforproductdetails();
      //  cakeweight();
        cakefaavour();
        setadapter()
        AppRater(homeActivity!!).app_launched();
        if(AppPreferenceHalper.read(PreferenceConstantParam.isLogIn,false)==true) {
            if (!AppPreferenceHalper.read(PreferenceConstantParam.ORDER_IDS,"").equals("")
                && AppPreferenceHalper.read(PreferenceConstantParam.ORDER_IDS,"")!!.contains( AppPreferenceHalper.read(PreferenceConstantParam.PRODUCTID, 0).toString())){

                productDetailsViewBind?.ll_review!!.visibility=View.VISIBLE

            }
            else
                productDetailsViewBind?.ll_review!!.visibility=View.GONE

        }else
            productDetailsViewBind?.ll_review!!.visibility=View.GONE

        return view
    }

    private fun callApiforflavour() {
        if (ConnectionDetector.isConnectingToInternet(activity)) {
            val apiInterface = RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)
            val callapi = apiInterface?.getflavour()
            callapi?.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        if (response != null) {
                            try {
                                val jsonarray: JSONArray = JSONArray(response.body()?.string())

                                for(p in 0 until jsonarray.length()){
                                    val jsonObj=jsonarray.getJSONObject(p)
                                    cakeweFLAVOURarraylist.add(jsonObj.optString("name"));
                                    val  flavourModel=FlavourModel(jsonObj.optInt("id"),jsonObj.optString("name"),jsonObj.optString("description"))
                                   // productDetailsViewBind?.tv_flovour?.setText(cakeweFLAVOURarraylist.get(0))
                                    flavourlist.add(flavourModel)

                                }
                                if (flavourlist.size>0) {
                                    productDetailsViewBind?.tv_flovour?.setText(flavourlist.get(0).description)
                                    productDetailsViewBind?.tv_ingradient_text!!.setText(flavourlist.get(0).description)
                                }

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }else
                        Alert.showalert(homeActivity!!,"Internal server error.")
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                }
            })



        }else
            Alert.showalert(homeActivity!!,homeActivity!!.resources.getString(R.string.nointernet))
    }

    private fun cakefaavour() {
        for(i in 0 until 30)
            cakeweightarraylist.add((i+1).toString()+" Pound")
    }

   /* private fun cakeweight() {
        for(i in 0 until 10)
            cakeweFLAVOURarraylist.add(" GSD Pound")
    }*/

    private fun callApiforproductdetails() {
      /*  progress= ProgressDialog(homeActivity)
        progress?.setMessage("loading..")
        progress?.show()*/
        if (ConnectionDetector.isConnectingToInternet(activity)) {
            val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
            this!!.homeActivity?.let { customProgress.showProgress(it, "Please Wait..", false) }
            val apiInterface = RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)
            val callApi = AppPreferenceHalper.read(PreferenceConstantParam.PRODUCTID, 0)?.let {
                apiInterface?.getproductdetails(it)
            }
            callApi?.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    //  progress?.dismiss()
                    customProgress.hideProgress()
                    if (response.isSuccessful) {
                        if (response != null) {
                            try {
                                //  val jsonObject = JSONObject(response?.body().toString())
                                val jsonObj: JSONObject = JSONObject(response.body()?.string())
                                updateview(jsonObj)

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }else
                        Alert.showalert(homeActivity!!,"Internal server error.")
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    customProgress.hideProgress()
                    Alert.showalert(homeActivity!!,"Internal server error.")
                }
            })
        }else
            Alert.showalert(homeActivity!!,homeActivity!!.resources.getString(R.string.nointernet))
    }

    private fun updateview(jsonObj: JSONObject) {
        try {


            productDetailsViewBind?.tv_product_name?.setText(jsonObj.getString("name"))
            homeActivity!!.homeActivityViewBind!!.tv_headertext?.setText(jsonObj.getString("name"))
            AppPreferenceHalper.write(
                PreferenceConstantParam.PRODUCTNAME,
                jsonObj.getString("name")
            )
            withegg_per_pound = jsonObj.getString("withegg_per_pound")
            eggless_per_pound = jsonObj.getString("eggless_per_pound")
            val wc_product_variations: JSONArray = jsonObj.getJSONArray("wc_product_variations")
            for (i in 0 until wc_product_variations.length()) {
                val productVariation: ProductVariation = ProductVariation(
                    wc_product_variations.getJSONObject(i).optInt("variation_id"),
                    wc_product_variations.getJSONObject(i).optString("attribute_pa_withegg-eggless")
                )
                variationlist.add(productVariation)
                //  "variation_id": 38,
                //  "attribute_pa_withegg-eggless": "eggless",
            }

            product_id = jsonObj.optInt("id")
            /* if (jsonObj.getInt("stock_quantity")!=null) {
            if (jsonObj.getInt("stock_quantity") > 0) {
                productDetailsViewBind?.tv_avalibility?.setText(resources.getString(R.string.avalible))
                productDetailsViewBind?.tv_avalibility?.setTextColor(resources.getColor(R.color.light_green))
                productDetailsViewBind?.img_circle_green?.visibility == View.VISIBLE
            } else {
                productDetailsViewBind?.tv_avalibility?.setText(resources.getString(R.string.not_avalible))
                productDetailsViewBind?.tv_avalibility?.setTextColor(resources.getColor(R.color.red))
                productDetailsViewBind?.img_circle_red?.visibility == View.VISIBLE
            }
        }else{
            productDetailsViewBind?.tv_avalibility?.setText(resources.getString(R.string.avalible))
            productDetailsViewBind?.tv_avalibility?.setTextColor(resources.getColor(R.color.light_green))
            productDetailsViewBind?.img_circle_green?.visibility == View.VISIBLE
        }*/
            productDetailsViewBind?.tv_price?.setText(jsonObj.getString("price") + homeActivity?.resources?.getString(
                    R.string.perpound
                ))
            productprice = jsonObj.getString("price")
            productDetailsViewBind?.tv_avaragerating?.setText(jsonObj.getString("average_rating"))
            productDetailsViewBind?.tv_ratingcount?.setText(
                jsonObj.getString("rating_count") + " " + homeActivity?.resources?.getString(
                    R.string.rating
                )
            )

            if( productDetailsViewBind?.ll_review!!.visibility==View.VISIBLE){
                productDetailsViewBind?.ratingbar!!.rating=jsonObj.getString("average_rating").toFloat()
            }

            productDetailsViewBind?.tv_reviewcount?.setText(
                jsonObj.getString("product_reviews_count") + " " + homeActivity?.resources?.getString(
                    R.string.review
                )
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                productDetailsViewBind?.tv_desc_text?.setText(
                    Html.fromHtml(
                        jsonObj.getString("description"),
                        Html.FROM_HTML_MODE_COMPACT
                    )
                );
            } else {
                productDetailsViewBind?.tv_desc_text?.setText(Html.fromHtml(jsonObj.getString("description")))
            }
            // productDetailsViewBind?.tv_desc_text?.setText(jsonObj.getString("description"))
            val imgJsonArray = jsonObj.getJSONArray("images")
            //  val meta_data=jsonObj.getJSONArray("meta_data")
            //  var matadatarray = meta_data.getJSONObject(3)


            if (imgJsonArray.length() > 0) {
                imagelist.clear()
                var imgobj1 = imgJsonArray.getJSONObject(0)
                 if (imgJsonArray.length()>1) {
                     var imgobj2 = imgJsonArray.getJSONObject(1)
                     if (imgobj2 != null) {
                         productDetailsViewBind?.cake_img2!!.visibility=View.VISIBLE
                         imgsrc2 = imgobj2.getString("src")
                         imagelist.add(imgsrc2!!)
                         Glide.with(homeActivity)
                             .load(imgsrc2)
                             //.apply(requestOptions)
                             .apply(
                                 RequestOptions().transforms(
                                     CenterCrop(),
                                     RoundedCorners(resources.getDimension(R.dimen._6sdp).toInt())
                                 )
                             )
                             .into(productDetailsViewBind?.cake_img2)

                     }
                 }

                if (imgJsonArray.length()>2) {
                    var imgobj3 = imgJsonArray.getJSONObject(2)
                    if (imgobj3 != null) {
                        imgsrc3 = imgobj3.getString("src")
                        productDetailsViewBind?.cake_img3!!.visibility=View.VISIBLE
                        imagelist.add(imgsrc3!!)
                        Glide.with(homeActivity)
                            .load(imgsrc3)
                            //  .apply(requestOptions)
                            .apply(
                                RequestOptions().transforms(
                                    CenterCrop(),
                                    RoundedCorners(resources.getDimension(R.dimen._6sdp).toInt())
                                )
                            )
                            .into(productDetailsViewBind?.cake_img3)
                    }
                }

                if (imgJsonArray.length()>3) {
                    var imgobj4 = imgJsonArray.getJSONObject(3)
                    if (imgobj4 != null) {

                        productDetailsViewBind?.cake_img4!!.visibility=View.VISIBLE
                        imgsrc4 = imgobj4.getString("src")
                        imagelist.add(imgsrc4!!)
                        Glide.with(homeActivity)
                            .load(imgsrc4)
                            //  .apply(requestOptions)
                            .apply(
                                RequestOptions().transforms(
                                    CenterCrop(),
                                    RoundedCorners(resources.getDimension(R.dimen._6sdp).toInt())
                                )
                            )
                            .into(productDetailsViewBind?.cake_img4)
                    }

                }

                if (imgobj1 != null) {
                    imgsrc1 = imgobj1.getString("src")
                    imagelist.add(imgsrc1!!)
                    /* Glide.with(homeActivity)
                    .load(imgsrc1)
                    //  .apply(requestOptions)
                    .into(productDetailsViewBind?.img_details)*/
                    productDetailsViewBind?.cake_img1!!.visibility=View.VISIBLE
                    Glide.with(homeActivity)
                        .load(imgsrc1)
                        //  .apply(requestOptions)
                        .apply(
                            RequestOptions().transforms(
                                CenterCrop(),
                                RoundedCorners(resources.getDimension(R.dimen._6sdp).toInt())
                            )
                        )
                        .into(productDetailsViewBind?.cake_img1)
                }



                setimageslideradapter()

                //command list

                val product_reviews_obj: JSONArray = jsonObj.getJSONArray("product_reviews_obj")
                if (product_reviews_obj.length() > 0) {
                    val len: Int = product_reviews_obj.length()
                    for (i in 0 until len) {
                        var obj = product_reviews_obj.getJSONObject(i)
                        var comment_author: String = obj.optString("comment_author")
                        var comment_date: String = obj.optString("comment_date")
                        var comment_content: String = obj.optString("comment_content")
                        var rating: String = obj.optString("rating")

                        var productCommand =
                            ProductCommand(comment_author, comment_date, comment_content, rating)
                        commandArrayList.add(productCommand)

                    }
                    commandListAdapter?.notifyDataSetChanged()

                }


            }


            val attributes: JSONArray = jsonObj.getJSONArray("attributes")
            /* for(p in 0 until attributes.length()){
            val jsonObj=attributes.getJSONObject(p)
            if (jsonObj.get("name").equals("Flavour")){
                 val JSONArray=jsonObj.getJSONArray("options")

                if (JSONArray != null) {
                    val len: Int = JSONArray.length();
                    for (i in 0 until len) {
                        cakeweFLAVOURarraylist.add(JSONArray.get(i).toString());
                    }
                    productDetailsViewBind?.tv_flovour?.setText(cakeweFLAVOURarraylist.get(0))

                }
            }
        }
*/
            val threesixty = jsonObj.getJSONArray("prod_360_deg_images");
            if (threesixty != null) {
                val len: Int = threesixty.length();
                for (i in 0 until len) {
                    Imagrarraylist.add(threesixty.get(i).toString());
                }
                if (Imagrarraylist?.size!! >0)
                    productDetailsViewBind!!.img360!!.visibility = View.VISIBLE
                else
                    productDetailsViewBind!!.img360!!.visibility = View.GONE

            }

        }catch (e:java.lang.Exception){
            e.printStackTrace()
        }
    }


    private fun setadapter() {
        commandListAdapter= CommandListAdapter(homeActivity!!,commandArrayList)
        productDetailsViewBind?.rv_commantlist?.layoutManager= LinearLayoutManager(homeActivity)
        productDetailsViewBind?.rv_commantlist?.adapter=commandListAdapter
    }

    private fun setimageslideradapter() {
         var currentPage = 0
        productDetailsViewBind!!.vp_imageslider!!.setAdapter(SliderImageAdapter(homeActivity!!, imagelist))
       productDetailsViewBind!!.indicator!!.setViewPager( productDetailsViewBind!!.vp_imageslider!!)
        val density = resources.displayMetrics.density
        productDetailsViewBind!!.indicator!!.setRadius(5 * density)
        val NUM_PAGES = imagelist.size
        val handler = Handler()
        val Update = Runnable {
            if (currentPage == NUM_PAGES) {
                currentPage = 0
            }
            productDetailsViewBind!!.vp_imageslider!!.setCurrentItem(currentPage++, true)
        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, 2000, 2000)

        productDetailsViewBind!!.indicator!!.setOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageSelected(position: Int) {
                currentPage = position

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int) {
            }
        })
    }

    public fun callAddtocartApi(){
        if(selectedvariationid==0){
            vari_id=variationlist.get(0).variation_id
            vari_name=variationlist.get(0).attribute_pa_withegg_eggless
        }else if(selectedvariationid==1){
            vari_id=variationlist.get(1).variation_id
            vari_name=variationlist.get(1).attribute_pa_withegg_eggless
        }
        val progress= ProgressDialog(homeActivity)
        progress?.setMessage("loading..")
        progress?.show()
        val apiInterface= RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)
        val token:String="Bearer "+AppPreferenceHalper.read(PreferenceConstantParam.TOKEN,"")
        val callapi =apiInterface?.addtocart(product_id!!,1,vari_id.toString(),vari_name.toString(),productDetailsViewBind?.tv_flovour?.text.toString(),
            weight!!,productDetailsViewBind?.et_message?.text.toString(),productprice!!.toInt()*weight!!.toInt(),
            withegg_per_pound!!,eggless_per_pound!!,true,token)
          callapi?.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (progress.isShowing)
                    progress.dismiss()
                if (response.isSuccessful){
                    Toast.makeText(homeActivity,"This Product is added in your cart.",Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(homeActivity,response.body()?.string(), Toast.LENGTH_LONG).show()
                }

            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                if (progress.isShowing)
                    progress.dismiss()

            }
        })


    }

    public fun share(){
                val shareBody:String = "https://play.google.com/store/apps/details?id=com.sculptee&hl=en";
                var sharingIntent:Intent =  Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, homeActivity?.resources?.getString(R.string.app_name))
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }

    override fun onResume() {
        super.onResume()
      //  Toast.makeText(homeActivity,"onresume",Toast.LENGTH_LONG).show()
       if(AppPreferenceHalper.read(PreferenceConstantParam.isLogIn,false)==true){
            if (addtocart) {
                productDetailsOnClick!!.addcart()
                homeActivity?.loadcartcountvalue()
                addtocart=false

            }else if(addtofevorite){
                callApiformakefavourite()
                addtofevorite=false
            }
        }

    }

    fun callApiformakefavourite() {

        if(selectedvariationid==0){
            vari_id= variationlist?.get(0)?.variation_id
           vari_name=variationlist?.get(0)?.attribute_pa_withegg_eggless
        }else if(selectedvariationid==1){
           vari_id=variationlist?.get(1)?.variation_id
           vari_name=variationlist?.get(1)?.attribute_pa_withegg_eggless
        }
       // productDetailsViewBind?.et_message?.text.toString()
        //weight!!,productDetailsFragment?.productprice!!.toInt()
        val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        this!!.homeActivity?.let { customProgress.showProgress(it,"Please Wait..",false) }
        val apiInterface= RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)


        var addfavjson = JSONObject();
        try {
            addfavjson.put("userid", AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_ID,"")!!.toInt())
            addfavjson.put("prod_id",AppPreferenceHalper.read(PreferenceConstantParam.PRODUCTID,0))

        }catch (e: Exception){
            e.printStackTrace()
        }
        var obj: JSONObject = addfavjson
        var jsonParser: JsonParser = JsonParser()
        var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
        val callApi=apiInterface?.Addtofavourite(gsonObject)
        callApi?.enqueue(object :Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                customProgress.hideProgress()
                try {
                    val jsonObj:JSONObject = JSONObject(response.body()?.string())
                    val statuscode=jsonObj.getString("code")
                    if (statuscode.equals("success")){
                        AppPreferenceHalper.write(PreferenceConstantParam.WISHLIST,jsonObj.getString("wishlists"))
                        Toast.makeText(homeActivity,jsonObj.getString("msg"),Toast.LENGTH_LONG).show()
//{"wishlists":"288,14,52","code":"success","msg":"Add to wishlists."}
                    }else
                        Toast.makeText(homeActivity,jsonObj.getString("msg"),Toast.LENGTH_LONG).show()

                }catch (e: Exception){
                    e.printStackTrace()
                }


                //{"code":"error","msg":"Failed to Add in wishlists (For duplicate entry)."}
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                customProgress.hideProgress()
            }
        })
    }
}