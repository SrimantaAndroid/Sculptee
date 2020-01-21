package com.sculptee.fragment.wishlist

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.rts.commonutils_2_0.netconnection.ConnectionDetector
import com.sculptee.R
import com.sculptee.datastorage.CakeProduct
import com.sculptee.datastorage.RoomSingleton
import com.sculptee.model.wishlistmodel.WishListmodel
import com.sculptee.network.ApiInterface
import com.sculptee.network.RetroFitApiService
import com.sculptee.screens.home.HomeActivity
import com.sculptee.utils.Alert
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.sculptee.utils.itemclickinterface.OnItemClickInterface
import com.sculptee.utils.preferenceconstant.PreferenceConstantParam
import com.sculptee.utils.sheardpreference.AppPreferenceHalper
import okhttp3.ResponseBody
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class WishListAdapter(
    val homeActivity: HomeActivity?,
    val currentwishlist: ArrayList<WishListmodel>,
    val param: OnItemClickInterface
) : RecyclerView.Adapter<WishListAdapter.ViewHolder>() {
    private lateinit var mDb: RoomSingleton
    var deviceResolution:DeviceResolution?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        deviceResolution= DeviceResolution(homeActivity)
        mDb = RoomSingleton.getInstance(homeActivity!!.applicationContext)
        val view= LayoutInflater.from(homeActivity).inflate(R.layout.wishlist_item_layout,null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  currentwishlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tv_cake_name.typeface=deviceResolution!!.getmavenproBlack(homeActivity)
        holder.tv_avaragerating.typeface=deviceResolution!!.getMavenProRegular(homeActivity)
        holder.tv_ratingcount.typeface=deviceResolution!!.getMavenProRegular(homeActivity)
        holder.tv_reviewcount.typeface=deviceResolution!!.getMavenProRegular(homeActivity)
        holder.tv_prod_price.typeface=deviceResolution!!.getMavenProRegular(homeActivity)
        holder.tv_caketype_val.typeface=deviceResolution!!.getMavenProRegular(homeActivity)
        holder.tv_flaver_value.typeface=deviceResolution!!.getMavenProRegular(homeActivity)
        holder.tv_weigtt_val.typeface=deviceResolution!!.getMavenProRegular(homeActivity)
        holder.tv_caketype.typeface=deviceResolution!!.getMavenProRegular(homeActivity)
        holder.tv_flaver.typeface=deviceResolution!!.getMavenProRegular(homeActivity)
        holder.tv_weigtt.typeface=deviceResolution!!.getMavenProRegular(homeActivity)
       // holder.tv_email.typeface=deviceResolution!!.getMavenProRegular(homeActivity)
        holder.tv_remove.typeface=deviceResolution!!.getMavenProRegular(homeActivity)

        holder.tv_cake_name.setText(currentwishlist.get(position).p_name)
        Glide.with(homeActivity)
            .load(currentwishlist.get(position).prod_img)
            //  .apply(requestOptions)
            .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(homeActivity!!.resources.getDimension(R.dimen._6sdp).toInt())))
            .into(holder.img_product_image)
        holder.tv_avaragerating.setText(currentwishlist.get(position).average_rating)
        holder.tv_ratingcount.setText(currentwishlist.get(position).rating_count.toString()+" rating")
        holder.tv_reviewcount.setText(currentwishlist.get(position).product_reviews_count+" review")
        holder.tv_prod_price.setText(currentwishlist.get(position).p_price+"/pound")
        holder.tv_caketype_val.setText(currentwishlist.get(position).p_type)
        holder.tv_flaver_value.setText(currentwishlist.get(position).P_flavour.replace("-"," "))
        holder.tv_weigtt_val.setText(currentwishlist.get(position).p_weight.toString()+" pound")
       /* holder.tv_email.setOnClickListener {
            addcart(currentwishlist.get(position),position)
        }*/
        holder.tv_remove.setOnClickListener {
            showalertforconfirmfordelete(homeActivity.resources.getString(R.string.suretodelete),currentwishlist.get(position).p_id,position);

        }
        holder.rl_wishitem.setOnClickListener {
            param.OnClickItem(position)
        }

    }

    private fun showalertforconfirmfordelete( message:String,pId: Int,position:Int) {
        var deviceResolution:DeviceResolution?=null
        deviceResolution=DeviceResolution(homeActivity)
        val alertDialog = Dialog(homeActivity,R.style.Transparent)
        /*alertDialog.setTitle(activity.resources.getString(R.string.app_name))
        alertDialog.setMessage(message)*/
        alertDialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
        val view: View =LayoutInflater.from(homeActivity).inflate(R.layout.alert_layout_yesno,null)
        alertDialog.setContentView(view)
        alertDialog .setCancelable(false)
        val tv_message:TextView=view.findViewById(R.id.tv_message)
        val btn_ok: Button =view.findViewById(R.id.btn_ok)
        val btn_no: Button =view.findViewById(R.id.btn_no)
        btn_ok.typeface=deviceResolution.getMavenProRegular(homeActivity)
        btn_no.typeface=deviceResolution.getMavenProRegular(homeActivity)
        tv_message.typeface=deviceResolution.getMavenProRegular(homeActivity)
        btn_ok.setOnClickListener {
            alertDialog.dismiss()
            // activity.alertyesfuncation();
            removefromlist(pId,position)

        }
        btn_no.setOnClickListener {
            alertDialog.dismiss()

        }
        tv_message.setText(message)
        alertDialog.show()
    }

    private fun removefromlist(pId: Int, position: Int) {
        if (ConnectionDetector.isConnectingToInternet(homeActivity)) {
            val customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
            homeActivity?.let { customProgress.showProgress(it, "Please Wait..", false) }
            val apiInterface =
                RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)
            // val callapi =apiInterface?.login(logInActivityViewBind?.et_mobileno?.text.toString(),logInActivityViewBind?.et_password?.text.toString())
            var loginjson = JSONObject();
            try {

                loginjson.put("userid", AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_ID,"")!!.toInt())
                loginjson.put("prod_id",pId.toInt())

            } catch (e: Exception) {
                e.printStackTrace()
            }
            var obj: JSONObject = loginjson
            var jsonParser: JsonParser = JsonParser()
            var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
            val callapi = apiInterface?.revovefrowishlist(gsonObject)
            callapi?.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    //  if (progress.isShowing)
                    //  progress.dismiss()
                    customProgress.hideProgress()
                    try {
                        val jsonObj:JSONObject = JSONObject(response.body()?.string())
                        val statuscode=jsonObj.getString("code")
                        if (statuscode.equals("success")){
                            AppPreferenceHalper.write(PreferenceConstantParam.WISHLIST,jsonObj.getString("wishlists"))
                            if (!AppPreferenceHalper.read(PreferenceConstantParam.WISHLIST, "").equals("")) {
                                currentwishlist.removeAt(position)
                                notifyDataSetChanged()
                            }
                            else {
                                currentwishlist.removeAt(position)
                                notifyDataSetChanged()
                                Alert.showalert(homeActivity!!, "You don't have more wish-item")
                            }
                        }else
                            Alert.showalert(homeActivity!!,jsonObj.getString("msg"))

                    }catch (e: Exception){
                        e.printStackTrace()
                    }


                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    //   if (progress.isShowing)
                    //    progress.dismiss()
                    customProgress.hideProgress()
                }
            })
        }else
            Alert.showalert(homeActivity!!,homeActivity!!.resources.getString(R.string.nointernet))

    }

    private fun addcart(model: WishListmodel, position: Int) {
        val rnds = (0..1000).random()
        val cakeProduct= CakeProduct(rnds,model?.p_id!!.toString(),AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_ID,"0")!!,model.p_name,"",
           model.prod_img,model.p_price.toInt(),model.P_flavour,model.p_type ,model.p_weight,model.p_price.toInt(),
            1,"",model.withegg_per_pound,model.eggless_per_pound,1)
        doAsync {
            mDb.CakeProductDao().insert(cakeProduct)

            uiThread {
                Toast.makeText(homeActivity,"This Product is added in your cart.", Toast.LENGTH_LONG).show()
                var wishlist:String= AppPreferenceHalper.read(PreferenceConstantParam.WISHLIST,"")!!
                removefromlist(model.p_id,position)
                /*if (wishlist.contains(model.p_id.toString())) {
                    wishlist=wishlist.replace(model.p_id.toString(),"")
                }*/
               /* var lastcahaar:String= wishlist.last().toString()
                var result:String?=""
                if (lastcahaar.equals(",")) {
                    result = wishlist.substring(0, wishlist.length - 1);
                    AppPreferenceHalper.write(PreferenceConstantParam.WISHLIST,result)
                }else
                    AppPreferenceHalper.write(PreferenceConstantParam.WISHLIST,wishlist)*/

                homeActivity?.loadcartcountvalue()
                currentwishlist.removeAt(position)
                notifyDataSetChanged()

            }
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tv_cake_name:TextView=itemView.findViewById(R.id.tv_cake_name)
        val img_product_image:ImageView=itemView.findViewById(R.id.img_product_image)
        val tv_avaragerating:TextView=itemView.findViewById(R.id.tv_avaragerating)
        val tv_ratingcount:TextView=itemView.findViewById(R.id.tv_ratingcount)
        val tv_reviewcount:TextView=itemView.findViewById(R.id.tv_reviewcount)
        val tv_prod_price:TextView=itemView.findViewById(R.id.tv_prod_price)
        val tv_caketype:TextView=itemView.findViewById(R.id.tv_caketype)
        val tv_caketype_val:TextView=itemView.findViewById(R.id.tv_caketype_val)
        val tv_flaver:TextView=itemView.findViewById(R.id.tv_flaver)
        val tv_flaver_value:TextView=itemView.findViewById(R.id.tv_flaver_value)
        val tv_weigtt:TextView=itemView.findViewById(R.id.tv_weigtt)
        val tv_weigtt_val:TextView=itemView.findViewById(R.id.tv_weigtt_val)
      //  val tv_email:TextView=itemView.findViewById(R.id.tv_email)
        val tv_remove:TextView=itemView.findViewById(R.id.tv_remove)
        val rl_wishitem:RelativeLayout=itemView.findViewById(R.id.rl_wishitem)

    }
}