package com.sculptee.fragment.orderlisting.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.R
import com.sculptee.model.orderlist.OrderListModel
import com.sculptee.screens.home.HomeActivity
import com.sculptee.utils.AppConstants
import com.sculptee.utils.itemclickinterface.OnItemClickInterface
import com.sculptee.utils.preferenceconstant.PreferenceConstantParam
import com.sculptee.utils.sheardpreference.AppPreferenceHalper
import java.util.ArrayList
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent

import android.net.Uri


class OrderListAdapter(
    var homeActivity: HomeActivity?,
    val currentoderlist: ArrayList<OrderListModel>,
   val param: OnItemClickInterface
) : RecyclerView.Adapter<OrderListAdapter.Viewholder>() {
    var deviceResolution:DeviceResolution?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        deviceResolution= DeviceResolution(homeActivity)
        val view= LayoutInflater.from(homeActivity).inflate(R.layout.order_item_layout,null)
        return Viewholder(view)
    }

    override fun getItemCount(): Int {
       return currentoderlist.size
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        settypeface(holder)
        holder.tv_cake_name?.setText(currentoderlist.get(position).p_name)
        holder.tv_flaver_value.setText(currentoderlist.get(position).P_flavour)
        holder.tv_caketype_val?.setText(currentoderlist.get(position).p_type)
        holder.tv_prod_price?.setText(currentoderlist.get(position).p_price)
        holder.tv_weigtt_val?.setText(currentoderlist.get(position).p_weight.toString())
        holder.tv_paymenttitle?.setText(currentoderlist.get(position).p_paymentmethod)
        holder.tv_paymentname?.setText(currentoderlist.get(position).p_methodtile)
        var sta=currentoderlist.get(position).p_status.substring(0, 1).toUpperCase() + currentoderlist.get(position).p_status.substring(1);
        holder.tv_delivarystatus?.setText(sta)

        Glide.with(homeActivity)
            .load(currentoderlist.get(position).prod_img)
            //  .apply(requestOptions)
           // .error(R.drawable.cake) //error
            .apply(RequestOptions().transforms(
                CenterCrop(),
                    RoundedCorners(homeActivity?.resources?.getDimension(R.dimen._3sdp)!!.toInt())
                )
            )
            .into(holder?.img_product_image)

        holder.rl_downimg?.setOnClickListener(View.OnClickListener { view ->
            holder.ll_oderinfo?.visibility=View.VISIBLE
            holder.imgdown?.visibility=View.INVISIBLE
        })
        holder.rl_movetoparraw?.setOnClickListener(View.OnClickListener { view ->
            holder.ll_oderinfo?.visibility=View.GONE
            holder.imgdown?.visibility=View.VISIBLE
        })
       /* holder.ll_oderinfo?.setOnClickListener { view ->
            param.OnClickItem(position)
        }*/
        holder.tv_email?.visibility=View.INVISIBLE
       holder.rl_orderiten?.setOnClickListener { view ->
            param.OnClickItem(position)
        }

        holder.tv_email?.setOnClickListener {
            val url = currentoderlist.get(position).order_invoice_url
          //  val i = Intent(Intent.ACTION_VIEW)
           // i.setData(Uri.parse(url));
           // homeActivity!!.startActivity(i);

        }
        if (currentoderlist.get(position).islast){
            holder.imgdown?.visibility=View.VISIBLE
            holder.tv_email?.visibility=View.VISIBLE
            holder.tv_billi_streetaddress?.setText(currentoderlist.get(position).p_b_streetaddress)
            holder.tv_billingcity?.setText(currentoderlist.get(position).p_b_city)
            holder.tv_billing_state?.setText(currentoderlist.get(position).p_b_state)
            holder.billing_pin?.setText(currentoderlist.get(position).p_b_pincode)

            holder.tv_ship_name?.setText(currentoderlist.get(position).p_s_name)
            holder.tv_ship_street_address?.setText(currentoderlist.get(position).p_s_streetname)
            holder.tv_ship_city?.setText(currentoderlist.get(position).p_s_city)
            holder.tv_shipstate?.setText(currentoderlist.get(position).p_s_state)
            holder.tv_ship_pin?.setText(currentoderlist.get(position).p_s_pincode)
            holder.tv_itemval.setText(String.format("%.02f",currentoderlist.get(position).total_amount))
            holder.tv_postagepackval?.setText(String.format("%.02f",  AppPreferenceHalper.read(PreferenceConstantParam.PACKING_COST,"")!!.toFloat()!!))
            holder.tv_shippingcostval?.setText(String.format("%.02f",  AppPreferenceHalper.read(PreferenceConstantParam.SHIPPINGCOST,"")!!.toFloat()!!))
             holder.tv_tax?.setText("Tax GST("+String.format("%.02f",  AppPreferenceHalper.read(PreferenceConstantParam.ORDERGST,"")!!.toFloat())+"%) : ")

           val  gestwithshippingbilling=(currentoderlist.get(position).total_amount+AppPreferenceHalper.read(PreferenceConstantParam.PACKING_COST,"")!!.toFloat()!!+
                   AppPreferenceHalper.read(PreferenceConstantParam.SHIPPINGCOST,"")!!.toFloat()!! )
            val gst:Float=(gestwithshippingbilling*AppPreferenceHalper.read(PreferenceConstantParam.ORDERGST,"")!!.toFloat()!!)/100

           // val gst:Float=(currentoderlist.get(position).total_amount*AppPreferenceHalper.read(PreferenceConstantParam.ORDERGST,"")!!.toFloat()!!)/100
            holder.tv_gstval?.setText(String.format("%.02f",gst))
           // val total:Float=currentoderlist.get(position).total_amount+AppConstants.POSTAGE_AND_PACKAGE+AppConstants.SHIPPINGCOST
            holder.tv_totalval?.setText(String.format("%.02f", currentoderlist.get(position).total_amount+
                    AppPreferenceHalper.read(PreferenceConstantParam.PACKING_COST,"")!!.toInt()!!+
                    AppPreferenceHalper.read(PreferenceConstantParam.SHIPPINGCOST,"")!!.toInt()!!+gst))

            holder.tv_ordertotalval?.setText(String.format("%.02f", currentoderlist.get(position).total_amount+
                    AppPreferenceHalper.read(PreferenceConstantParam.PACKING_COST,"")!!.toInt()!!+
                    AppPreferenceHalper.read(PreferenceConstantParam.SHIPPINGCOST,"")!!.toInt()!!+gst))

                //  holder.tv_ordertotalval?.setText(String.format("%.02f", currentoderlist.get(position).total_amount+AppConstants.POSTAGE_AND_PACKAGE+AppConstants.SHIPPINGCOST+gst))

        }
        else
            holder.imgdown?.visibility=View.INVISIBLE
    }

    private fun settypeface(holder: OrderListAdapter.Viewholder) {
        holder.tv_cake_name?.typeface=deviceResolution?.getMavenProBold(homeActivity)
        holder.tv_caketype?.typeface=deviceResolution?.getMavenProRegular(homeActivity)

        holder.tv_caketype_val?.typeface=deviceResolution?.getMavenProRegular(homeActivity)
        holder.tv_flaver?.typeface=deviceResolution?.getMavenProRegular(homeActivity)
        holder.tv_flaver_value?.typeface=deviceResolution?.getMavenProRegular(homeActivity)
        holder.tv_weigtt?.typeface=deviceResolution?.getMavenProRegular(homeActivity)

        holder.tv_weigtt_val?.typeface=deviceResolution?.getMavenProRegular(homeActivity)
        holder.tv_prod_price?.typeface=deviceResolution?.getMavenProRegular(homeActivity)
        holder.tv_email?.typeface=deviceResolution?.getMavenProRegular(homeActivity)
        holder.tv_delivarystatus?.typeface=deviceResolution?.getMavenProRegular(homeActivity)

        holder.tv_delivarytime?.typeface=deviceResolution?.getMavenProRegular(homeActivity)
        holder.tv_paymentinfo?.typeface=deviceResolution?.getMavenProBold(homeActivity)
        holder.tv_paymenttitle?.typeface=deviceResolution?.getMavenProRegular(homeActivity)
        holder.tv_paymentname?.typeface=deviceResolution?.getMavenProRegular(homeActivity)

        holder.tv_billingaddressd?.typeface=deviceResolution?.getMavenProBold(homeActivity)
        holder.tv_billi_streetaddress?.typeface=deviceResolution?.getMavenProRegular(homeActivity)
        holder.tv_billingcity?.typeface=deviceResolution?.getMavenProRegular(homeActivity)
        holder.tv_billing_state?.typeface=deviceResolution?.getMavenProRegular(homeActivity)
        holder.tv_paymentmethod?.typeface=deviceResolution?.getMavenProBold(homeActivity)
        holder.billing_pin?.typeface=deviceResolution?.getMavenProRegular(homeActivity)

        holder.tv_shipping?.typeface=deviceResolution?.getMavenProBold(homeActivity)
        holder.tv_ship_name?.typeface=deviceResolution?.getMavenProRegular(homeActivity)
        holder.tv_ship_street_address?.typeface=deviceResolution?.getMavenProRegular(homeActivity)
        holder.tv_ship_city?.typeface=deviceResolution?.getMavenProRegular(homeActivity)

        holder.tv_shipstate?.typeface=deviceResolution?.getMavenProRegular(homeActivity)
        holder.tv_ship_pin?.typeface=deviceResolution?.getMavenProRegular(homeActivity)
        holder.tv_ordersuppery?.typeface=deviceResolution?.getMavenProBold(homeActivity)
        holder.tv_item?.typeface=deviceResolution?.getMavenProRegular(homeActivity)

        holder.tv_itemval?.typeface=deviceResolution?.getMavenProRegular(homeActivity)
        holder.tv_postagepack?.typeface=deviceResolution?.getMavenProRegular(homeActivity)
        holder.tv_postagepackval?.typeface=deviceResolution?.getMavenProRegular(homeActivity)
        holder.tv_shippingcost?.typeface=deviceResolution?.getMavenProRegular(homeActivity)

        holder.tv_shippingcostval?.typeface=deviceResolution?.getMavenProRegular(homeActivity)
        holder.tv_tax?.typeface=deviceResolution?.getMavenProRegular(homeActivity)
        holder.tv_gstval?.typeface=deviceResolution?.getMavenProRegular(homeActivity)
        holder.tv_total?.typeface=deviceResolution?.getMavenProRegular(homeActivity)
        holder.tv_totalval?.typeface=deviceResolution?.getMavenProRegular(homeActivity)
        holder.tv_orddrt_total?.typeface=deviceResolution?.getMavenProBold(homeActivity)

        holder.tv_ordertotalval?.typeface=deviceResolution?.getMavenProBold(homeActivity)

    }

    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView){
          var tv_cake_name:TextView?=itemView.findViewById(R.id.tv_cake_name)
          var imgdown:ImageView?=itemView.findViewById(R.id.imgdown)
           var ll_oderinfo:LinearLayout?=itemView.findViewById(R.id.ll_oderinfo)
           var img_up:ImageView?=itemView.findViewById(R.id.img_up)
           var img_product_image:ImageView=itemView.findViewById(R.id.img_product_image)
        var tv_caketype:TextView?=itemView.findViewById(R.id.tv_caketype)
        var tv_caketype_val:TextView?=itemView.findViewById(R.id.tv_caketype_val)
        var tv_flaver:TextView?=itemView.findViewById(R.id.tv_flaver)
        var tv_flaver_value:TextView=itemView.findViewById(R.id.tv_flaver_value)
        var tv_weigtt:TextView?=itemView.findViewById(R.id.tv_weigtt)
        var tv_weigtt_val:TextView?=itemView.findViewById(R.id.tv_weigtt_val)
        var tv_prod_price:TextView?=itemView.findViewById(R.id.tv_prod_price)
        var tv_email:TextView?=itemView.findViewById(R.id.tv_email)
        var tv_delivarystatus:TextView=itemView.findViewById(R.id.tv_delivarystatus)
        var tv_delivarytime:TextView?=itemView.findViewById(R.id.tv_delivarytime)
        var tv_paymentinfo:TextView?=itemView.findViewById(R.id.tv_paymentinfo)
        var tv_paymentmethod:TextView?=itemView.findViewById(R.id.tv_paymentmethod)
        var tv_paymenttitle:TextView?=itemView.findViewById(R.id.tv_paymenttitle)
        var tv_paymentname:TextView=itemView.findViewById(R.id.tv_paymentname)

        var tv_billingaddressd:TextView?=itemView.findViewById(R.id.tv_billingaddressd)
        var tv_billi_streetaddress:TextView?=itemView.findViewById(R.id.tv_billi_streetaddress)
        var tv_billingcity:TextView?=itemView.findViewById(R.id.tv_billingcity)
        var tv_billing_state:TextView=itemView.findViewById(R.id.tv_billing_state)
        var billing_pin:TextView?=itemView.findViewById(R.id.billing_pin)
        var tv_shipping:TextView?=itemView.findViewById(R.id.tv_shipping)
        var tv_ship_name:TextView?=itemView.findViewById(R.id.tv_ship_name)
        var tv_ship_street_address:TextView?=itemView.findViewById(R.id.tv_ship_street_address)
        var tv_ship_city:TextView=itemView.findViewById(R.id.tv_ship_city)
        var tv_shipstate:TextView?=itemView.findViewById(R.id.tv_shipstate)
        var tv_ship_pin:TextView?=itemView.findViewById(R.id.tv_ship_pin)
        var tv_ordersuppery:TextView?=itemView.findViewById(R.id.tv_ordersuppery)
        var tv_item:TextView?=itemView.findViewById(R.id.tv_item)
        var tv_itemval:TextView=itemView.findViewById(R.id.tv_itemval)

        var tv_postagepack:TextView?=itemView.findViewById(R.id.tv_postagepack)
        var tv_postagepackval:TextView?=itemView.findViewById(R.id.tv_postagepackval)
        var tv_shippingcost:TextView=itemView.findViewById(R.id.tv_shippingcost)
        var tv_shippingcostval:TextView?=itemView.findViewById(R.id.tv_shippingcostval)
        var tv_tax:TextView?=itemView.findViewById(R.id.tv_tax)
        var tv_gstval:TextView?=itemView.findViewById(R.id.tv_gstval)
        var tv_total:TextView?=itemView.findViewById(R.id.tv_total)
        var tv_totalval:TextView=itemView.findViewById(R.id.tv_totalval)
        var tv_orddrt_total:TextView?=itemView.findViewById(R.id.tv_orddrt_total)
        var tv_ordertotalval:TextView=itemView.findViewById(R.id.tv_ordertotalval)
         var rl_orderiten:RelativeLayout=itemView.findViewById(R.id.rl_orderiten)
        var rl_downimg:RelativeLayout=itemView.findViewById(R.id.rl_downimg)
        var  rl_movetoparraw:RelativeLayout=itemView.findViewById(R.id.rl_movetoparraw)

    }
}