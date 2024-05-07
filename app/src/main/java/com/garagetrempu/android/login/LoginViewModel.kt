package com.garagetrempu.android.login

import androidx.lifecycle.ViewModel
import com.garagetrempu.android.AppManager
import com.garagetrempu.android.callback
import com.garagetrempu.android.request.LoginRequest
import com.garagetrempu.android.response.LoginResponse
import com.garagetrempu.android.service
import retrofit2.Callback

class LoginViewModel: ViewModel() {
    fun login(email: String, password: String, callback: (()->Unit)) {
        service.login(
            LoginRequest(email, password)
        )
            .enqueue(callback <LoginResponse>(
                {
                if (it.code() === 200) {
                    AppManager.token = it.body()!!.token
                    callback()
                }
                }, {
                    var toto = ""
                }
            ))
    }
}