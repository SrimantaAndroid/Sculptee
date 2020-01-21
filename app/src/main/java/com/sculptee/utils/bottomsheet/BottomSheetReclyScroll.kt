package com.sculptee.utils.bottomsheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sculptee.R
import com.sculptee.screens.cart.CartActivity

class BottomSheetReclyScroll(
    val cartActivity: CartActivity,
    val listposition: Int,
    val quantity: Int,
    val rangelist: ArrayList<Int>
): RecyclerView.Adapter<BottomSheetReclyScroll.ItemView>() {

   var tvarray= ArrayList<TextView>()
    var bottomsheetlistner:Bottomsheetlistner?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemView {
        val v:View=LayoutInflater.from(cartActivity).inflate(R.layout.bootom_sheet_horizental_scroll_item,null)
        bottomsheetlistner= cartActivity as Bottomsheetlistner?
        return  ItemView(v)
    }

    override fun getItemCount(): Int {
        return  rangelist.size
    }

    override fun onBindViewHolder(holder: ItemView, position: Int) {
        tvarray!!.add(holder.tv_count)
        holder.tv_count.setText((rangelist.get(position)).toString())

        if (quantity==rangelist.get(position)) {
            tvarray!!.get(position).setBackgroundDrawable(cartActivity.resources.getDrawable(R.drawable.circle_shape_bootm_selected))
        }
        else
            tvarray!!.get(position).setBackgroundDrawable(cartActivity.resources.getDrawable(R.drawable.circle_shape_bootm))

        holder.tv_count.setOnClickListener {
            for (i in 0 until tvarray!!.size){
                if (i==position) {
                    tvarray!!.get(i).setBackgroundDrawable(cartActivity.resources.getDrawable(R.drawable.circle_shape_bootm_selected))
                    bottomsheetlistner!!.onOptionClick(holder.tv_count.text.toString(),listposition)
                }
                else
                    tvarray!!.get(i).setBackgroundDrawable(cartActivity.resources.getDrawable(R.drawable.circle_shape_bootm))
            }
        }


    }

    class ItemView(itemView: View) : RecyclerView.ViewHolder(itemView){
       var tv_count:TextView=itemView.findViewById(R.id.tv_count)

    }

    interface  Bottomsheetlistner{
        fun onOptionClick(text: String,postition:Int)
    }
}