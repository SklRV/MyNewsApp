package com.example.mynewsapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences : SharedPreferences = this.getSharedPreferences("newUser", Context.MODE_PRIVATE)
        val authorization= Intent(this, NewsApp::class.java)

        if(sharedPreferences.getString("userLogin", null) != null){
            startActivity(authorization)
        }
    }
}