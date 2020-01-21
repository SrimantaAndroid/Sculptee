package com.sculptee.fcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.sculptee.R

import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.transition.Transition
import android.widget.ImageView

import kotlin.random.Random

import android.widget.RemoteViews
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import android.media.RingtoneManager
import android.app.PendingIntent
import com.payu.magicretry.MainActivity
import android.content.Intent
import android.util.Base64
import com.sculptee.screens.splash.SplashActivity
import java.nio.charset.Charset


class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }
    private val TAG = "FCM Service"
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val data = remoteMessage.data
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        //  Log.d(TAG, "From: " + remoteMessage.from!!)
        Log.d(TAG, "Notification Message Body: " + data.get("message"))
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val NOTIFICATION_CHANNEL_ID = "Sculptee_channel"

        val intent = Intent(this, SplashActivity::class.java)
        val pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
/*
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notification.setSmallIcon(R.drawable.icon_transperent);
            notification.setColor(getResources().getColor(R.color.notification_color));
        } else {
            notification.setSmallIcon(R.drawable.icon);
        }*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "Your Notifications", NotificationManager.IMPORTANCE_HIGH)
            //  notificationChannel.description = "Description"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor =  R.color.noticolor
            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        // to diaplay notification in DND Mode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID)
             channel.canBypassDnd()
        }
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val  iconbitmap:Bitmap= BitmapFactory.decodeResource(applicationContext.getResources(), R.drawable.noti_large_icon);

       // val charset: Charset = Charsets.UTF_8
        //val  dataa:ByteArray = Base64.decode(data.get("message"), Base64.DEFAULT);
       // val  text:String=  String(dataa, charset);

        val datasd = Base64.decode(data.get("message"), Base64.DEFAULT)
        val decodebody = String(datasd, charset("UTF-8"))

        if(!data.get("imageURL").equals("")) {
            val futureTarget = Glide.with(this)
                .asBitmap()
                .load(data.get("imageURL"))
                // .load("http://13.234.121.130/wp-content/uploads/2019/08/Cake-4_feature_iamge-300x300.jpeg")
                .submit()

            val bitmap = futureTarget.get()
            val notificationLayout = RemoteViews(packageName, R.layout.notification_small)
           // notificationLayout.setImageViewBitmap(R.id.img_small, bitmap)
            notificationLayout.setTextViewText(R.id.tv_headersmall, data.get("title"))
            notificationLayout.setTextViewText(R.id.tv_message, data.get("message"))
            val notificationLayoutExpanded = RemoteViews(packageName, R.layout.notification_large)
            notificationLayoutExpanded.setImageViewBitmap(R.id.img_big, bitmap)
            notificationLayoutExpanded.setTextViewText(R.id.tv_header, data.get("title"))
            notificationLayoutExpanded.setTextViewText(R.id.tv_message_big, data.get("message"))

            notificationBuilder.setAutoCancel(true)
              //  .setColor(ContextCompat.getColor(this, R.color.noticolor))
                .setContentTitle(data.get("title"))
               // .setContentText(data.get("message"))
                //.setStyle( NotificationCompat.BigTextStyle().bigText(decodebody))
                .setContentText(decodebody)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSound(alarmSound)
                .setWhen(System.currentTimeMillis())
                .setOnlyAlertOnce(true)
                //.setSmallIcon(R.drawable.app_icon_noti)
                .setLargeIcon(iconbitmap)
                .setContentIntent(pIntent)
                .setStyle( NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmap).setSummaryText(decodebody))
              //  .setStyle(NotificationCompat.DecoratedCustomViewStyle())
               //  .setCustomContentView(notificationLayout)
                //.setCustomBigContentView(notificationLayoutExpanded)
                .setAutoCancel(true)
           // notificationBuilder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
          //  notificationBuilder.setStyle( NotificationCompat.BigTextStyle().bigText(decodebody));

        }else{
            notificationBuilder.setAutoCancel(true)
                //.setColor(ContextCompat.getColor(this, R.color.noticolor))
                .setContentTitle(data.get("title"))
               // .setStyle( NotificationCompat.BigTextStyle().bigText(decodebody))
                .setContentText(decodebody)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSound(alarmSound)
                .setWhen(System.currentTimeMillis())
                .setOnlyAlertOnce(true)
                .setContentIntent(pIntent)
                //.setLargeIcon(iconbitmap)
                //.setSmallIcon(R.drawable.app_icon_noti)
              //  .setStyle(NotificationCompat.DecoratedCustomViewStyle())

                .setAutoCancel(true)
            notificationBuilder.setStyle( NotificationCompat.BigTextStyle().bigText(decodebody));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.app_icon_noti);
            notificationBuilder.setColor(getResources().getColor(R.color.noticolor));
        } else {
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        }
      //  val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.birthday1)
       // notificationBuilder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(largeIcon))
        val r = java.util.Random()
        val random= r.nextInt(100 - 10 + 1) + 10
        notificationManager.notify(random, notificationBuilder.build())


    }
}