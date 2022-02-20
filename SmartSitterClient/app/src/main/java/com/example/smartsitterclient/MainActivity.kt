package com.example.smartsitterclient


//import android.R
//import sun.jvm.hotspot.utilities.IntArray

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
//import sun.jvm.hotspot.utilities.IntArray
import java.io.IOException


class MainActivity : AppCompatActivity() {
    // declare attribute for textview
    var pageNameTextView: TextView? = null
    var buttonClick22: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pageNameTextView = findViewById(R.id.page_name4)
        // creating a client
        val okHttpClient = OkHttpClient()
        // building a request
        //val request: Request = Request.Builder().url("http://10.100.102.10:5000/").build()
        val request: Request = Request.Builder().url("http://10.100.102.10:5000/").build()
        // making call asynchronously
        okHttpClient.newCall(request).enqueue(object : Callback {
            // called if server is unreachable
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "server down", Toast.LENGTH_SHORT).show()
                    val tempString = e.message
                    val localPageNameTextView = pageNameTextView
                    localPageNameTextView?.text = tempString
                    pageNameTextView = localPageNameTextView
                }
            }
            // called if we get a response from the server
            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    val tempString = response.body!!.string() + '!'
                    val localPageNameTextView = pageNameTextView
                    localPageNameTextView?.text = tempString
                    pageNameTextView = localPageNameTextView
                }
            }
        })
        buttonClick22 = findViewById(R.id.button1)
        val localButtonClick22 = buttonClick22
        localButtonClick22?.setOnClickListener {
            startActivity(Intent(this, ReservationFirstStepActivity::class.java))
            //finish() //with this command the previous page closed and can't be reach with the back button (that built in the phone)
        }
        buttonClick22 = localButtonClick22
    }
}