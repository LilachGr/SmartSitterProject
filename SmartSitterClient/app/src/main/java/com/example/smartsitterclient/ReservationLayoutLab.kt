package com.example.smartsitterclient

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.*
import okhttp3.FormBody
import java.io.IOException
import android.content.Intent


class ReservationLayoutLab : AppCompatActivity() {
    private var userName: EditText? = null
    private var dateReservation: EditText? = null
    private var timeReservation: EditText? = null
    private var studentsNumber: EditText? = null
    private var sendButton: Button? = null
    private var okHttpClient: OkHttpClient? = null
    private val mapper = jacksonObjectMapper()
    private var buttonClick22: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_reservation_layout_lab)
        buttonClick22 = findViewById(R.id.send_reservatio22n)
        val localButtonClick22 = buttonClick22
        localButtonClick22?.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            //finish() //with this command the previous page closed and can't be reach with the back button (that built in the phone)
        }
        buttonClick22 = localButtonClick22
    }
}