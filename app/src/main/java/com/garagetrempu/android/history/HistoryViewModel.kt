import android.util.Log
import androidx.lifecycle.ViewModel
import com.garagetrempu.android.response.GetAppointmentsResponse
import com.garagetrempu.android.response.GetAppointmentsResponseWrapper
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
                        Log.d("HistoryViewModel", "Appointments received: ${it.size}")
                        appointments.addAll(it)
                    }
                    callback() // Appel de la fonction de rappel
                } else {
                    Log.e("HistoryViewModel", "Failed to load appointments: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GetAppointmentsResponseWrapper>, t: Throwable) {
                Log.e("HistoryViewModel", "Failed to load appointments", t)
            }
        })
    }
}


