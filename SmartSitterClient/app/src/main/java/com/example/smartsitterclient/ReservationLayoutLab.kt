package com.example.smartsitterclient

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class ReservationLayoutLab : AppCompatActivity(), View.OnClickListener {
    private var buttonClickSendReservation: Button? = null
    private var viewPreviousData: TextView? = null
    private var viewChosenChairId: TextView? = null
    private var setChairs = setOf<Button>()
    private var chairIdNumber = 0

    private fun functionPerChair(chairId: String, localViewChosenChairId: TextView) {
        val temp = "chosen chair: $chairId"
        localViewChosenChairId.text = temp
    }

    private fun enableChairs(){}

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

        //define all the chairs:
        val chairIdButton1 :Button = findViewById(R.id.chair_id_1)
        val chairIdButton2 :Button = findViewById(R.id.chair_id_2)

        setChairs = setChairs.plusElement(chairIdButton1)
        setChairs = setChairs.plusElement(chairIdButton2)

        chairIdButton1.setOnClickListener(this)
        chairIdButton2.setOnClickListener(this)
        //finish to define all the chairs

        val isEnabled = false
        if (isEnabled){
            chairIdButton1.isEnabled = false
        }

        buttonClickSendReservation = findViewById(R.id.send_location)
        val localButtonClickSendReservation = buttonClickSendReservation
        localButtonClickSendReservation?.setOnClickListener {
            if (chairIdNumber != 0){
                // some chair is chosen and chairIdNumber contain it.
                // connect to server and try to insert the values, and return the result.
                startActivity(Intent(this, MainActivity::class.java))
            }
            //finish() //with this command the previous page closed and can't be reach with the back button (that built in the phone)
        }
        buttonClickSendReservation = localButtonClickSendReservation
    }

    override fun onClick(v: View?) {
        val localViewChosenChairId = viewChosenChairId
        val idChair = v?.id
        viewChosenChairId = localViewChosenChairId
        for (b in setChairs) {
            b.isSelected = false
            if (idChair != null && idChair == b.id){
                b.isSelected = true
                if (localViewChosenChairId != null) {
                    val buttonText = b.text.toString()
                    chairIdNumber = buttonText.toInt()
                    functionPerChair(buttonText, localViewChosenChairId)
                }
            }
        }
    }
}