package com.garagetrempu.android.dashboard

import androidx.lifecycle.ViewModel
import com.garagetrempu.android.AppManager
import com.garagetrempu.android.request.LoginRequest
import com.garagetrempu.android.response.GetAppointmentsResponse
import com.garagetrempu.android.service

class DashboardViewModel : ViewModel(){
    val appointments = mutableListOf<GetAppointmentsResponse>()
    fun getAppointments(callback: (()->Unit)) {
        service.getAppointments()
            .enqueue(com.garagetrempu.android.callback<List<GetAppointmentsResponse>>(
                {
                    if (it.code() === 200) {
                        appointments.addAll(it.body()!!)
                        callback()
                    }
                }, {
                    var toto = ""
                }
            ))
    }
}