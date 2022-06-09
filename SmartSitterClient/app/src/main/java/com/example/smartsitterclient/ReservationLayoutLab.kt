package com.example.smartsitterclient

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.*
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
    private val mapper1 = jacksonObjectMapper()
    private var okHttpClient1: OkHttpClient? = null
    private var responseLabString = ""
    private var canReturnHome = false
    private var building = "604"
    private var room = "202"


    private fun functionPerChair(chairId: String, localViewChosenChairId: TextView) {
        val temp = "chosen chair: $chairId"
        localViewChosenChairId.text = temp
    }

    private fun getUnavailableSeatsFromJson(reservationBasicDetails: ReservationBasicDetails){
        val reservationBasicDetailsJson = mapper.writeValueAsString(reservationBasicDetails)

        val text = reservationBasicDetailsJson.toString()
        val formBody: RequestBody = FormBody.Builder().add("reservationTimeDate", text).build()
        val s = SimpleDataClasses()
        val url = s.serverURL + s.serverGetUnavailableChairs
        val request: Request = Request.Builder().url(url).post(formBody).build()
        okHttpClient!!.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread { Toast.makeText(applicationContext, "server down", Toast.LENGTH_SHORT).show() }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    var allUnavailableChairs = response.body!!.string()
                    val convertedObject: JsonArray = Gson().fromJson(allUnavailableChairs, JsonArray::class.java)
                    for (row in convertedObject) {
                        var rowNew = row.toString().replace("\\","").drop(1).dropLast(1)
                        val jsonObject = JsonParser().parse(rowNew).asJsonObject
                        val buildingRow = jsonObject["building"].toString().drop(1).dropLast(1)
                        val roomRow = jsonObject["room"].toString().drop(1).dropLast(1)
                        val chairIdRow = jsonObject["chairId"].toString().drop(1).dropLast(1)
                        var idRow = jsonObject["id"].toString().drop(1).dropLast(1)
                        if (buildingRow == building && roomRow == room) {
                            for (b in setChairs) {
                                if (b.text.toString() == chairIdRow) {
                                    b.isEnabled = false
                                }
                            }
                        }
                    }
                }
            }
        })
    }

    private fun enableUnavailableChairs(){
        var allUnavailableChairs = ""
        val reservationBasicDetails = ReservationBasicDetails(
            userName.toString(),
            date.toString(), time.toString(), duration.toString(), "1"
        )
        val reservationBasicDetailsJson = mapper.writeValueAsString(reservationBasicDetails)

        val text = reservationBasicDetailsJson.toString()
        val formBody: RequestBody = FormBody.Builder().add("reservationTimeDate", text).build()
        val s = SimpleDataClasses()
        val url = s.serverURL + s.serverGetUnavailableChairs
        val request: Request = Request.Builder().url(url).post(formBody).build()
        okHttpClient!!.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread { Toast.makeText(applicationContext, "server down", Toast.LENGTH_SHORT).show() }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    allUnavailableChairs = response.body!!.string()
                    var listAllUnavailableChairs = allUnavailableChairs.toList()
                    for (row in listAllUnavailableChairs) {
                        //var listRow = row.to
                        // id, reservation_date , start_time , end_time , duration , num_of_participants , user_id , location_id , updated
                        //var reservationDateSql = row[1]
                        print(row)
                    }
                }
            }
        })

