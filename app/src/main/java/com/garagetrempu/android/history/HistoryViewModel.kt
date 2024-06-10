package com.garagetrempu.android.history

import android.util.Log
import androidx.lifecycle.ViewModel
import com.garagetrempu.android.AppManager
import com.garagetrempu.android.response.GetAppointmentsResponse
import com.garagetrempu.android.response.GetAppointmentsResponseWrapper
import com.garagetrempu.android.response.NewAppointmentDTO
import com.garagetrempu.android.service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel : ViewModel() {
    val appointments = mutableListOf<GetAppointmentsResponse>()
    var selectedAppointment: GetAppointmentsResponse? = null

    fun getAppointmentsFromHistory(callback: () -> Unit) {
        appointments.clear()
        service.history().enqueue(object : Callback<GetAppointmentsResponseWrapper> {
            override fun onResponse(
                call: Call<GetAppointmentsResponseWrapper>,
                response: Response<GetAppointmentsResponseWrapper>
            ) {
                if (response.isSuccessful) {
                    response.body()?.appointments?.let {
                        Log.d("com.garagetrempu.android.history.HistoryViewModel", "Appointments received: ${it.size}")
                        appointments.addAll(it)
                    }
                    callback() // Appel de la fonction de rappel
                } else {
                    Log.e("com.garagetrempu.android.history.HistoryViewModel", "Failed to load appointments: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GetAppointmentsResponseWrapper>, t: Throwable) {
                Log.e("com.garagetrempu.android.history.HistoryViewModel", "Failed to load appointments", t)
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


