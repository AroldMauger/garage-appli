package com.garagetrempu.android.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.garagetrempu.android.R
import org.koin.android.ext.android.inject

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
    }


}