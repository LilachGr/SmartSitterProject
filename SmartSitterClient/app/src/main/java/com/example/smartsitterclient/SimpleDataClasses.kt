package com.example.smartsitterclient

const val serverIP = "http://10.100.102.10:5000/"
const val serverReservationFirstStep = "reservation"

data class ReservationBasicDetails(var userNameStudent: String, var dateReservation: String, var timeReservation: String,
                                   var NumberOfStudent: String)
