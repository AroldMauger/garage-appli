package com.garagetrempu.android.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.garagetrempu.android.BuildConfig
import com.garagetrempu.android.R
import com.garagetrempu.android.dashboard.DashboardActivity
import kotlinx.android.synthetic.main.activity_main.loginButton
import kotlinx.android.synthetic.main.activity_main.password
import kotlinx.android.synthetic.main.activity_main.user
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val viewModel by inject<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupListeners()
        if (BuildConfig.DEBUG) {
            user.setText("garagetrempu@gmail.com")
            password.setText("")
        }
    }

    private fun setupListeners() {
        loginButton.setOnClickListener {
            viewModel.login(user.text.toString(), password.text.toString()) {
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
            }
        }
    }
}