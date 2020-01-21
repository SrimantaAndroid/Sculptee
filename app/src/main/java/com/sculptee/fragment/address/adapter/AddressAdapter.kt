package com.sculptee.fragment.address.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.R
import com.sculptee.datastorage.Address
import com.sculptee.fragment.address.AddressFragment
import com.sculptee.screens.home.HomeActivity
import com.sculptee.utils.itemclickinterface.OnItemClickInterface

class AddressAdapter(
    val homeActivity: HomeActivity,
    val addressList: ArrayList<Address>,
    val addressFragment: AddressFragment,
    val onItemClickInterface: OnItemClickInterface
) : RecyclerView.Adapter<AddressAdapter.ViewHolder>() {
    var  deviceResolution: DeviceResolution?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        deviceResolution= DeviceResolution(homeActivity)
        val view= LayoutInflater.from(homeActivity).inflate(R.layout.address_items,null)
        return AddressAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  addressList.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_add_type?.setTypeface(deviceResolution?.getMavenProBold(homeActivity))
        holder.tv_deafult?.setTypeface(deviceResolution?.getMavenProBold(homeActivity))
        holder.tv_addressname?.setTypeface(deviceResolution?.getMavenProBold(homeActivity))
        holder.tv_streettownaddress?.setTypeface(deviceResolution?.getMavenProBold(homeActivity))
        holder.tv_statepin?.setTypeface(deviceResolution?.getMavenProBold(homeActivity))

        holder.tv_add_type!!.setText(addressList.get(position).addresstype+" Address")
        holder.tv_addressname!!.setText(addressList.get(position).fname+" "+addressList.get(position).lname)
        holder.tv_streettownaddress!!.setText(addressList.get(position).address1+","+addressList.get(position).address2)
        holder.tv_statepin!!.setText(addressList.get(position).state+","+addressList.get(position).pin)

        if (addressList.get(position).default) {
            holder.tv_deafult?.visibility = View.VISIBLE
            holder.imgdeleteaddress?.visibility=View.INVISIBLE
            holder.tv_add_type?.setTextColor(homeActivity.resources.getColor(R.color.colorPrimaryDark))
        }

        holder.imgdeleteaddress!!.setOnClickListener {
            addressFragment.calldeleteaddress(position)

        }

        holder.imgeditaddress!!.setOnClickListener {
            addressFragment.calleditaddress(position)

        }
        holder.rl_address!!.setOnClickListener {
            onItemClickInterface.OnClickItem(position)
        }

    }

    class ViewHolder( itemView: View): RecyclerView.ViewHolder(itemView){
        val tv_add_type:TextView?=itemView.findViewById(R.id.tv_add_type)
        val tv_deafult:TextView?=itemView.findViewById(R.id.tv_deafult)
        val tv_addressname:TextView?=itemView.findViewById(R.id.tv_addressname)
        val tv_streettownaddress:TextView?=itemView.findViewById(R.id.tv_streettownaddress)
        val tv_statepin:TextView?=itemView.findViewById(R.id.tv_statepin)
        val imgdeleteaddress:ImageView?=itemView.findViewById(R.id.imgdeleteaddress)
        val imgeditaddress:ImageView?=itemView.findViewById(R.id.imgeditaddress)
        val rl_address :RelativeLayout?=itemView.findViewById(R.id.rl_address)

    }
}