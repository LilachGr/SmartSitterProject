package com.example.smartsitterclient

//import android.R
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import okhttp3.FormBody
//import sun.jvm.hotspot.utilities.IntArray
import java.io.IOException


class ReservationFirstStepActivity : AppCompatActivity() {
    private var editText: EditText? = null
    private var button: Button? = null
    private var okHttpClient: OkHttpClient? = null
    private var dropdown: Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_reservation_first_step)

        //get the spinner from the xml.
        dropdown = findViewById(R.id.student_number)
        //create a list of items for the spinner.
        val items = arrayOf("1", "2", "3","4","5","6","7","8","9","10")
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        val adapter = ArrayAdapter(this, R.layout.activity_reservation_first_step, items)
        //set the spinners adapter to the previously created one.
        val localDropdown = dropdown
        if (localDropdown != null) {
            localDropdown.adapter = adapter
        }
        dropdown = localDropdown
        setContentView(R.layout.activity_reservation_first_step)

        editText = findViewById(R.id.user_name)
        button = findViewById(R.id.send_reservation)
        okHttpClient = OkHttpClient()
        val localButton = button
        localButton?.setOnClickListener {
            val localEditText = editText
            val dummyText = localEditText?.text.toString()
            // we add the information we want to send in a form. each string we want to send should have a name.
            // in our case we sent the dummyText with a name 'sample'
            val formBody: RequestBody = FormBody.Builder().add("sample", dummyText).add("hello", dummyText).build()
            // while building request we give our form as a parameter to post()
            val request: Request = Request.Builder().url("http://10.100.102.10:5000/debug").post(formBody).build()
            okHttpClient!!.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread { Toast.makeText(applicationContext, "server down", Toast.LENGTH_SHORT).show() }
                }

                //@Throws(IOException::class)
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