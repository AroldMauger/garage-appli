package com.garagetrempu.android.add_appointment

import androidx.lifecycle.ViewModel
import com.garagetrempu.android.AppManager
import com.garagetrempu.android.response.NewAppointmentDTO
import com.garagetrempu.android.service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddAppointmentViewModel : ViewModel() {
    fun addNewAppointment(newAppointment: NewAppointmentDTO, onSuccess: () -> Unit, onFailure: (Throwable) -> Unit) {
        val token = AppManager.token
        service.newAppointment("Bearer $token", newAppointment).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(Throwable(response.message()))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                onFailure(t)
            }
        })
    }

}