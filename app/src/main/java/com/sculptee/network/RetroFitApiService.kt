package com.sculptee.network

import com.sculptee.network.NetworkUrlConstant.Companion.CONSUMER_KEY
import com.sculptee.network.NetworkUrlConstant.Companion.CONSUMER_SECRET
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.SigningInterceptor
import java.util.concurrent.TimeUnit
import com.google.gson.GsonBuilder
import com.google.gson.Gson



object RetroFitApiService {
   fun getRetrofitInstance(): Retrofit? {

              // OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
       val consumer:OkHttpOAuthConsumer=OkHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET)
      val  client:OkHttpClient =  OkHttpClient.Builder()
           .addInterceptor( SigningInterceptor(consumer))
           .readTimeout(60, TimeUnit.SECONDS)
           .connectTimeout(60, TimeUnit.SECONDS)
           .build();
       val gson = GsonBuilder()
           .setLenient()
           .create()

       val retrofit = Retrofit.Builder()
     .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
     .addConverterFactory(GsonConverterFactory.create(gson))
     .baseUrl(NetworkUrlConstant.BASE_URL)
           .client(client)
     .build()
    return retrofit
   }

}