package com.sculptee.screens.cart.adapter

import android.app.Dialog
import android.content.Intent
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
import com.sculptee.R
import com.sculptee.datastorage.CakeProduct
import com.sculptee.network.ApiInterface
import com.sculptee.network.RetroFitApiService
import com.sculptee.screens.cart.CartActivity
import com.sculptee.screens.sociallogin.SocialSignActivity
import com.sculptee.utils.bottomsheet.BottomSheetQuantity
import com.sculptee.utils.bottomsheet.BottomSheetweight
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.sculptee.utils.preferenceconstant.PreferenceConstantParam
import com.sculptee.utils.sheardpreference.AppPreferenceHalper
import okhttp3.ResponseBody
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartListAdapter(
    var cartActivity: CartActivity,
    val cartlist: ArrayList<CakeProduct>

) : RecyclerView.Adapter<CartListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View=LayoutInflater.from(cartActivity).inflate(R.layout.cart_item_layout,null)
       // this.cartActivity=cartActivity
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return  cartlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var deviceResolution: DeviceResolution= DeviceResolution(cartActivity)

        holder.tv_cake_name.typeface=deviceResolution.getmavenproBlack(cartActivity)
        holder.tv_weigtt.typeface=deviceResolution.getMavenProRegular(cartActivity)
        holder.tv_flaver.typeface=deviceResolution.getMavenProRegular(cartActivity)
        holder.tv_price_cake.typeface=deviceResolution.getmavenproBlack(cartActivity)
        holder.tv_movetolovelist.typeface=deviceResolution.getMavenProBold(cartActivity)
       // holder.tv_count.typeface=deviceResolution.getMavenProRegular(cartActivity)

        holder.tv_caketype.typeface=deviceResolution.getMavenProRegular(cartActivity)
        holder.tv_caketype_val.typeface=deviceResolution.getMavenProRegular(cartActivity)
        holder.tv_flaver_value.typeface=deviceResolution.getMavenProRegular(cartActivity)
        holder.tv_weigtt_val.typeface=deviceResolution.getMavenProRegular(cartActivity)
        holder.tv_remove.typeface=deviceResolution.getMavenProBold(cartActivity)
        holder.tv_quentity.typeface=deviceResolution.getMavenProRegular(cartActivity)
        holder.tv_quentity_val.typeface=deviceResolution.getMavenProRegular(cartActivity)

        holder.tv_cake_name.setText(cartlist.get(position).product_name)
        holder.tv_weigtt_val.setText(cartlist.get(position).weight.toString()+" "+" Pound")
        holder.tv_caketype_val.setText(cartlist.get(position).product_type)
        holder.tv_flaver_value.setText(cartlist.get(position).flovour)
        holder.tv_quentity_val.setText(cartlist.get(position).quentity.toString())
     //   val totalprice:Int=cartlist.get(position).product_price*cartlist.get(position).weight
        val totalprice:Int=cartlist.get(position).product_total_price
        holder.tv_price_cake.setText(totalprice.toString())

        Glide.with(cartActivity)
            .load(cartlist.get(position).imgurl)
            //  .apply(requestOptions)
            .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(cartActivity.resources.getDimension(R.dimen._3sdp).toInt())))
            .into(holder?.img_product_image)
        holder.rl_weight.setOnClickListener {
           // openpopupdrop_downfoeweight(holder.tv_weigtt_val, holder.rl_weight,cartlist.get(position), holder.tv_price_cake,position,holder.tv_quentity_val)
            val bottomSheetwe = BottomSheetweight(cartActivity,position,cartlist.get(position).weight)
            bottomSheetwe.show(cartActivity.supportFragmentManager, "BottomSheetEx")
        }
        holder.rl_quentity.setOnClickListener {
           // openpopupdrop_downforquentity(holder.tv_quentity_val, holder.rl_quentity,cartlist.get(position), holder.tv_price_cake,position)
            val bottomSheet = BottomSheetQuantity(cartActivity,position,cartlist.get(position).quentity)
            bottomSheet.show(cartActivity.supportFragmentManager, "BottomSheetEx")
        }

        holder.rl_movetolovelist.setOnClickListener {
            if(AppPreferenceHalper.read(PreferenceConstantParam.isLogIn,false)==true) {
                callApiformovetoLoveList(cartlist.get(position).product_id,position)
            }else{
                cartActivity?.startActivity(Intent(cartActivity, SocialSignActivity::class.java))
            }

        }

       /* holder.tv_plus.setOnClickListener { v: View? ->
            if(holder.tv_count.text.toString().toInt()>=1) {
                holder.tv_count.setText((holder.tv_count.text.toString().toInt() + 1).toString())
                holder.tv_price_cake.setText((holder.tv_count.text.toString().toInt()*totalprice).toString())
                cartlist.get(position).product_total_price= holder.tv_count.text.toString().toInt()*totalprice;
                doAsync {
                    cartActivity.mDb.CakeProductDao()
                        .upadteprice(cartlist.get(position).product_id, holder.tv_count.text.toString().toInt()*totalprice)
                }
            }
        }
        holder.tv_minus.setOnClickListener { v: View? ->
            if(holder.tv_count.text.toString().toInt()>1) {
                holder.tv_count.setText((holder.tv_count.text.toString().toInt() - 1).toString())
                holder.tv_price_cake.setText(( holder.tv_count.text.toString().toInt()*totalprice).toString())
                cartlist.get(position).product_total_price=holder.tv_count.text.toString().toInt()*totalprice
                doAsync {
                   // cartActivity.mDb.CakeProductDao().update(cartlist.get(position).product_id, holder.tv_count.text.toString().toInt())
                    cartActivity.mDb.CakeProductDao().upadteprice(cartlist.get(position).product_id, holder.tv_count.text.toString().toInt()*totalprice)
                }

            }
        }*/
        holder.rl_remove.setOnClickListener(View.OnClickListener {
            showalertfordeleteitem(position)
        })

    }

    private fun showalertfordeleteitem(position: Int) {
        var deviceResolution=DeviceResolution(cartActivity)
        val alertDialog = Dialog(cartActivity,R.style.Transparent)
        /*alertDialog.setTitle(activity.resources.getString(R.string.app_name))
        alertDialog.setMessage(message)*/
        alertDialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
        val view: View =LayoutInflater.from(cartActivity).inflate(R.layout.alert_layout_yesno,null)
        alertDialog.setContentView(view)
        alertDialog .setCancelable(false)
        val tv_message:TextView=view.findViewById(R.id.tv_message)
        val btn_ok:Button=view.findViewById(R.id.btn_ok)
        val btn_no:Button=view.findViewById(R.id.btn_no)
        btn_ok.typeface=deviceResolution.getMavenProRegular(cartActivity)
        btn_no.typeface=deviceResolution.getMavenProRegular(cartActivity)
        tv_message.typeface=deviceResolution.getMavenProRegular(cartActivity)
        btn_ok.setOnClickListener {
            alertDialog.dismiss()
            doAsync {
                cartActivity.mDb.CakeProductDao().deleterecord(cartlist.get(position))
                cartlist.removeAt(position)
                // cartActivity.cartlist.removeAt(position)
                cartActivity.runOnUiThread {

                    cartActivity.showtotalprice()
                    notifyItemRemoved(position)

                }

            }

        }
        btn_no.setOnClickListener {
            alertDialog.dismiss()

        }
        tv_message.setText("Are you want to Romove from list?")
        alertDialog.show()


    }

    private fun callApiformovetoLoveList(id: String, position: Int) {
        //weight!!,productDetailsFragment?.productprice!!.toInt()
        val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        this!!.cartActivity?.let { customProgress.showProgress(it,"Please Wait..",false) }
        val apiInterface= RetroFitApiService.getRetrofitInstance()?.create(ApiInterface::class.java)

        var addfavjson = JSONObject();
        try {
            addfavjson.put("userid", AppPreferenceHalper.read(PreferenceConstantParam.CUSTOMER_ID,"")!!.toInt())
            addfavjson.put("prod_id", id.toInt())

        }catch (e: Exception){
            e.printStackTrace()
        }
        var obj: JSONObject = addfavjson
        var jsonParser: JsonParser = JsonParser()
        var gsonObject: JsonObject = jsonParser.parse(obj.toString()) as JsonObject;
        val callApi=apiInterface?.Addtofavourite(gsonObject)
        callApi?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                customProgress.hideProgress()
                try {
                    val jsonObj: JSONObject = JSONObject(response.body()?.string())
                    val statuscode=jsonObj.getString("code")
                    if (statuscode.equals("success")){
                        AppPreferenceHalper.write(PreferenceConstantParam.WISHLIST,jsonObj.getString("wishlists"))
                        Toast.makeText(cartActivity,jsonObj.getString("msg"), Toast.LENGTH_LONG).show()
//{"wishlists":"288,14,52","code":"success","msg":"Add to wishlists."}

                        doAsync {
                            cartActivity.mDb.CakeProductDao().deleterecord(cartlist.get(position))
                            cartlist.removeAt(position)
                            // cartActivity.cartlist.removeAt(position)
                            cartActivity.runOnUiThread {
                                cartActivity.showtotalprice()
                                notifyItemRemoved(position)

                            }

                        }
                    }else
                        Toast.makeText(cartActivity,jsonObj.getString("msg"), Toast.LENGTH_LONG).show()

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

   /* private fun openpopupdrop_downforquentity(
        tvQuentityVal: TextView,
        rlQuentity: RelativeLayout,
        get: CakeProduct,
        tvPriceCake: TextView,
        cposition: Int) {

        val cakeweightarraylist=ArrayList<String>()
        for(i in 0 until 30)
            cakeweightarraylist.add((i+1).toString())
        val popUpView = cartActivity?.getLayoutInflater()?.inflate(com.sculptee.R.layout.drop_down_layout, null)
        val  listPopupWindow = PopupWindow(popUpView, cartActivity?.resources?.getDimension(R.dimen._60sdp)!!.toInt(), cartActivity?.resources?.getDimension(R.dimen._115sdp)!!.toInt(), true
            // ViewGroup.LayoutParams.WRAP_CONTENT, false
        )
        val reclyerview_dropdown:RecyclerView=popUpView!!.findViewById(R.id.reclyerview_dropdown)
        val dropDownListAdapter: DropDownWeightListAdapter = DropDownWeightListAdapter(cartActivity,cakeweightarraylist,object :
            OnItemClickInterface {
            override fun OnClickItem(position: Int) {
                super.OnClickItem(position)
                tvQuentityVal!!.setText(cakeweightarraylist.get(position))
                val value=cakeweightarraylist.get(position).toInt()*((cartlist.get(cposition).weight)*cartlist.get(cposition).product_price)
                tvPriceCake.setText(value.toString())
                listPopupWindow.dismiss()
            }
        })
        reclyerview_dropdown?.layoutManager= LinearLayoutManager(cartActivity)
        reclyerview_dropdown?.adapter=dropDownListAdapter
        listPopupWindow?.showAsDropDown(rlQuentity);

    }
*/
   /* private fun openpopupdrop_downfoeweight(
        tvQuentityVal: TextView?,
        rl_quentity: RelativeLayout?,
        cakeprod: CakeProduct,
        tvPriceCake: TextView,
        cposition: Int,
        tvQuentityVal1: TextView) {
         val cakeweightarraylist=ArrayList<String>()
        for(i in 0 until 30)
            cakeweightarraylist.add((i+1).toString()+" Pound")
        val popUpView = cartActivity?.getLayoutInflater()?.inflate(com.sculptee.R.layout.drop_down_layout, null)
        val  listPopupWindow = PopupWindow(popUpView, cartActivity?.resources?.getDimension(R.dimen._80sdp)!!.toInt(), cartActivity?.resources?.getDimension(R.dimen._115sdp)!!.toInt(), true
            // ViewGroup.LayoutParams.WRAP_CONTENT, false
        )
        val reclyerview_dropdown:RecyclerView=popUpView!!.findViewById(R.id.reclyerview_dropdown)
        val dropDownListAdapter: DropDownWeightListAdapter = DropDownWeightListAdapter(cartActivity,cakeweightarraylist,object :
            OnItemClickInterface {
            override fun OnClickItem(position: Int) {
                super.OnClickItem(position)
                tvQuentityVal!!.setText(cakeweightarraylist.get(position))
                val value=(cartlist.get(cposition).product_price*(position+1))*tvQuentityVal1.text.toString().toInt()
                tvPriceCake.setText(value.toString())
                cartlist.get(cposition).weight= position+1
                listPopupWindow.dismiss()
            }
        })
        reclyerview_dropdown?.layoutManager= LinearLayoutManager(cartActivity)
        reclyerview_dropdown?.adapter=dropDownListAdapter
        listPopupWindow?.showAsDropDown(rl_quentity);

    }*/



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

      //  val tv_minus:TextView=itemView.findViewById(R.id.tv_minus)
      //  val tv_plus:TextView=itemView.findViewById(R.id.tv_plus)
        //val tv_count:TextView=itemView.findViewById(R.id.tv_count)
        val tv_price_cake:TextView=itemView.findViewById(R.id.tv_price_cake)
        val tv_cake_name:TextView=itemView.findViewById(R.id.tv_cake_name)
        val img_product_image:ImageView=itemView.findViewById(R.id.img_product_image)
        val rl_remove:RelativeLayout=itemView.findViewById(R.id.rl_remove)
        val tv_weigtt:TextView=itemView.findViewById(R.id.tv_weigtt)
        val tv_flaver:TextView=itemView.findViewById(R.id.tv_flaver)

        val tv_caketype:TextView=itemView.findViewById(R.id.tv_caketype)
        val tv_caketype_val:TextView=itemView.findViewById(R.id.tv_caketype_val)
        val tv_flaver_value:TextView=itemView.findViewById(R.id.tv_flaver_value)
        val tv_weigtt_val:TextView=itemView.findViewById(R.id.tv_weigtt_val)
        val  tv_remove:TextView=itemView.findViewById(R.id.tv_remove)
        val tv_movetolovelist:TextView=itemView.findViewById(R.id.tv_movetolovelist)
        val tv_quentity:TextView=itemView.findViewById(R.id.tv_quentity)
        val tv_quentity_val:TextView=itemView.findViewById(R.id.tv_quentity_val)
        val rl_weight:RelativeLayout=itemView.findViewById(R.id.rl_weight)
        var rl_quentity:RelativeLayout=itemView.findViewById(R.id.rl_quentity)
        var rl_movetolovelist:RelativeLayout=itemView.findViewById(R.id.rl_movetolovelist)
    }
}