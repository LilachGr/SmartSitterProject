package com.example.smartsitterclient

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    var buttonClick22: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayShowHomeEnabled(true);
        supportActionBar?.setLogo(R.mipmap.my_logo_round);
        supportActionBar?.setDisplayUseLogoEnabled(true);
        buttonClick22 = findViewById(R.id.button1)
        val localButtonClick22 = buttonClick22
        localButtonClick22?.setOnClickListener {
            startActivity(Intent(this, SignIn::class.java))
            //finish() //with this command the previous page closed and can't be reach with the back button (that built in the phone)
        }
        buttonClick22 = localButtonClick22
    }
}