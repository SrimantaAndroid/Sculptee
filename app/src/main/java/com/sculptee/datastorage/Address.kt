package com.sculptee.datastorage

import androidx.room.ColumnInfo
import androidx.room.Entity
//@Entity(tableName = "address")
data class Address(@ColumnInfo(name="userid") var userid:String,
                   @ColumnInfo(name="addressid") var addid:String,
                   @ColumnInfo(name = "addresstype") var addresstype:String,
                   @ColumnInfo(name = "companyname") var companyname:String,
                   @ColumnInfo(name = "fname") var fname :String,
                   @ColumnInfo(name = "lname") var lname:String,
                   @ColumnInfo(name = "address1") var address1:String,
                   @ColumnInfo(name = "address2") var address2:String,
                   @ColumnInfo(name = "town") var town:String,
                   @ColumnInfo(name = "state") var state:String,
                   @ColumnInfo(name = "pin") var pin:String,
                   @ColumnInfo(name = "isdeafult") var default:Boolean)


