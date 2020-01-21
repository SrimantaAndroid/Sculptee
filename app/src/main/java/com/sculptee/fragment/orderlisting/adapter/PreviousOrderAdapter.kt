package com.sculptee.fragment.orderlisting.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.R
import com.sculptee.screens.home.HomeActivity
import com.sculptee.utils.itemclickinterface.OnItemClickInterface

class PreviousOrderAdapter(
    val homeActivity: HomeActivity?,
    val param: OnItemClickInterface
) :
    RecyclerView.Adapter<PreviousOrderAdapter.PriviousOrderViewHolder>() {
    var deviceResolution: DeviceResolution?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriviousOrderViewHolder {
        deviceResolution= DeviceResolution(homeActivity)
        val view= LayoutInflater.from(homeActivity).inflate(R.layout.previous_order_item_layout,null)
        return PriviousOrderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  4
    }

    override fun onBindViewHolder(holder: PriviousOrderViewHolder, position: Int) {
        settypeface(holder)
        holder.tv_cake_name?.setText("hef")
        holder.imgdown?.setOnClickListener(View.OnClickListener { view ->
            holder.ll_oderinfo?.visibility=View.VISIBLE
            holder.imgdown?.visibility=View.INVISIBLE
        })
        holder.img_up?.setOnClickListener(View.OnClickListener { view ->
            holder.ll_oderinfo?.visibility=View.GONE
            holder.imgdown?.visibility=View.VISIBLE
        })
        holder.ll_oderinfo?.setOnClickListener {
            param.OnClickItem(position)
        }
    }

    private fun settypeface(holder: PriviousOrderViewHolder) {
        holder.tv_cake_name?.typeface=deviceResolution?.getMavenProRegular(homeActivity)
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
        holder.tv_paymentinfo?.typeface=deviceResolution?.getMavenProRegular(homeActivity)
        holder.tv_paymenttitle?.typeface=deviceResolution?.getMavenProRegular(homeActivity)
        holder.tv_paymentname?.typeface=deviceResolution?.getMavenProRegular(homeActivity)

        holder.tv_billingaddressd?.typeface=deviceResolution?.getMavenProRegular(homeActivity)
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
        holder.tv_orddrt_total?.typeface=deviceResolution?.getMavenProRegular(homeActivity)

        holder.tv_ordertotalval?.typeface=deviceResolution?.getMavenProRegular(homeActivity)

    }

    class PriviousOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
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
    }

}