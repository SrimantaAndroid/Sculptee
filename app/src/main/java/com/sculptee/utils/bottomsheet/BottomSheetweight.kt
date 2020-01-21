package com.sculptee.utils.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.R
import com.sculptee.model.weightmodel.WeightSelectModel
import com.sculptee.screens.cart.CartActivity

class BottomSheetweight(
    val cartActivity: CartActivity,
    val position: Int,
    val weight: Int
) : BottomSheetDialogFragment(){
    public val rangelist=ArrayList<WeightSelectModel>()
    var bottomsheetlistner: BottomSheetReclyScrollweight.Bottomsheetqeightlistner?=null
    var  rec_sedlectquentity:RecyclerView?=null
    var btn_done:Button?=null
    var img_cross:ImageView?=null
    var tv_selquen:TextView?=null
    var bottomSheetReclyScroll:BottomSheetReclyScrollweight?=null
    var sewlected:String?=null
    var pos:Int?=null
    var deviceResolution:DeviceResolution?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.bottom_sheet_layout, container, false)
        rec_sedlectquentity=v.findViewById(R.id.rec_sedlectquentity)
        btn_done=v.findViewById(R.id.btn_done)
        img_cross=v.findViewById(R.id.img_cross)
        tv_selquen=v.findViewById(R.id.tv_selquen)
        deviceResolution= DeviceResolution(cartActivity)
        
        btn_done!!.typeface=deviceResolution!!.getMavenProBold(cartActivity)
        tv_selquen!!.typeface=deviceResolution!!.getMavenProBold(cartActivity)
        bottomsheetlistner=cartActivity
        tv_selquen!!.text="Select Weight"
        btn_done!!.setOnClickListener {
           //bottomsheetlistner!!.onOptionClick(sewlected!!,position)
            dismiss()
        }
        img_cross!!.setOnClickListener {
            dismiss()
        }
        return v

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        for (i in  1..10){
            if (i==weight) {
                val weightListAdapter=WeightSelectModel(true,i)
                rangelist.add(weightListAdapter)
            }else{
                val weightListAdapter=WeightSelectModel(false,i)
                rangelist.add(weightListAdapter)
            }
        }
        bottomSheetReclyScroll=BottomSheetReclyScrollweight(cartActivity,position,weight,rangelist,this,object : BottomSheetReclyScrollweight.Bottomsheetqeightlistner{
            override fun onweightClick(text: String, postition: Int) {
                sewlected=text
               // bottomSheetReclyScroll.
                bottomSheetReclyScroll!!.notifyDataSetChanged()
               // bottomsheetlistner!!.onOptionClick(text,position)
            }
        })
     //   bottomSheetReclyScroll!!.setHasStableIds(true)
        rec_sedlectquentity!!.setLayoutManager( LinearLayoutManager(cartActivity, LinearLayoutManager.HORIZONTAL, false))
        rec_sedlectquentity!!.adapter=bottomSheetReclyScroll
        rec_sedlectquentity!!.smoothScrollToPosition((rec_sedlectquentity!!.adapter!!.getItemCount()+weight)-rec_sedlectquentity!!.adapter!!.getItemCount());

    }

    //override fun onweightClick(text: String,position: Int) {
      //  bottomsheetlistner!!.onOptionClick(text)
      /*  sewlected=text
        bottomSheetReclyScroll!!.notifyDataSetChanged()
        bottomsheetlistner!!.onOptionClick(text,position)*/
    //}
}