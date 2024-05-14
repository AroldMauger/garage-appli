package com.garagetrempu.android.dashboard.appointmentlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.garagetrempu.android.R
import com.garagetrempu.android.dashboard.DashboardViewModel
import kotlinx.android.synthetic.main.fragment_appointmentlist.list
import org.koin.android.ext.android.inject

class AppointmentListFragment : Fragment() {

    val viewModel by inject<DashboardViewModel> ()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_appointmentlist, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAppointments {
            list.adapter = AppointmentAdapter(viewModel.appointments)
            list.layoutManager = LinearLayoutManager(context)
        }

    }
}