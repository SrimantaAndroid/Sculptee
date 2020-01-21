package com.sculptee.utils.apprater

import android.R.id.edit
import android.app.ActionBar
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import com.sculptee.utils.sheardpreference.AppPreferenceHalper
import android.content.Intent
import android.net.Uri
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.LinearLayout
import com.facebook.FacebookSdk.getApplicationContext
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.sculptee.R
import com.sculptee.utils.Alert


class AppRater(var context : Activity?) {
    val mContext: Context=context!!
    var deviceResolution=DeviceResolution(context!!)

    private val APP_TITLE = "Sculptee"// App Name
    private val APP_PNAME = getApplicationContext().getPackageName();// Package Name

    private val DAYS_UNTIL_PROMPT = 2//Min number of days
    private val LAUNCHES_UNTIL_PROMPT = 10//Min number of launches


    public fun app_launched() {
        if (AppPreferenceHalper.read("dontshowagain", false)==true) { return  }
        // Increment launch counter
        val launch_count = AppPreferenceHalper.read("launch_count", 0)!! + 1
        AppPreferenceHalper.write("launch_count", launch_count!!)
        // Get date of first launch
        var date_firstLaunch: Long? =  AppPreferenceHalper.read("date_firstlaunch", 0L)
        if (date_firstLaunch!! == 0L) {
            date_firstLaunch = System.currentTimeMillis()
            AppPreferenceHalper.write("date_firstlaunch", date_firstLaunch)
        }

        // Wait at least n days before opening
        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch + (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
           // if (System.currentTimeMillis() >= date_firstLaunch!! + DAYS_UNTIL_PROMPT) {

               // if(AppPreferenceHalper.read("dontshowagain", false)!!)
                    // showRateDialog(mContext)
                Alert.showalertRateusapp(mContext!!,"If you enjoy using $APP_TITLE, please take a moment to rate it. Thanks for your support!")
            }
        }


    }


    fun showRateDialog(mContext: Context) {

        val dialog = Dialog(mContext)
        dialog.setTitle("Rate $APP_TITLE")

        val ll = LinearLayout(mContext)
        ll.orientation = LinearLayout.VERTICAL
       // ll.gravity=
        val lp = LinearLayout.LayoutParams((deviceResolution.getWidth(0.7)) as Int, LinearLayout.LayoutParams.WRAP_CONTENT)
        lp.setMargins(deviceResolution.getHeight(0.05), deviceResolution.getHeight(0.05),
            deviceResolution.getHeight(0.05), deviceResolution.getHeight(0.05));
        lp.gravity = Gravity.CENTER;
        // val lp = LinearLayout.LayoutParams(deviceResolution.getWidth(2.6), ActionBar.LayoutParams.WRAP_CONTENT)
      //  ll.layoutParams=lp
        val tv = TextView(mContext)
        tv.layoutParams=lp
        tv.text = "If you enjoy using $APP_TITLE, please take a moment to rate it. Thanks for your support!"
        tv.textSize = deviceResolution.getTextSize(4.0)
        tv.typeface= deviceResolution.getmavenproBlack(context)
        tv.setTextColor(context!!.resources.getColor(R.color.colorPrimaryDark))
       // tv.setPadding(30, 20, 30, 20)
        ll.addView(tv)

        val b1 = Button(mContext)
        b1.setText("Rate $APP_TITLE")
        val buttonlp = LinearLayout.LayoutParams((deviceResolution.getWidth(0.7)) as Int, LinearLayout.LayoutParams.WRAP_CONTENT)
        buttonlp.setMargins(deviceResolution.getHeight(0.05), deviceResolution.getHeight(0.01),
            deviceResolution.getHeight(0.05), deviceResolution.getHeight(0.01));
        buttonlp.gravity = Gravity.CENTER;
        b1.layoutParams=buttonlp
        b1.typeface= deviceResolution.getMavenProBold(context)
        b1.setOnClickListener {
            mContext.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$APP_PNAME")
                )
            )
            dialog.dismiss()
        }
       /* b1.setOnClickListener(object : View.OnClickListener() {
            fun onClick(v: View) {
                mContext.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$APP_PNAME")
                    )
                )
                dialog.dismiss()
            }
        })*/
        ll.addView(b1)



        val b2 = Button(mContext)
        b2.setText("Remind me later")
        b2.typeface= deviceResolution.getMavenProBold(context)
        b2.layoutParams=buttonlp
        b2.setOnClickListener {
            dialog.dismiss()
        }
       /* b2.setOnClickListener(object : OnClickListener() {
            fun onClick(v: View) {
                dialog.dismiss()
            }
        })*/
        ll.addView(b2)

        val b3 = Button(mContext)
        b3.setText("No, thanks")
        b3.layoutParams=buttonlp

            // deviceResolution.getHeight(0.05), 0);
        b3.typeface= deviceResolution.getMavenProBold(context)
        b3.setOnClickListener {
            AppPreferenceHalper.write("dontshowagain", true)
            dialog.dismiss()
        }
        /*b3.setOnClickListener(object : OnClickListener() {
            fun onClick(v: View) {
                if (editor != null) {
                    editor.putBoolean("dontshowagain", true)
                    editor.commit()
                }
                dialog.dismiss()
            }
        })*/
        ll.addView(b3)



        dialog.setContentView(ll)
        dialog.show()
    }
}