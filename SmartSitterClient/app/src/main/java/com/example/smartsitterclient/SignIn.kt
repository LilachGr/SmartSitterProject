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


class SignIn : AppCompatActivity() {
    private var userName: EditText? = null
    private var password: EditText? = null
    private var sendButton: Button? = null
    private var loginButton: Button? = null
    private var message: TextView? = null
    private var nextButton: Button? = null
    private var okHttpClient: OkHttpClient? = null

    private val mapper = jacksonObjectMapper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        supportActionBar?.setDisplayShowHomeEnabled(true);
        supportActionBar?.setLogo(R.mipmap.my_logo_round);
        supportActionBar?.setDisplayUseLogoEnabled(true);

        userName = findViewById(R.id.user_name)
        password = findViewById(R.id.password)
        sendButton = findViewById(R.id.send_sign_in)
        nextButton = findViewById(R.id.next_button)
        loginButton = findViewById(R.id.login_button)
        message = findViewById(R.id.sign_in_message)
        okHttpClient = OkHttpClient()


        val localButtonClickNext = nextButton
        localButtonClickNext?.isEnabled = false
        nextButton = localButtonClickNext

        val localSendButton = sendButton
        var localUserName: EditText? = null
        var localPassword: EditText? = null
        val localMessage = message
        var localErrorOrId: String? = null

        localSendButton?.setOnClickListener {
            localUserName = userName
            localPassword = password

            localMessage?.text = ""
            message = localMessage
            localSendButton.isEnabled = false

            val signInDetails = SignInDetails(
                localUserName?.text.toString(),
                localPassword?.text.toString()
            )
            val signInDetailsJson = mapper.writeValueAsString(signInDetails)

            val text = signInDetailsJson.toString()
            // we add the information we want to send in a form. each string we want to send should have a name. in our case we sent the dummyText with a name 'sample'
            val formBody: RequestBody = FormBody.Builder().add("signInDetails", text).build()
            // while building request we give our form as a parameter to post()
            val s = SimpleDataClasses()
            val url = s.serverURL + s.serverSignInForm
            val request: Request = Request.Builder().url(url).post(formBody).build()
            okHttpClient!!.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        val stringTemp = "server down"
                        Toast.makeText(applicationContext, stringTemp, Toast.LENGTH_SHORT).show()
                        localMessage?.text = stringTemp
                        message = localMessage
                        localSendButton.isEnabled = true
                    }
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    runOnUiThread {
                        localErrorOrId = response.body!!.string()
                        var stringTemp: String? = null
                        if (localErrorOrId == "error") {
                            stringTemp = "Your sign in details are incorrect.\nPlease try again."
                            localSendButton.isEnabled = true
                        } else {
                            localSendButton.isEnabled = false
                            val localButtonClickNext2 = nextButton
                            localButtonClickNext2?.isEnabled = true
                            nextButton = localButtonClickNext2
                            stringTemp = "Welcome " + localUserName?.text.toString()
                        }
                        localMessage?.text = stringTemp
                        message = localMessage
                    }
                }
            })
        }
        sendButton = localSendButton

        val localNextButton = nextButton
        localNextButton?.setOnClickListener {
            if (localErrorOrId != "error") {
                val i = Intent(this, ReservationFirstStepActivity::class.java)
                i.putExtra("idUserName", localErrorOrId)
                startActivity(i)
            }
        }
        nextButton = localNextButton

        val localLoginButton = loginButton
        localLoginButton?.setOnClickListener {
            startActivity(Intent(this, RegistrationPage::class.java))
        }
        loginButton = localLoginButton
    }
}