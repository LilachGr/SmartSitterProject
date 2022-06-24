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
        val temp = "Building: $building, room: $room, chosen chair: $chairId"
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
                    if (allUnavailableChairs == ""){
                        return@runOnUiThread
                    }
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_layout_lab)
        supportActionBar?.setDisplayShowHomeEnabled(true);
        supportActionBar?.setLogo(R.mipmap.my_logo_round);
        supportActionBar?.setDisplayUseLogoEnabled(true);

        buttonClickNext = findViewById(R.id.home_button)
        val localButtonClickNext2 = buttonClickNext
        localButtonClickNext2?.isEnabled = false
        buttonClickNext = localButtonClickNext2

        //viewPreviousData = findViewById(R.id.previous_data)
        viewChosenChairId = findViewById(R.id.chosen_room_building)

        val extras = intent.extras
        if (extras != null) {
            //val localPreviousData = viewPreviousData
            userName = extras.getString("idUserName")
            time = extras.getString("time")
            date = extras.getString("date")
            duration = extras.getString("duration")
            date = date.toString()
            val tempText = "Date: $date, Time: $time, Duration: $duration"
            //localPreviousData?.text = tempText
            //viewPreviousData = localPreviousData
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
        val chairIdButton21 :Button = findViewById(R.id.chair_id_21)
        val chairIdButton22 :Button = findViewById(R.id.chair_id_22)
        val chairIdButton23 :Button = findViewById(R.id.chair_id_23)
        val chairIdButton24 :Button = findViewById(R.id.chair_id_24)
        val chairIdButton25 :Button = findViewById(R.id.chair_id_25)
        val chairIdButton26 :Button = findViewById(R.id.chair_id_26)
        val chairIdButton27 :Button = findViewById(R.id.chair_id_27)
        val chairIdButton28 :Button = findViewById(R.id.chair_id_28)
        val chairIdButton29 :Button = findViewById(R.id.chair_id_29)
        val chairIdButton30 :Button = findViewById(R.id.chair_id_30)
        val chairIdButton31 :Button = findViewById(R.id.chair_id_31)
        val chairIdButton32 :Button = findViewById(R.id.chair_id_32)
        val chairIdButton33 :Button = findViewById(R.id.chair_id_33)
        val chairIdButton34 :Button = findViewById(R.id.chair_id_34)
        val chairIdButton35 :Button = findViewById(R.id.chair_id_35)
        val chairIdButton36 :Button = findViewById(R.id.chair_id_36)
        val chairIdButton37 :Button = findViewById(R.id.chair_id_37)
        val chairIdButton38 :Button = findViewById(R.id.chair_id_38)
        val chairIdButton39 :Button = findViewById(R.id.chair_id_39)

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
        setChairs = setChairs.plusElement(chairIdButton21)
        setChairs = setChairs.plusElement(chairIdButton22)
        setChairs = setChairs.plusElement(chairIdButton23)
        setChairs = setChairs.plusElement(chairIdButton24)
        setChairs = setChairs.plusElement(chairIdButton25)
        setChairs = setChairs.plusElement(chairIdButton26)
        setChairs = setChairs.plusElement(chairIdButton27)
        setChairs = setChairs.plusElement(chairIdButton28)
        setChairs = setChairs.plusElement(chairIdButton29)
        setChairs = setChairs.plusElement(chairIdButton30)
        setChairs = setChairs.plusElement(chairIdButton31)
        setChairs = setChairs.plusElement(chairIdButton32)
        setChairs = setChairs.plusElement(chairIdButton33)
        setChairs = setChairs.plusElement(chairIdButton34)
        setChairs = setChairs.plusElement(chairIdButton35)
        setChairs = setChairs.plusElement(chairIdButton36)
        setChairs = setChairs.plusElement(chairIdButton37)
        setChairs = setChairs.plusElement(chairIdButton38)
        setChairs = setChairs.plusElement(chairIdButton39)

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
        chairIdButton21.setOnClickListener(this)
        chairIdButton22.setOnClickListener(this)
        chairIdButton23.setOnClickListener(this)
        chairIdButton24.setOnClickListener(this)
        chairIdButton25.setOnClickListener(this)
        chairIdButton26.setOnClickListener(this)
        chairIdButton27.setOnClickListener(this)
        chairIdButton28.setOnClickListener(this)
        chairIdButton29.setOnClickListener(this)
        chairIdButton30.setOnClickListener(this)
        chairIdButton31.setOnClickListener(this)
        chairIdButton32.setOnClickListener(this)
        chairIdButton33.setOnClickListener(this)
        chairIdButton34.setOnClickListener(this)
        chairIdButton35.setOnClickListener(this)
        chairIdButton36.setOnClickListener(this)
        chairIdButton37.setOnClickListener(this)
        chairIdButton38.setOnClickListener(this)
        chairIdButton39.setOnClickListener(this)
        //finish to define all the chairs

        val reservationBasicDetails = ReservationBasicDetails(
            userName.toString(),
            date.toString(), time.toString(), duration.toString(), "1"
        )

        getUnavailableSeatsFromJson(reservationBasicDetails)

        buttonClickSendReservation = findViewById(R.id.send_location)
        val localButtonClickSendReservation = buttonClickSendReservation
        localButtonClickSendReservation?.setOnClickListener {
            if (chairIdNumber != 0){
                // some chair is chosen and chairIdNumber contain it.
                // connect to server and try to insert the values, and return the result.
                localButtonClickSendReservation.isEnabled = false
                val reservationAllDetails = ReservationAllDetails(
                    userName.toString(), date.toString(), time.toString(), duration.toString(),
                    "1", chairIdNumber.toString(), room, building
                )
                val reservationAllDetailsJson = mapper1.writeValueAsString(reservationAllDetails)

                val text1 = reservationAllDetailsJson.toString()
                val formBody1: RequestBody = FormBody.Builder().add("reservationAllDetails", text1).build()
                val s1 = SimpleDataClasses()
                val url1 = s1.serverURL + s1.serverAllReservation
                val request1: Request = Request.Builder().url(url1).post(formBody1).build()
                okHttpClient1!!.newCall(request1).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        runOnUiThread {
                            val stringTemp = "server down"
                            Toast.makeText(applicationContext, stringTemp, Toast.LENGTH_SHORT).show()
                            localButtonClickSendReservation.isEnabled = true
                        }
                    }

                    @Throws(IOException::class)
                    override fun onResponse(call: Call, response: Response) {
                        runOnUiThread {
                            responseLabString = response.body!!.string()
                            if (responseLabString == "error") {
                                localButtonClickSendReservation.isEnabled = true
                                Toast.makeText(
                                    applicationContext,
                                    "Your chosen chair it taken.\nPlease choose a different chair.",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else if (responseLabString == "true") {
                                localButtonClickSendReservation.isEnabled = false
                                val localButtonClickNext = buttonClickNext
                                localButtonClickNext?.isEnabled = true
                                buttonClickNext = localButtonClickNext
                                Toast.makeText(applicationContext, "Data received!!", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                })
            }
        }
        buttonClickSendReservation = localButtonClickSendReservation

        buttonClickNext = findViewById(R.id.home_button)
        val localButtonClickNext = buttonClickNext
        localButtonClickNext?.setOnClickListener {
            if (responseLabString != "error") {
                startActivity(Intent(this, MainActivity::class.java))
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