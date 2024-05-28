package com.garagetrempu.android.dashboard.appointmentsdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.garagetrempu.android.R
import com.garagetrempu.android.dashboard.DashboardViewModel
import kotlinx.android.synthetic.main.fragment_appointment_details.car_modal
import kotlinx.android.synthetic.main.fragment_appointment_details.customer_modal
import kotlinx.android.synthetic.main.fragment_appointment_details.date_modal
import kotlinx.android.synthetic.main.fragment_appointment_details.phone_modal
import kotlinx.android.synthetic.main.fragment_appointment_details.reason_modal
import kotlinx.android.synthetic.main.fragment_appointment_details.time_modal
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel

class AppointmentDetailsFragment :Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_appointment_details, null, false)
    }
    val viewModel by sharedViewModel<DashboardViewModel> ()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        date_modal.setText(viewModel.selectedAppointment!!.date)
        time_modal.setText(viewModel.selectedAppointment!!.date)
        reason_modal.setText(viewModel.selectedAppointment!!.reason)
        car_modal.setText(viewModel.selectedAppointment!!.car)
        customer_modal.setText(viewModel.selectedAppointment!!.customer)
        phone_modal.setText(viewModel.selectedAppointment!!.phone)

    }
}