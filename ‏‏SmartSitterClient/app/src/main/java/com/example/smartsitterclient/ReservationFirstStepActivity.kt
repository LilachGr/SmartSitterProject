package com.example.smartsitterclient

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import okhttp3.FormBody
import java.io.IOException


class ReservationFirstStepActivity : AppCompatActivity() {
    private var editText: EditText? = null
    private var button: Button? = null
    private var okHttpClient: OkHttpClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_first_step)
        editText = findViewById(R.id.dummy_text)
        button = findViewById(R.id.dummy_send)
        okHttpClient = OkHttpClient()
        val localButton = button
        localButton?.setOnClickListener {
            val localEditText = editText
            val dummyText = localEditText?.text.toString()
            // we add the information we want to send in a form. each string we want to send should have a name.
            // in our case we sent the dummyText with a name 'sample'
            val formBody: RequestBody = FormBody.Builder().add("sample", dummyText).add("hello", dummyText).build()
            // while building request we give our form as a parameter to post()
            val request: Request = Request.Builder().url("http://192.168.39.98:5000/debug").post(formBody).build()
            okHttpClient!!.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread { Toast.makeText(applicationContext, "server down", Toast.LENGTH_SHORT).show() }
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    if (response.body!!.string() == "received") {
                        runOnUiThread {
                            Toast.makeText(applicationContext, "data received", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }
        button = localButton
    }
}