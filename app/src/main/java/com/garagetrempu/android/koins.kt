package com.garagetrempu.android

import com.garagetrempu.android.add_appointment.AddAppointmentViewModel
import com.garagetrempu.android.history.HistoryViewModel
import com.garagetrempu.android.dashboard.DashboardViewModel
import com.garagetrempu.android.login.LoginViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

fun allModules() = listOf<Module>(
    loginActivityModule(),
    dashboardActivityModule(),
    historyActivityModule(),
    addAppointmentActivityModule()
)

fun loginActivityModule() = module {
    viewModel { LoginViewModel() }
}

fun dashboardActivityModule() = module{
    viewModel { DashboardViewModel() }
}

fun historyActivityModule() = module{
    viewModel { HistoryViewModel() }
}

fun addAppointmentActivityModule() = module{
    viewModel { AddAppointmentViewModel() }
}