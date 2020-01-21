package com.sculptee.datastorage

import androidx.room.*

@Dao
interface CakeProductDao {
    @Query("SELECT * FROM cakeproduct Where userid=:userid")
    fun getcatlist(userid:String):List<CakeProduct>

//onConflict = OnConflictStrategy.REPLACE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cakeProduct: CakeProduct)


    @Delete
    fun deleterecord(cakeProduct: CakeProduct)

    @Query("UPDATE cakeproduct SET weight=:weight WHERE product_id = :product_id")
    fun update(product_id:String,weight:Int)

    @Query("DELETE FROM cakeproduct")
    fun deletecartrecord()


    @Query("UPDATE cakeproduct SET weight=:weight WHERE product_id=:product_id")
    fun upadteprice(product_id:String,weight:Int)

    @Query("UPDATE cakeproduct SET quentity=:quantity WHERE product_id=:product_id")
    fun upadtequentity(product_id:String,quantity:Int)

  /*  @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insetaddress(address: Address)*/

    /*@Query("SELECT * FROM address Where userid=:userid")
    fun getaddress(userid: String)*/
}