package com.garagetrempu.android.login

import androidx.lifecycle.ViewModel
import com.garagetrempu.android.callback
import com.garagetrempu.android.request.LoginRequest
import com.garagetrempu.android.response.LoginResponse
import com.garagetrempu.android.service
import retrofit2.Callback

class LoginViewModel: ViewModel() {
    fun login(email: String, password: String) {
        service.login(
            LoginRequest(email, password)
        )
            .enqueue(callback <LoginResponse>(
                {
                var toto = "" // gérer si code 200
                }, {
                    var toto = ""
                }
            ))
    }
}