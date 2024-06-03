package com.garagetrempu.android.add_appointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.garagetrempu.android.R
import kotlinx.android.synthetic.main.fragment_add_appointment.returntomain_button

class AddAppointmentFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_appointment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        returntomain_button.setOnClickListener {
            findNavController().navigate(R.id.from_form_to_list)
        }
    }
}