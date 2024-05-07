package com.garagetrempu.android

import android.util.Log
import com.garagetrempu.android.request.LoginRequest
import com.garagetrempu.android.response.GetAppointmentsResponse
import com.garagetrempu.android.response.LoginResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

object ApiConfiguration {
    const val TIMEOUT: Long = 60
}

val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.HEADERS
}

val client = OkHttpClient.Builder()
    .connectTimeout(ApiConfiguration.TIMEOUT, TimeUnit.SECONDS)
    .addInterceptor(interceptor)
    .retryOnConnectionFailure(true)
    .build()

val service:Api = Retrofit.Builder()
    .baseUrl(BuildConfig.API_URL)
    .client(client)
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(Api::class.java)

interface Api {
    @POST("api/auth/login")
    fun login (
        @Body params: LoginRequest
    ) :Call<LoginResponse>

    @GET("api/appointments")
    fun getAppointments (
        @Header ("Authorization") authorization: String = "${AppManager.token}"
    ) :Call<List <GetAppointmentsResponse>>
}

fun <T> callback(success: ((Response<T>) -> Unit)?, failure: ((t: Throwable) -> Unit)? = null): Callback<T> {
    return object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            success?.invoke(response)
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            print(t.message)
            Log.e("Api", "onFailure : $t")
            failure?.invoke(t)
        }
    }
}