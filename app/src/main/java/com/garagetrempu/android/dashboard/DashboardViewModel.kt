package com.garagetrempu.android.dashboard

import androidx.lifecycle.ViewModel
import com.garagetrempu.android.AppManager
import com.garagetrempu.android.request.LoginRequest
import com.garagetrempu.android.response.GetAppointmentsResponse
import com.garagetrempu.android.service

class DashboardViewModel : ViewModel(){

    fun getAppointments(callback: (()->Unit)) {
        service.getAppointments()
            .enqueue(com.garagetrempu.android.callback<List<GetAppointmentsResponse>>(
                {
                    if (it.code() === 200) {
                        callback()
                    }
                }, {
                    var toto = ""
                }
            ))
    }
}