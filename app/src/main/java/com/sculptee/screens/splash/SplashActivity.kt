package com.sculptee.screens.splash

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.sculptee.R
import com.sculptee.screens.home.HomeActivity
import com.sculptee.screens.imageslider.ImageSliderActivity
import com.sculptee.screens.sociallogin.SocialSignActivity
import com.sculptee.utils.AppConstants
import com.sculptee.utils.preferenceconstant.PreferenceConstantParam
import com.sculptee.utils.sheardpreference.AppPreferenceHalper
import android.hardware.usb.UsbDevice.getDeviceId
import android.provider.Settings
import android.telephony.TelephonyManager
import android.Manifest.permission
import android.Manifest.permission.READ_PHONE_STATE
import android.content.pm.PackageManager

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.util.Base64.NO_WRAP

import android.util.Base64


class SplashActivity : AppCompatActivity() {
    var view: View? = null
    private val TAG = " FCM TOKEN"
    val REQUEST_READ_PHONE_STATE:Int=222
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = LayoutInflater.from(this).inflate(R.layout.activity_splash, null)
        setContentView(view)
        AppPreferenceHalper.init(this)
       // init()
        getdeviceimeino()

        //SHA1: A2:33:75:EC:E6:D1:D1:B4:51:50:FD:9B:54:E0:96:50:BC:13:B1:99
        // store SHA1: A2:33:75:EC:E6:D1:D1:B4:51:50:FD:9B:54:E0:96:50:BC:13:B1:99
       /* val sha1 = byteArrayOf(
            0xA2.toByte(), 0x33, 0x75, 0xEC.toByte(), 0xE6.toByte(),
            0xD1.toByte(), 0xD1.toByte(), 0xB4.toByte(), 0x51, 0x50, 0xFD.toByte(),
            0x9B.toByte(),
            0x54,
            0xE0.toByte(),
            0x96.toByte(),
            0x50.toByte(),
            0xBC.toByte(),
            0x13,
            0xB1.toByte(),
            0x99.toByte()
        )
        System.out.println("keyhashGooglePlaySignIn:" + Base64.encodeToString(sha1, Base64.NO_WRAP))*/
       // gettokenfromfcm()
    }

    private fun getdeviceimeino() {
        val permissionCheck =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf<String>(Manifest.permission.READ_PHONE_STATE),
                REQUEST_READ_PHONE_STATE
            )
        } else {
            val TelephonyMgr = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
               val imei = TelephonyMgr.deviceId
            AppPreferenceHalper.write(PreferenceConstantParam.DEVICE_IMEI,imei.toString())
            gettokenfromfcm()
            init()
        }
    }

    @SuppressLint("MissingPermission")
    private fun gettokenfromfcm() {
        //  val TelephonyMgr = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        //   val imei = TelephonyMgr.deviceId
        //  val imei= Settings.Secure.getString(context.getContenResolver(), InstanceID.getInstance(context).getId());
        //  AppPreferenceHalper.write(PreferenceConstantParam.DEVICE_IMEI,imei.toString())
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token
                AppPreferenceHalper.write(PreferenceConstantParam.DEVICE_TOKEN, token!!.toString())
                // Log and toast
                // val msg = getString("tdsd", token)
                Log.d(TAG, token)
               // Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
            })
    }

    private fun init() {
        Handler().postDelayed({
            if (AppPreferenceHalper.read(PreferenceConstantParam.isLogIn, false) == false) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }

        }, AppConstants.APPLAUNCHTIME)
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        when (requestCode) {
            REQUEST_READ_PHONE_STATE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val TelephonyMgr =
                        getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                    val imei = TelephonyMgr.deviceId
                    AppPreferenceHalper.write(PreferenceConstantParam.DEVICE_IMEI, imei.toString())
                    gettokenfromfcm()
                    init()
                }
                else {
                    //getdeviceimeino()
                    gettokenfromfcm()
                    init()
                }
            }


        }
    }
}