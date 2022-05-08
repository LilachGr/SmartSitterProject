package com.example.smartsitterclient


class SimpleDataClasses(){
    val serverURL = "http://10.100.102.10:5000/"
    val serverReservationFirstStep = "reservation"
    val serverLoginForm = "login"
}
data class ReservationBasicDetails(var userNameStudent: String, var dateReservation: String, var timeReservation: String,
                                   var duration: String, var NumberOfStudent: String)

data class LoginDetails(var userNameStudent: String, var passwordStudent: String, var emailUniversity: String)
