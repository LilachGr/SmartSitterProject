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
//import sun.jvm.hotspot.utilities.IntArray
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class LoginPage : AppCompatActivity() {
    private var userName: EditText? = null
    private var password: EditText? = null
    private var repeatPassword: EditText? = null
    private var emailUniversity: EditText? = null
    private var sendButton: Button? = null
    private var okHttpClient: OkHttpClient? = null
    private var loginMessage: TextView? = null
    private var nextButton: Button? = null

    private val mapper = jacksonObjectMapper()

    private fun validEmailUniversity(strEmail: String): Boolean {
        return true
    }

    private fun validPassword(strPsw: String,strRepeatPsw: String): Boolean {
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        userName = findViewById(R.id.user_name)
        password = findViewById(R.id.password)
        repeatPassword = findViewById(R.id.repeat_password)
        emailUniversity = findViewById(R.id.email)
        loginMessage = findViewById(R.id.login_message)
        sendButton = findViewById(R.id.send_login)
        nextButton = findViewById(R.id.next_button)
        okHttpClient = OkHttpClient()

        val localButton = sendButton
        var localUserName: EditText? = null
        var localPassword: EditText? = null
        var localRepeatPassword: EditText? = null
        var localEmailUniversity: EditText? = null
        var localError: String? = null
        val message = loginMessage

        localButton?.setOnClickListener {
            localUserName = userName
            localPassword = password
            localRepeatPassword = repeatPassword
            localEmailUniversity = emailUniversity

            val isValidEmail: Boolean = validEmailUniversity(localEmailUniversity?.text.toString())
            if (!isValidEmail) {
                localError = "error"
                val stringTemp2 = "Your email is not accurate university email. Please TRY AGAIN!!"
                message?.text = stringTemp2
                loginMessage = message
                return@setOnClickListener
            }

            val isValidPassword: Boolean = validPassword(localPassword?.text.toString(), localRepeatPassword?.text.toString())
            if (!isValidPassword) {
                localError = "error"
                val stringTemp2 = "Your password is not valid password. Please TRY AGAIN!!"
                message?.text = stringTemp2
                loginMessage = message
                return@setOnClickListener
            }

            val loginDetails = LoginDetails(
                localUserName?.text.toString(),
                localPassword?.text.toString(), localEmailUniversity?.text.toString()
            )
            val loginDetailsJson = mapper.writeValueAsString(loginDetails)

            val text = loginDetailsJson.toString()
            // we add the information we want to send in a form. each string we want to send should have a name. in our case we sent the dummyText with a name 'sample'
            val formBody: RequestBody = FormBody.Builder().add("loginDetails", text).build()
            // while building request we give our form as a parameter to post()
            val s = SimpleDataClasses()
            val url = s.serverURL + s.serverLoginForm
            val request: Request = Request.Builder().url(url).post(formBody).build()
            okHttpClient!!.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread { Toast.makeText(applicationContext, "server down", Toast.LENGTH_SHORT).show() }
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    runOnUiThread {
                        localError = response.body!!.string()
                        val localLoginMessage = loginMessage
                        var stringTemp: String? = null
                        if (localError == "error") {
                            stringTemp = "Your login details are not correct. Please TRY AGAIN!!"
                        }
                        localLoginMessage?.text = stringTemp
                        loginMessage = localLoginMessage
                    }
                }
            })
        }
        sendButton = localButton

        val localNextButton = nextButton
        localNextButton?.setOnClickListener {
            if (localError != "error") {
                startActivity(Intent(this, SignIn::class.java))
            }
        }
        nextButton = localNextButton
    }
}