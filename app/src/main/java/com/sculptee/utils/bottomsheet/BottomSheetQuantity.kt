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
import com.sculptee.screens.cart.CartActivity

class BottomSheetQuantity(val cartActivity: CartActivity, val  position: Int,val quantity:Int) : BottomSheetDialogFragment(),BottomSheetReclyScroll.Bottomsheetlistner{
   //var bottomsheetlistner: BottomSheetReclyScroll.Bottomsheetlistner?=null
    var  rec_sedlectquentity:RecyclerView?=null
    var btn_done:Button?=null
    var img_cross:ImageView?=null
    var tv_selquen:TextView?=null
    var bottomSheetReclyScroll:BottomSheetReclyScroll?=null
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
       // bottomsheetlistner= cartActivity as BottomSheetReclyScroll.Bottomsheetlistner?
        btn_done!!.setOnClickListener {
          //  bottomsheetlistner!!.onOptionClick(sewlected!!,position)
            dismiss()
        }
        img_cross!!.setOnClickListener {
            dismiss()
        }
        return v

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rangelist=ArrayList<Int>()

         for (i in  1..10){
             rangelist.add(i)
         }


        bottomSheetReclyScroll=BottomSheetReclyScroll(cartActivity,position,quantity,rangelist)
        rec_sedlectquentity!!.setLayoutManager( LinearLayoutManager(cartActivity, LinearLayoutManager.HORIZONTAL, false))
        rec_sedlectquentity!!.adapter=bottomSheetReclyScroll
        rec_sedlectquentity!!.smoothScrollToPosition((rec_sedlectquentity!!.adapter!!.getItemCount()+quantity)-rec_sedlectquentity!!.adapter!!.getItemCount());
     //  rec_sedlectquentity!!.layoutManager!!.scrollToPosition(3)
    }

    override fun onOptionClick(text: String,position: Int) {
      //  bottomsheetlistner!!.onOptionClick(text)
        sewlected=text
    }
}