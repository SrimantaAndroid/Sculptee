package com.sculptee.network

import com.google.gson.JsonObject
import com.sculptee.model.address.AddressModel
import com.sculptee.model.events.EventResponse
import com.sculptee.model.loginmodel.LogInApiResponse
import com.sculptee.model.signupmodel.SignupResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import okhttp3.RequestBody
import retrofit2.http.HTTP



interface ApiInterface {
    @GET(NetworkUrlConstant.EVENTS)
    fun getevent(): Call<List<EventResponse>>

    @GET(NetworkUrlConstant.CATEGORY_DETAILS)
    fun getallevetcategoriwise(
                            @Query("page")page : Int,
                            @Query("per_page")per_page : Int):Call<ResponseBody>

    @GET(NetworkUrlConstant.CATEGORY_DETAILS)
    fun getevetcategoriwise(@Query("category")category : String,
                            @Query("page")page : Int,
                            @Query("per_page")per_page : Int):Call<ResponseBody>
                         //   @Query("per_page")per_page : Int):Call<List<EventListResponse>>

    @GET(NetworkUrlConstant.PRODUCT_DETAILS)
    fun  getproductdetails(@Path("id") id:Int):Call<ResponseBody>
/*
    @FormUrlEncoded
    @POST(NetworkUrlConstant.LOGIN)
    fun login(@Field("username") username:String,
              @Field("password") password :String):Call<LogInApiResponse>*/

    //@FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST(NetworkUrlConstant.LOGIN)
    fun login(@Body  body: JsonObject):Call<LogInApiResponse>

    @FormUrlEncoded
    @POST(NetworkUrlConstant.ADDTO_CART)
    fun addtocart(
                  @Field("product_id") product_id:Int,
                  @Field("quantity") quantity :Int,
                  @Field("variation_id") variation_id:String,
                  @Field("variation[attribute_pa_withegg-eggless]") withegg:String,
                  @Field("variation[attribute_pa_flavour]") flavour :String,
                  @Field("variation[attribute_pa_size]") size :Int,
                  @Field("sculptee_msg_txt") sculptee_msg_txt :String,
                  @Field("sculptee_hid_cart_total") sculptee_hid_cart_total:Int,
                  @Field("sculptee_hid_withegg_per_pound") sculptee_hid_withegg_per_pound :String,
                  @Field("sculptee_hid_eggless_per_pound") sculptee_hid_eggless_per_pound:String,
                  @Field("return_cart") return_cart :Boolean,
                  @Header("Authorization")auth: String ):Call<ResponseBody>

    @GET(NetworkUrlConstant.GET_CART_LIST)
    fun getcartlist( @Header("Authorization")auth: String ):Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST(NetworkUrlConstant.CREATE_ORDER)
    fun createorder(@Body  body: JsonObject ):Call<ResponseBody>

    @GET(NetworkUrlConstant.CUSTOMER_ORDER_LIST)
    fun getevetcategoriwise(@Query("customer")category : String): Call<ResponseBody>

    @GET(NetworkUrlConstant.POSTCODE_LOCATION_CHECK)
    fun checkpostcode(@Path("postcode") postcode:String):Call<ResponseBody>

    @FormUrlEncoded
    @POST(NetworkUrlConstant.HASHURL)
    fun gethashforpayment():Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST(NetworkUrlConstant.SIGNUP)
    fun signup(@Body  body: JsonObject):Call<SignupResponse>

    @POST(NetworkUrlConstant.ADD_FAVOURITE)
    fun Addtofavourite(@Body body: JsonObject):Call<ResponseBody>

    @GET(NetworkUrlConstant.FOVOURITELIST)
    fun  getfevouriteList(@Query("include") id: String?):Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @HTTP(method = "DELETE", path = "sculpteetheme/v1/wishlists/remove-item", hasBody = true)
    fun revovefrowishlist(@Body body: JsonObject):Call<ResponseBody>

    @GET(NetworkUrlConstant.GETSHIPPINGVAL)
    fun getgestandshipping():Call<ResponseBody>

    @POST(NetworkUrlConstant.ADD_FAVOURITE)
    fun updatePaymentinfp(@Body body: JsonObject):Call<ResponseBody>


    @PUT(NetworkUrlConstant.UPDATEORDER)
    fun Updateorderinfo(@Path("id") orderid : String,
                        @Body body: JsonObject):Call<ResponseBody>

   // @DELETE(NetworkUrlConstant.DETE_USER_TOKEN)
    //fun deletetoken(@Body body:JsonObject):Call<ResponseBody>

    @HTTP(method = "DELETE", path = NetworkUrlConstant.DETE_USER_TOKEN, hasBody = true)
    fun deletetoken(@Body  JsonObject: JsonObject): Call<ResponseBody>


    @Headers("Content-Type: application/json")
    @POST(NetworkUrlConstant.SIGNUP)
    fun signupwithraw(@Body  body: JsonObject):Call<ResponseBody>

    @GET(NetworkUrlConstant.COUPAN)
    fun getcoupandiscount(@Query("code") id:String,@Query("userid") userid:String):Call<ResponseBody>


    @GET(NetworkUrlConstant.USER_COUPONS)
    fun  getusercoupon(@Path("id") id:String):Call<ResponseBody>

    @GET(NetworkUrlConstant.FLAVOURAPI)
    fun getflavour():Call<ResponseBody>

    @PUT(NetworkUrlConstant.UPDATEPASSWORD)
    fun Changepassword(@Path("id") id : String,
                        @Body body: JsonObject):Call<ResponseBody>

    @POST(NetworkUrlConstant.REVIEWRATING)
    fun reviewrating(@Body body: JsonObject):Call<ResponseBody>

    @POST(NetworkUrlConstant.SAVEADDESSS)
    fun saveaddress(@Body body: JsonObject):Call<List<AddressModel>>

    @PUT(NetworkUrlConstant.UPDATEADDRESS)
    fun updateaddress(@Body body: JsonObject):Call<List<AddressModel>>

    @GET(NetworkUrlConstant.GETUSERADDRESS)
    fun getuseraddress(@Path("userid") id:String):Call<List<AddressModel>>

    @DELETE(NetworkUrlConstant.DELETEADDESS)
    fun deleteddress(@Path("userid") id: String,
                     @Path("address_index")address_index: String):Call<List<AddressModel>>

}