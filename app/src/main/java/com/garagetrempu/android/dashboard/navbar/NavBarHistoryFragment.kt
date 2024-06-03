package com.garagetrempu.android.dashboard.navbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.garagetrempu.android.R
import kotlinx.android.synthetic.main.fragment_navbar_history.back_to_history

class NavBarHistoryFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navbar_history, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        back_to_history.setOnClickListener {
            findNavController().navigate(R.id.from_navbarhistory_to_history)
        }
        val newAppointmentButton = view.findViewById<Button>(R.id.newAppointment)
        newAppointmentButton.setOnClickListener {
            // Naviguer vers le fragment add_appointment
            findNavController().navigate(R.id.from_navbarhistory_to_add_appointment)
        }

        val dashboardAppointmentButton = view.findViewById<Button>(R.id.dashboard)
        dashboardAppointmentButton.setOnClickListener {
            // Naviguer vers le fragment history
            findNavController().navigate(R.id.from_navbarhistory_to_list)
        }

    }
}