package com.sculptee.screens.help

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.sculptee.R

class HelpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View =LayoutInflater.from(this).inflate(R.layout.activity_help,null)
        setContentView(view)
    }
}
