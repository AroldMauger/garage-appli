package com.garagetrempu.android.dashboard.appointmentlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.garagetrempu.android.R
import com.garagetrempu.android.dashboard.DashboardViewModel
import com.garagetrempu.android.extension.navigateSafe
import com.garagetrempu.android.response.GetAppointmentsResponse
import kotlinx.android.synthetic.main.fragment_appointmentlist.list
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel

class AppointmentListFragment : Fragment(), AppointmentAdapter.Listener{

    val viewModel by sharedViewModel<DashboardViewModel> ()
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
            list.adapter = AppointmentAdapter(viewModel.appointments, this)
            list.layoutManager = LinearLayoutManager(context)
        }
        val displayTextView: TextView = view.findViewById(R.id.display)
        displayTextView.setOnClickListener {
            findNavController().navigate(R.id.from_list_to_navbar)
        }
    }

    override fun onItemSelected(appointment: GetAppointmentsResponse) {
        viewModel.selectedAppointment = appointment
        findNavController().navigateSafe(R.id.from_list_to_details)
    }


}