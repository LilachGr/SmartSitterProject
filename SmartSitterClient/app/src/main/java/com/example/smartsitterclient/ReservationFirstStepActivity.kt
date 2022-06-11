package com.example.smartsitterclient

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.*
import okhttp3.FormBody
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities

class ReservationFirstStepActivity : AppCompatActivity() {
    //private var userName: EditText? = null
    private var userName: String = ""
    private var dateReservation: EditText? = null
    private var timeReservation: EditText? = null
    private var duration: EditText? = null
    private var sendButton: Button? = null
    private var okHttpClient: OkHttpClient? = null
    private var myServerResponse: String? = null
    private var reservationLaterMessage: TextView? = null
    private var spinnerDuration: Spinner? = null
    private var myAdapter: ArrayAdapter<String>? = null
    private var thisReservationActivity: ReservationFirstStepActivity? = null
    private var nextButton: Button? = null

    private val mapper = jacksonObjectMapper()

    private fun validDate(strDate: String): Boolean {
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        format.isLenient = false
        return try {
            format.parse(strDate)
            true
        } catch (e: ParseException) {
            println(
                "Date " + strDate + " is not valid according to " +
                        format.toPattern() + " pattern."
            )
            false
        }
    }

    private fun validTime(strDate: String): Boolean {
        val format = SimpleDateFormat("HH:mm",Locale.getDefault())
        format.isLenient = false
        return try {
            format.parse(strDate)
            true
        } catch (e: ParseException) {
            println(
                "Time " + strDate + " is not valid according to " +
                        format.toPattern() + " pattern."
            )
            false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_reservation_first_step)

        spinnerDuration = findViewById(R.id.spinner_duration_layout)
        myAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, resources.getStringArray(R.array.spinner_duration))
        myAdapter!!.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        val localSpinnerDuration = spinnerDuration
        if (localSpinnerDuration != null) {
            localSpinnerDuration.adapter = myAdapter
        }
        spinnerDuration = localSpinnerDuration

        //userName = findViewById(R.id.user_name)
        dateReservation = findViewById(R.id.date_reservation)
        timeReservation = findViewById(R.id.time_reservation)
        //duration = findViewById(R.id.duration)

        reservationLaterMessage = findViewById(R.id.reservation_later_message)
        sendButton = findViewById(R.id.send_reservation)
        nextButton = findViewById(R.id.next_button)
        okHttpClient = OkHttpClient()
        val localButton = sendButton

        var localUserName: EditText? = null
        var localDateReservation: EditText? = null
        var localTimeReservation: EditText? = null
        var localDuration: EditText? = null
        //var localDuration: Spinner? = null
        var stringServer: String? = null
        var localDuration2: Spinner? = null
        var localDurationString = ""

        var idUserName: String = ""

        val extras = intent.extras
        if (extras != null) {
            idUserName = extras.getString("idUserName").toString()
        }

        userName = idUserName

        localButton?.setOnClickListener {
            //localUserName = userName
            localDateReservation = dateReservation
            localTimeReservation = timeReservation
            localDuration = duration
            localDuration2 = spinnerDuration
            localDurationString = localDuration2!!.selectedItem.toString()

            val isValidDate: Boolean = validDate(localDateReservation?.text.toString())
            if (!isValidDate) {
                val localViewDateTime = reservationLaterMessage
                stringServer = "error"
                val stringTemp2 = "Your reservation details are not correct. Please TRY AGAIN!!"
                localViewDateTime?.text = stringTemp2
                reservationLaterMessage = localViewDateTime
                return@setOnClickListener
            }

            val isValidTime: Boolean = validTime(localTimeReservation?.text.toString())
            if (!isValidTime) {
                val localViewDateTime = reservationLaterMessage
                stringServer = "error"
                val stringTemp2 = "Your reservation details are not correct. Please TRY AGAIN!!"
                localViewDateTime?.text = stringTemp2
                reservationLaterMessage = localViewDateTime
                return@setOnClickListener
            }

            val reservationBasicDetails = ReservationBasicDetails(
                //localUserName?.text.toString(),
                userName,
                localDateReservation?.text.toString(), localTimeReservation?.text.toString(),
                //localDuration?.text.toString(),
                localDurationString,
                "1"
            )
            val reservationBasicDetailsJson = mapper.writeValueAsString(reservationBasicDetails)

            val text = reservationBasicDetailsJson.toString()
            // we add the information we want to send in a form. each string we want to send should have a name. in our case we sent the dummyText with a name 'sample'
            val formBody: RequestBody = FormBody.Builder().add("reservationBasicDetails", text).build()
            // while building request we give our form as a parameter to post()
            val s = SimpleDataClasses()
            val url = s.serverURL + s.serverReservationFirstStep
            val request: Request = Request.Builder().url(url).post(formBody).build()
            thisReservationActivity = this
            okHttpClient!!.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread { Toast.makeText(applicationContext, "server down", Toast.LENGTH_SHORT).show() }
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    runOnUiThread {
                        stringServer = response.body!!.string()
                        val localViewDateTime = reservationLaterMessage
                        var stringTemp: String? = null
                        if (stringServer == "time_available") {
                            stringTemp = "Data received!!"
                        }
                        if (stringServer == "time_not_available") {
                            stringTemp = "Your chosen time is full, please choose another time!!"
                        }
                        if (stringServer == "user_has_this_date") {
                            stringTemp = "Your already booked in this date, please choose another date!!"
                        }
                        if (stringServer == "smaller_date_time") {
                            stringTemp = "Your reservation details are not correct. Please TRY AGAIN!!"
                        }
                        localViewDateTime?.text = stringTemp
                        reservationLaterMessage = localViewDateTime
                    }
                }
            })
        }
        sendButton = localButton

        val localNextButton = nextButton
        localNextButton?.setOnClickListener {
            if (stringServer == "time_available"){
                val i = Intent(this, ReservationLayoutLab::class.java)
                i.putExtra("idUserName", userName)
                i.putExtra("date", localDateReservation?.text.toString())
                i.putExtra("time", localTimeReservation?.text.toString())
                //i.putExtra("duration", localDuration?.text.toString())
                i.putExtra("duration", localDurationString)
                startActivity(i)
            }
        }
        nextButton = localNextButton
    }
}