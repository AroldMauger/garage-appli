package com.garagetrempu.android.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import com.garagetrempu.android.AppManager
import com.garagetrempu.android.request.LoginRequest
import com.garagetrempu.android.response.GetAppointmentsResponse
import com.garagetrempu.android.response.NewAppointmentDTO
import com.garagetrempu.android.service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel : ViewModel(){
    val appointments = mutableListOf<GetAppointmentsResponse>()
    var selectedAppointment : GetAppointmentsResponse? = null
    fun getAppointments(callback: (()->Unit)) {
        appointments.clear()
        service.getAppointments()
            .enqueue(com.garagetrempu.android.callback<List<GetAppointmentsResponse>>(
                {
                    if (it.code() === 200) {
                        appointments.addAll(it.body()!!)
                        callback()
                    }
                }, {
                    Log.e("DashboardViewModel", "Failed to load appointments: ${it.message}")
                }
            ))
    }

    // CODE AJOUTÉ
    fun finishAppointment(appointmentId: Int, onSuccess: () -> Unit, onFailure: (Throwable) -> Unit) {
        val token = AppManager.token ?: throw IllegalStateException("Token is null")
        service.finishAppointment(token, appointmentId)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        onSuccess()
                    } else {
                        onFailure(Exception("Failed to finish appointment"))
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    onFailure(t)
                }
            })
    }

    fun modifyAppointment(appointmentId: Int, newAppointment: NewAppointmentDTO, onSuccess: () -> Unit, onFailure: (Throwable) -> Unit) {
        val token = AppManager.token ?: throw IllegalStateException("Token is null")
        service.modifyAppointment(token, appointmentId, newAppointment)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        onSuccess()
                    } else {
                        onFailure(Exception("Failed to modify appointment"))
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    onFailure(t)
                }
            })
    }


    fun deleteAppointment(appointmentId: Int, onSuccess: () -> Unit, onFailure: (Throwable) -> Unit) {
        val token = AppManager.token ?: throw IllegalStateException("Token is null")
        service.deleteAppointment(token, appointmentId)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        onSuccess()
                    } else {
                        onFailure(Exception("Failed to delete appointment"))
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    onFailure(t)
                }
            })
    }
}