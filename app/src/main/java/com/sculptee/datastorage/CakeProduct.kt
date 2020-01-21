package com.sculptee.datastorage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cakeproduct")
data class CakeProduct(@PrimaryKey(autoGenerate = true)
                       var id: Int,
                       @ColumnInfo(name = "product_id") var product_id: String,
                       @ColumnInfo(name = "userid") var userid:String,
                       @ColumnInfo(name = "product_name") var product_name: String,
                       @ColumnInfo(name = "message_on_cake") var message_on_cake:String ,
                       @ColumnInfo(name = "imgurl") var imgurl:String,
                       @ColumnInfo(name = "product_price") var product_price:Int,
                       @ColumnInfo(name = "flovour") var flovour:String,
                       @ColumnInfo(name = "product_type") var product_type:String,

                       @ColumnInfo(name = "weight") var weight:Int,
                       @ColumnInfo(name = "product_total_price") var product_total_price:Int,
                       @ColumnInfo(name = "variation_id") var variation_id:Int,
                       @ColumnInfo(name = "variation_name") var variation_name:String,
                       @ColumnInfo(name = "withegg_per_pound") var withegg_per_pound:String,
                       @ColumnInfo(name = "eggless_per_pound") var eggless_per_pound:String,
                       @ColumnInfo(name = "quentity") var quentity :Int)

