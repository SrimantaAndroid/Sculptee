package com.sculptee.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.facebook.FacebookSdk.getApplicationContext
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.R
import com.sculptee.screens.cart.CartActivity
import com.sculptee.screens.home.HomeActivity
import com.sculptee.screens.placeorder.PlaceOrderActivity
import com.sculptee.utils.sheardpreference.AppPreferenceHalper


class Alert {
     companion object {
         fun showalert(activity: Activity, message: String) {
             //  var deviceResolution:DeviceResolution?=null
             var deviceResolution = DeviceResolution(activity)
             val alertDialog = Dialog(activity, R.style.Transparent)
             /*alertDialog.setTitle(activity.resources.getString(R.string.app_name))
             alertDialog.setMessage(message)*/
             alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
             val view: View = LayoutInflater.from(activity).inflate(R.layout.alertlayout, null)
             alertDialog.setContentView(view)
             alertDialog.setCancelable(false)
             val tv_message: TextView = view.findViewById(R.id.tv_message)
             val btn_ok: Button = view.findViewById(R.id.btn_ok)
             btn_ok.typeface = deviceResolution.getMavenProRegular(activity)
             tv_message.typeface = deviceResolution.getMavenProRegular(activity)
             btn_ok.setOnClickListener {
                 alertDialog.dismiss()
             }
             tv_message.setText(message)
             alertDialog.show()
             /*alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                 DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
             alertDialog.show()*/
         }

         fun showyesnoalert(activity: HomeActivity, message: String) {
             // var deviceResolution:DeviceResolution?=null
             var deviceResolution = DeviceResolution(activity)
             val alertDialog = Dialog(activity, R.style.Transparent)
             /*alertDialog.setTitle(activity.resources.getString(R.string.app_name))
             alertDialog.setMessage(message)*/
             alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
             val view: View =
                 LayoutInflater.from(activity).inflate(R.layout.alert_layout_yesno, null)
             alertDialog.setContentView(view)
             alertDialog.setCancelable(false)
             val tv_message: TextView = view.findViewById(R.id.tv_message)
             val btn_ok: Button = view.findViewById(R.id.btn_ok)
             val btn_no: Button = view.findViewById(R.id.btn_no)
             btn_ok.typeface = deviceResolution.getMavenProRegular(activity)
             btn_no.typeface = deviceResolution.getMavenProRegular(activity)
             tv_message.typeface = deviceResolution.getMavenProRegular(activity)
             btn_ok.setOnClickListener {
                 alertDialog.dismiss()
                 // activity.alertyesfuncation();
                 activity.calllogoutdeleteusertoken()
             }
             btn_no.setOnClickListener {
                 alertDialog.dismiss()

             }
             tv_message.setText(message)
             alertDialog.show()
             /*alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                 DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
             alertDialog.show()*/
         }

         fun showalertnocartitem(activity: CartActivity, message: String) {
             // var deviceResolution:DeviceResolution?=null
             var deviceResolution = DeviceResolution(activity)
             val alertDialog = Dialog(activity, R.style.Transparent)
             /*alertDialog.setTitle(activity.resources.getString(R.string.app_name))
             alertDialog.setMessage(message)*/
             alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
             val view: View = LayoutInflater.from(activity).inflate(R.layout.alertlayout, null)
             alertDialog.setContentView(view)
             alertDialog.setCancelable(false)
             val tv_message: TextView = view.findViewById(R.id.tv_message)
             val btn_ok: Button = view.findViewById(R.id.btn_ok)
             btn_ok.typeface = deviceResolution.getMavenProRegular(activity)
             tv_message.typeface = deviceResolution.getMavenProRegular(activity)
             btn_ok.setOnClickListener {
                 alertDialog.dismiss()
                 activity.finish()
             }
             tv_message.setText(message)
             alertDialog.show()
             /*alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                 DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
             alertDialog.show()*/
         }


         fun showalertinternerservererror(activity: HomeActivity, message: String) {
             // var deviceResolution:DeviceResolution?=null
             var deviceResolution = DeviceResolution(activity)
             val alertDialog = Dialog(activity, R.style.Transparent)
             /*alertDialog.setTitle(activity.resources.getString(R.string.app_name))
             alertDialog.setMessage(message)*/
             alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
             val view: View = LayoutInflater.from(activity).inflate(R.layout.alertlayout, null)
             alertDialog.setContentView(view)
             alertDialog.setCancelable(false)
             val tv_message: TextView = view.findViewById(R.id.tv_message)
             val btn_ok: Button = view.findViewById(R.id.btn_ok)
             btn_ok.typeface = deviceResolution.getMavenProRegular(activity)
             tv_message.typeface = deviceResolution.getMavenProRegular(activity)
             btn_ok.setOnClickListener {
                 alertDialog.dismiss()
                 activity.finish()
             }
             tv_message.setText(message)
             alertDialog.show()
             /*alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                 DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
             alertDialog.show()*/
         }


         fun showalertRateusapp(activity: Context, message: String) {
             val APP_PNAME = getApplicationContext().getPackageName();// Package Name
             // var deviceResolution:DeviceResolution?=null
             var deviceResolution = DeviceResolution(activity as Activity?)
             val alertDialog = Dialog(activity, R.style.Transparent)
             /*alertDialog.setTitle(activity.resources.getString(R.string.app_name))
             alertDialog.setMessage(message)*/
             alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
             val view: View =
                 LayoutInflater.from(activity).inflate(R.layout.alert_rating_layout, null)
             alertDialog.setContentView(view)
             alertDialog.setCancelable(false)
             val tv_message: TextView = view.findViewById(R.id.tv_message)
             val btnrateus: Button = view.findViewById(R.id.btnrateus)
             val btn_remindermelater: Button = view.findViewById(R.id.btn_remindermelater)
             val btn_nothanks: Button = view.findViewById(R.id.btn_nothanks)
             btnrateus.typeface = deviceResolution.getMavenProRegular(activity)
             btn_remindermelater.typeface = deviceResolution.getMavenProRegular(activity)
             btn_nothanks.typeface = deviceResolution.getMavenProRegular(activity)
             tv_message.typeface = deviceResolution.getMavenProRegular(activity)
             btnrateus.setOnClickListener {
                 alertDialog.dismiss()
                 activity.startActivity(
                     Intent(
                         Intent.ACTION_VIEW,
                         Uri.parse("market://details?id=$APP_PNAME")
                     )
                 )
                 // activity.finish()
             }
             btn_remindermelater.setOnClickListener {
                 alertDialog.dismiss()
             }
             btn_nothanks.setOnClickListener {
                 alertDialog.dismiss()
                 AppPreferenceHalper.write("dontshowagain", true)
             }
             tv_message.setText(message)
             alertDialog.show()
             /*alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                 DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
             alertDialog.show()*/
         }


         fun showalertforPaymentSucess(activity: PlaceOrderActivity, message: String, payuResponse: String) {
             // var deviceResolution:DeviceResolution?=null
             var deviceResolution = DeviceResolution(activity)
             val alertDialog = Dialog(activity, R.style.Transparent)
             /*alertDialog.setTitle(activity.resources.getString(R.string.app_name))
        alertDialog.setMessage(message)*/
             alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
             val view: View =
                 LayoutInflater.from(activity).inflate(R.layout.alertlayout, null)
             alertDialog.setContentView(view)
             alertDialog.setCancelable(false)
             val tv_message: TextView = view.findViewById(R.id.tv_message)
             val btn_ok: Button = view.findViewById(R.id.btn_ok)
           //  val btn_no: Button = view.findViewById(R.id.btn_no)
             btn_ok.typeface = deviceResolution.getMavenProRegular(activity)
           //  btn_no.typeface = deviceResolution.getMavenProRegular(activity)
             tv_message.typeface = deviceResolution.getMavenProRegular(activity)
             btn_ok.setOnClickListener {
                 alertDialog.dismiss()
                 // activity.alertyesfuncation();
                 activity.callapiforpaymentsucesstranscation(payuResponse)
             }

             tv_message.setText(message)
             alertDialog.show()
             /*alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
            DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
        alertDialog.show()*/
         }
     }
}