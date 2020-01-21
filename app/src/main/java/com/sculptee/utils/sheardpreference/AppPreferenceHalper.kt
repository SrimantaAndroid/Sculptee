package com.sculptee.utils.sheardpreference

import android.content.Context
import android.content.SharedPreferences
import com.sculptee.utils.AppConstants

object AppPreferenceHalper  {
    private lateinit var preference: SharedPreferences
    fun init(context: Context){
        preference=context.getSharedPreferences(AppConstants.PREFS_NAME,Context.MODE_PRIVATE)
    }
    fun write( key: String,value: String){
        val prefeditor:SharedPreferences.Editor= preference.edit()
        with(prefeditor){
            putString(key,value)
            commit()
        }
    }
    fun write( key: String,value: Int){
        val prefeditor:SharedPreferences.Editor= preference.edit()
        with(prefeditor){
            putInt(key,value)
            commit()
        }
    }

    fun write( key: String,value: Boolean){
        val prefeditor:SharedPreferences.Editor= preference.edit()
        with(prefeditor){
            putBoolean(key,value)
            commit()
        }
    }

    fun write( key: String,value: Long){
        val prefeditor:SharedPreferences.Editor= preference.edit()
        with(prefeditor){
            putLong(key,value)
            commit()
        }
    }

    fun read( key:String,value: String):String?{
        return preference.getString(key,value)
    }

    fun read( key:String,value: Int):Int?{
        return preference.getInt(key,value)
    }
    fun read( key:String,value: Float):Float?{
        return preference.getFloat(key,value)
    }

    fun read( key:String,value: Boolean):Boolean?{
        return preference.getBoolean(key,value)
    }

    fun read( key:String,value: Long):Long?{
        return preference.getLong(key,value)
    }
    fun clear(){
        val prefeditor:SharedPreferences.Editor= preference.edit()
        prefeditor.clear()
        prefeditor.commit()
    }
}