"""        val isEnabled = false
        if (isEnabled){
            chairIdButton1.isEnabled = false
        }"""
    }

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
        okHttpClient1 = OkHttpClient()

        //define all the chairs:
        val chairIdButton1 :Button = findViewById(R.id.chair_id_1)
        val chairIdButton2 :Button = findViewById(R.id.chair_id_2)
        val chairIdButton3 :Button = findViewById(R.id.chair_id_3)
        val chairIdButton4 :Button = findViewById(R.id.chair_id_4)
        val chairIdButton5 :Button = findViewById(R.id.chair_id_5)
        val chairIdButton6 :Button = findViewById(R.id.chair_id_6)
        val chairIdButton7 :Button = findViewById(R.id.chair_id_7)
        val chairIdButton8 :Button = findViewById(R.id.chair_id_8)
        val chairIdButton9 :Button = findViewById(R.id.chair_id_9)
        val chairIdButton10 :Button = findViewById(R.id.chair_id_10)
        val chairIdButton11 :Button = findViewById(R.id.chair_id_11)
        val chairIdButton12 :Button = findViewById(R.id.chair_id_12)
        val chairIdButton13 :Button = findViewById(R.id.chair_id_13)
        val chairIdButton14 :Button = findViewById(R.id.chair_id_14)
        val chairIdButton15 :Button = findViewById(R.id.chair_id_15)
        val chairIdButton16 :Button = findViewById(R.id.chair_id_16)
        val chairIdButton17 :Button = findViewById(R.id.chair_id_17)
        val chairIdButton18 :Button = findViewById(R.id.chair_id_18)
        val chairIdButton19 :Button = findViewById(R.id.chair_id_19)
        val chairIdButton20 :Button = findViewById(R.id.chair_id_20)

        setChairs = setChairs.plusElement(chairIdButton1)
        setChairs = setChairs.plusElement(chairIdButton2)
        setChairs = setChairs.plusElement(chairIdButton3)
        setChairs = setChairs.plusElement(chairIdButton4)
        setChairs = setChairs.plusElement(chairIdButton5)
        setChairs = setChairs.plusElement(chairIdButton6)
        setChairs = setChairs.plusElement(chairIdButton7)
        setChairs = setChairs.plusElement(chairIdButton8)
        setChairs = setChairs.plusElement(chairIdButton9)
        setChairs = setChairs.plusElement(chairIdButton10)
        setChairs = setChairs.plusElement(chairIdButton11)
        setChairs = setChairs.plusElement(chairIdButton12)
        setChairs = setChairs.plusElement(chairIdButton13)
        setChairs = setChairs.plusElement(chairIdButton14)
        setChairs = setChairs.plusElement(chairIdButton15)
        setChairs = setChairs.plusElement(chairIdButton16)
        setChairs = setChairs.plusElement(chairIdButton17)
        setChairs = setChairs.plusElement(chairIdButton18)
        setChairs = setChairs.plusElement(chairIdButton19)
        setChairs = setChairs.plusElement(chairIdButton20)

        chairIdButton1.setOnClickListener(this)
        chairIdButton2.setOnClickListener(this)
        chairIdButton3.setOnClickListener(this)
        chairIdButton4.setOnClickListener(this)
        chairIdButton5.setOnClickListener(this)
        chairIdButton6.setOnClickListener(this)
        chairIdButton7.setOnClickListener(this)
        chairIdButton8.setOnClickListener(this)
        chairIdButton9.setOnClickListener(this)
        chairIdButton10.setOnClickListener(this)
        chairIdButton11.setOnClickListener(this)
        chairIdButton12.setOnClickListener(this)
        chairIdButton13.setOnClickListener(this)
        chairIdButton14.setOnClickListener(this)
        chairIdButton15.setOnClickListener(this)
        chairIdButton16.setOnClickListener(this)
        chairIdButton17.setOnClickListener(this)
        chairIdButton18.setOnClickListener(this)
        chairIdButton19.setOnClickListener(this)
        chairIdButton20.setOnClickListener(this)


        //finish to define all the chairs
        //enableUnavailableChairs()

        var allUnavailableChairs = ""
        val reservationBasicDetails = ReservationBasicDetails(
            userName.toString(),
            date.toString(), time.toString(), duration.toString(), "1"
                //"1","26/06/22", "21:00", "120", "1"
        )

        getUnavailableSeatsFromJson(reservationBasicDetails)

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
                val reservationAllDetailsJson = mapper1.writeValueAsString(reservationAllDetails)

                val text1 = reservationAllDetailsJson.toString()
                val formBody1: RequestBody = FormBody.Builder().add("reservationAllDetails", text1).build()
                val s1 = SimpleDataClasses()
                val url1 = s1.serverURL + s1.serverAllReservation
                val request1: Request = Request.Builder().url(url1).post(formBody1).build()
                okHttpClient1!!.newCall(request1).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        runOnUiThread { Toast.makeText(applicationContext, "server down", Toast.LENGTH_SHORT).show() }
                    }

                    @Throws(IOException::class)
                    override fun onResponse(call: Call, response: Response) {
                        runOnUiThread {
                            responseLabString = response.body!!.string()
                            if (responseLabString == "error") {
                                Toast.makeText(
                                    applicationContext,
                                    "Your chosen chair it taken, please try again!!!",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else if (responseLabString == "true") {
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