package com.example.smartsitterclient

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class ReservationLayoutLab : AppCompatActivity() {
    private var buttonClick22: Button? = null
    private var viewPreviousData: TextView? = null
    private var viewChosenChairId: TextView? = null

    private var chairIdButton1: Button? = null

    private fun functionPerChair(chairId: String, localViewChosenChairId: TextView) {
        Toast.makeText(this, "click1", Toast.LENGTH_SHORT).show()
        val temp = "chosen chair: $chairId"
        localViewChosenChairId.text = temp
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_layout_lab)

        viewPreviousData = findViewById(R.id.previous_data)
        viewChosenChairId = findViewById(R.id.chosen_chair_id)

        val extras = intent.extras
        if (extras != null) {
            val localPreviousData = viewPreviousData
            val userName = extras.getString("idUserName")
            val time = extras.getString("time")
            var date = extras.getString("date")
            val duration = extras.getString("duration")
            date = date.toString()
            val tempText = "Date: $date, Time: $time, Duration: $duration"
            localPreviousData?.text = tempText
            viewPreviousData = localPreviousData
        }

        val localViewChosenChairId = viewChosenChairId

        //define all the chairs:
        chairIdButton1 = findViewById(R.id.chair_id_1)
        val localChairIdButton1 = chairIdButton1
        localChairIdButton1?.setOnClickListener {
            if (localViewChosenChairId != null) {
                functionPerChair("1", localViewChosenChairId)
                chairIdButton1?.isSelected = true
            }
        }
        chairIdButton1 = localChairIdButton1
        //finish to define all the chairs

        viewChosenChairId = localViewChosenChairId

        val isEnabled = false
        if (isEnabled){
            chairIdButton1?.isEnabled = false
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