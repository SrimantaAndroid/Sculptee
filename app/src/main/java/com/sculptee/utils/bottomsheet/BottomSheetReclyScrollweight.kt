package com.sculptee.utils.bottomsheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sculptee.R
import com.sculptee.model.weightmodel.WeightSelectModel
import com.sculptee.screens.cart.CartActivity

class BottomSheetReclyScrollweight(
    val cartActivity: CartActivity,
    val listposition: Int,
    val weight: Int,
    val rangelist: ArrayList<WeightSelectModel>,
    val bottomSheetweight: BottomSheetweight,
    val listner: Bottomsheetqeightlistner
): RecyclerView.Adapter<BottomSheetReclyScrollweight.ItemView>() {

   var tvarray= ArrayList<TextView>()
    var bottomsheetlistner:Bottomsheetqeightlistner?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemView {
        val v:View=LayoutInflater.from(cartActivity).inflate(R.layout.bootom_sheet_horizental_scroll_item,null)
        bottomsheetlistner= cartActivity as Bottomsheetqeightlistner?
        return  ItemView(v)
    }

    override fun getItemCount(): Int {
        return  bottomSheetweight.rangelist.size
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    override fun onBindViewHolder(holder: ItemView, position: Int) {
        holder.setIsRecyclable(true);
        tvarray!!.add(holder.tv_count)
        holder.tv_count.setText((position+1).toString())

        if (bottomSheetweight.rangelist.get(position).isselect) {
            tvarray!!.get(position).setBackgroundResource(0)
            tvarray!!.get(position).setBackgroundDrawable(cartActivity.resources.getDrawable(R.drawable.circle_shape_bootm_selected))
        }
        else {
            tvarray!!.get(position).setBackgroundResource(0)
            tvarray!!.get(position).setBackgroundDrawable(cartActivity.resources.getDrawable(R.drawable.circle_shape_bootm))
        }

        holder.tv_count.setOnClickListener {

          /*  for (i in 0 until  bottomSheetweight.rangelist!!.size) {
                if ( bottomSheetweight.rangelist.get(i).isselect==false) {
                    bottomSheetweight.rangelist.get(i).isselect = true
                   // tvarray!!.get(position).setBackgroundDrawable(cartActivity.resources.getDrawable(R.drawable.circle_shape_bootm_selected))
                 //   bottomSheetweight.bottomsheetlistner!!.onweightClick(holder.tv_count.text.toString(),listposition)
                }
                else{
                    bottomSheetweight.rangelist.get(i).isselect = false
                    //tvarray!!.get(i).setBackgroundDrawable(cartActivity.resources.getDrawable(R.drawable.circle_shape_bootm))
                }

            }*/
           // bottomSheetweight.rangelist.get(position).isselect = true
          //  bottomSheetweight.bottomsheetlistner!!.onweightClick(holder.tv_count.text.toString(),listposition)

            // tvarray!!.get(position).setBackgroundDrawable(cartActivity.resources.getDrawable(R.drawable.circle_shape_bootm_selected))
            for (i in 0 until tvarray!!.size){
                if (i==position) {
                    tvarray!!.get(i).setBackgroundDrawable(cartActivity.resources.getDrawable(R.drawable.circle_shape_bootm_selected))
                    bottomsheetlistner!!.onweightClick(holder.tv_count.text.toString(),listposition)
                }
                else
                    tvarray!!.get(i).setBackgroundDrawable(cartActivity.resources.getDrawable(R.drawable.circle_shape_bootm))
            }
        }



    }

    class ItemView(itemView: View) : RecyclerView.ViewHolder(itemView){
       var tv_count:TextView=itemView.findViewById(R.id.tv_count)

    }

    interface  Bottomsheetqeightlistner{
        fun onweightClick(text: String,postition:Int)
    }
}