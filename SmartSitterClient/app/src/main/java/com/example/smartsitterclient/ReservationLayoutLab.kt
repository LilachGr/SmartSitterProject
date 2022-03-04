package com.example.smartsitterclient

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ReservationLayoutLab : AppCompatActivity() {
//    private var userName: EditText? = null
//    //private var dateReservation: EditText? = null
//    private var timeReservation: EditText? = null
//    private var studentsNumber: EditText? = null
//    private var sendButton: Button? = null
//    private var okHttpClient: OkHttpClient? = null
//    private val mapper = jacksonObjectMapper()
    private var buttonClick22: Button? = null
    private var viewDateTime: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_layout_lab)

        viewDateTime = findViewById(R.id.dateTime)

        val extras = intent.extras
        if (extras != null) {
            val localViewDateTime = viewDateTime
            val time = extras.getString("time")
            var date = extras.getString("date")
            val num = extras.getString("num")
            val duration = extras.getString("duration")
            date = date.toString()
            val tempText = "Date: $date, Time: $time, Duration: $duration\nNumber of Students:$num"
            localViewDateTime?.text = tempText
            viewDateTime = localViewDateTime
        }

        buttonClick22 = findViewById(R.id.send_location)
        val localButtonClick22 = buttonClick22
        localButtonClick22?.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            //finish() //with this command the previous page closed and can't be reach with the back button (that built in the phone)
        }
        buttonClick22 = localButtonClick22
    }
}