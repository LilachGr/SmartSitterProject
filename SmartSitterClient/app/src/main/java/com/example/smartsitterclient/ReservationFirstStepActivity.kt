package com.example.smartsitterclient

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
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
    private var userName: EditText? = null
    private var dateReservation: EditText? = null
    private var timeReservation: EditText? = null
    private var studentsNumber: EditText? = null
    private var duration: EditText? = null
    private var sendButton: Button? = null
    private var okHttpClient: OkHttpClient? = null
    private var myServerResponse: String? = null
    private var reservationLaterMessage: TextView? = null
    private var thisReservationActivity: ReservationFirstStepActivity? = null
    private var nextButton: Button? = null

    //private var dropdown: Spinner? = null
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
        val format = SimpleDateFormat("hh:mm", Locale.US)
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

       /* //get the spinner from the xml.
        dropdown = findViewById(R.id.student_number)
        //create a list of items for the spinner.
        val items = arrayOf(1, 2, 3,4,5)
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        val adapter = ArrayAdapter(this, R.layout.activity_reservation_first_step, items)
        //set the spinners adapter to the previously created one.
        val localDropdown = dropdown
        if (localDropdown != null) {
            localDropdown.adapter = adapter
        }
        dropdown = localDropdown*/

        setContentView(R.layout.activity_reservation_first_step)

        userName = findViewById(R.id.user_name)
        dateReservation = findViewById(R.id.date_reservation)
        timeReservation = findViewById(R.id.time_reservation)
        studentsNumber = findViewById(R.id.students_number)
        duration = findViewById(R.id.duration)

        reservationLaterMessage = findViewById(R.id.reservation_later_message)
        sendButton = findViewById(R.id.send_reservation)
        nextButton = findViewById(R.id.next_button)
        okHttpClient = OkHttpClient()
        val localButton = sendButton

        var localUserName: EditText? = null
        var localDateReservation: EditText? = null
        var localTimeReservation: EditText? = null
        var localStudentsNumber: EditText? = null
        var localDuration: EditText? = null
        var stringServer: String? = null

        localButton?.setOnClickListener {
            localUserName = userName
            localDateReservation = dateReservation
            localTimeReservation = timeReservation
            localStudentsNumber = studentsNumber
            localDuration = duration

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
                localUserName?.text.toString(),
                localDateReservation?.text.toString(), localTimeReservation?.text.toString(),
                localDuration?.text.toString(), localStudentsNumber?.text.toString()
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
                        //Toast.makeText(applicationContext, stringServer, Toast.LENGTH_SHORT).show()
                        val localViewDateTime = reservationLaterMessage
                        var stringTemp: String? = null
                        if (stringServer == "reservation_now") {
                            stringTemp = "data received"
                        }
                        if (stringServer == "reservation_later") {
                            stringTemp = "Your reservation will be answered in a later time"
                        }
                        if (stringServer == "error") {
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
            if (stringServer == "reservation_now"){
                val i = Intent(this, ReservationLayoutLab::class.java)
                i.putExtra("date", localDateReservation?.text.toString())
                i.putExtra("time", localTimeReservation?.text.toString())
                i.putExtra("num", localStudentsNumber?.text.toString())
                i.putExtra("duration", localDuration?.text.toString())
                startActivity(i)
            }
            if (stringServer == "reservation_later" || stringServer == "error"){
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
        nextButton = localNextButton
    }
}