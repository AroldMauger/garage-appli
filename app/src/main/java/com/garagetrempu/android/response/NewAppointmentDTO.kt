package com.garagetrempu.android.response


data class NewAppointmentDTO(
    val date: String,
    val customer: String,
    val phone: String,
    val car: String,
    val reason: String,
)