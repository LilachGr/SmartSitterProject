package com.example.smartsitterclient

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.*
import java.io.IOException


class RegistrationPage : AppCompatActivity() {
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
        val isValidEmail =  Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()
        if (isValidEmail) {
            val list = strEmail.split('@')
            if (list[1] == "biu.ac.il"){ //check if the ending is correct
                return true
            }
        }
        return false
    }

    private fun validPassword(strPsw: String, strRepeatPsw: String): Boolean {
        if (strPsw == strRepeatPsw) {
            return true
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_page)
        supportActionBar?.setDisplayShowHomeEnabled(true);
        supportActionBar?.setLogo(R.mipmap.my_logo_round);
        supportActionBar?.setDisplayUseLogoEnabled(true);

        userName = findViewById(R.id.user_name)
        password = findViewById(R.id.password)
        repeatPassword = findViewById(R.id.repeat_password)
        emailUniversity = findViewById(R.id.email)
        loginMessage = findViewById(R.id.login_message)
        sendButton = findViewById(R.id.send_login)
        nextButton = findViewById(R.id.next_button)
        okHttpClient = OkHttpClient()

        val localButtonClickNext2 = nextButton
        localButtonClickNext2?.isEnabled = false
        nextButton = localButtonClickNext2

        val localButton = sendButton
        var localUserName: EditText? = null
        var localPassword: EditText? = null
        var localRepeatPassword: EditText? = null
        var localEmailUniversity: EditText? = null
        var localError: String? = null
        val message = loginMessage

        localButton?.setOnClickListener {
            localButton.isEnabled = false
            message?.text = ""
            loginMessage = message

            localUserName = userName
            localPassword = password
            localRepeatPassword = repeatPassword
            localEmailUniversity = emailUniversity

            val isValidEmail: Boolean = validEmailUniversity(localEmailUniversity?.text.toString())
            if (!isValidEmail) {
                localError = "error"
                val stringTemp2 = "Your email is not a valid university email."
                message?.text = stringTemp2
                loginMessage = message
                localButton.isEnabled = true
                return@setOnClickListener
            }

            val isValidPassword: Boolean = validPassword(localPassword?.text.toString(), localRepeatPassword?.text.toString())
            if (!isValidPassword) {
                localError = "error"
                val stringTemp2 = "The password and repeat password must match."
                message?.text = stringTemp2
                loginMessage = message
                localButton.isEnabled = true
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
                    runOnUiThread {
                        val stringTemp = "server down"
                        val localLoginMessage = loginMessage
                        localLoginMessage?.text = stringTemp
                        loginMessage = localLoginMessage
                        localButton.isEnabled = true
                        Toast.makeText(applicationContext, stringTemp, Toast.LENGTH_SHORT).show()
                    }
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    runOnUiThread {
                        localError = response.body!!.string()
                        val localLoginMessage = loginMessage
                        var stringTemp: String? = null
                        if (localError == "error") {
                            localButton.isEnabled = true
                            stringTemp = "Your registration details are incorrect.\nPlease try again!"
                        } else{
                            localButton.isEnabled = false
                            val localButtonClickNext = nextButton
                            localButtonClickNext?.isEnabled = true
                            nextButton = localButtonClickNext
                            stringTemp = "Data received!!"
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