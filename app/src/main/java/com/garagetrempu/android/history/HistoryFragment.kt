package com.garagetrempu.android.history

import HistoryViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.garagetrempu.android.R
import com.garagetrempu.android.dashboard.DashboardViewModel
import com.garagetrempu.android.dashboard.appointmentlist.AppointmentAdapter
import com.garagetrempu.android.extension.navigateSafe
import com.garagetrempu.android.response.GetAppointmentsResponse
import kotlinx.android.synthetic.main.fragment_appointmentlist.list
import org.koin.android.viewmodel.ext.android.sharedViewModel

class HistoryFragment : Fragment(), HistoryAdapter.Listener {

    private val viewModel by sharedViewModel<HistoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fetch the appointments from history
        viewModel.getAppointmentsFromHistory {
            view.findViewById<RecyclerView>(R.id.list_history).apply {
                adapter = HistoryAdapter(viewModel.appointments, this@HistoryFragment)
                layoutManager = LinearLayoutManager(context)
            }
        }

        // Setup navigation button
        val navbarButton: TextView = view.findViewById(R.id.display_from_history)
        navbarButton.setOnClickListener {
            findNavController().navigate(R.id.from_history_to_navbar)
        }
    }

    override fun onItemSelected(appointment: GetAppointmentsResponse) {
        viewModel.selectedAppointment = appointment
        findNavController().navigateSafe(R.id.from_history_to_details)
    }
}



