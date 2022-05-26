package com.example.smartsitterclient

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.*
import java.io.IOException


class ReservationLayoutLab : AppCompatActivity(), View.OnClickListener {
    private var buttonClickSendReservation: Button? = null
    private var buttonClickNext: Button? = null
    private var viewPreviousData: TextView? = null
    private var viewChosenChairId: TextView? = null
    private var setChairs = setOf<Button>()
    private var chairIdNumber = 0

    private var userName: String? = null
    private var time: String? = null
    private var date: String? = null
    private var duration: String? = null
    private val mapper = jacksonObjectMapper()
    private var okHttpClient: OkHttpClient? = null
    private var responseLabString = ""
    private var canReturnHome = false


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
            userName = extras.getString("idUserName")
            time = extras.getString("time")
            date = extras.getString("date")
            duration = extras.getString("duration")
            date = date.toString()
            val tempText = "Date: $date, Time: $time, Duration: $duration"
            localPreviousData?.text = tempText
            viewPreviousData = localPreviousData
        }

        okHttpClient = OkHttpClient()

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

                val reservationAllDetails = ReservationAllDetails(
                    userName.toString(), date.toString(), time.toString(), duration.toString(),
                    "1", chairIdNumber.toString(), "202", "604"
                )
                val reservationAllDetailsJson = mapper.writeValueAsString(reservationAllDetails)

                val text = reservationAllDetailsJson.toString()
                // we add the information we want to send in a form. each string we want to send should have a name. in our case we sent the dummyText with a name 'sample'
                val formBody: RequestBody = FormBody.Builder().add("reservationAllDetails", text).build()
                // while building request we give our form as a parameter to post()
                val s = SimpleDataClasses()
                val url = s.serverURL + s.serverAllReservation
                val request: Request = Request.Builder().url(url).post(formBody).build()
                okHttpClient!!.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        runOnUiThread { Toast.makeText(applicationContext, "server down", Toast.LENGTH_SHORT).show() }
                    }

                    @Throws(IOException::class)
                    override fun onResponse(call: Call, response: Response) {
                        runOnUiThread {
                            responseLabString = response.body!!.string()
                            if (responseLabString == "error") {
                                Toast.makeText(applicationContext, "Your chosen chair it taken, please try again!!!", Toast.LENGTH_LONG).show()
                            }
                            else if (responseLabString == "true") {
                                Toast.makeText(applicationContext, "Data received!!", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                })
            }
        }
        buttonClickSendReservation = localButtonClickSendReservation

        buttonClickNext = findViewById(R.id.next_button)
        val localButtonClickNext = buttonClickNext
        localButtonClickNext?.setOnClickListener {
            if (responseLabString != "error") {
                startActivity(Intent(this, SignIn::class.java))
            }
        }
        buttonClickNext = localButtonClickNext
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