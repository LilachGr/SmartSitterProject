package com.example.smartsitterclient


class SimpleDataClasses(){
    val serverURL = "http://10.100.102.57:5000/"
    val serverReservationFirstStep = "reservation"
    val serverLoginForm = "login"
    val serverSignInForm = "signIn"
    val serverAllReservation = "allReservation"
}

data class ReservationBasicDetails(var userNameStudent: String, var dateReservation: String, var timeReservation: String,
                                   var duration: String, var NumberOfStudent: String)

data class ReservationAllDetails(var userNameStudent: String, var dateReservation: String, var timeReservation: String,
                                   var duration: String, var NumberOfStudent: String, var chairId: String)

data class LoginDetails(var userNameStudent: String, var passwordStudent: String, var emailUniversity: String)

data class SignInDetails(var userNameStudent: String, var passwordStudent: String)

