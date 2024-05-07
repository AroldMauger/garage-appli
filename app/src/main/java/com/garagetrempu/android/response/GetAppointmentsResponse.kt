package com.garagetrempu.android.response

data class GetAppointmentsResponse(
    val id:Int,
    val date:String,
    val customer: String,
    val phone : String,
    val car: String,
    val reason : String
)

