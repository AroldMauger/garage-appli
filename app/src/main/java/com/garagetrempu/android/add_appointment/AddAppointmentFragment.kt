package com.garagetrempu.android.add_appointment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.garagetrempu.android.R
import com.garagetrempu.android.response.NewAppointmentDTO
import kotlinx.android.synthetic.main.fragment_add_appointment.car
import kotlinx.android.synthetic.main.fragment_add_appointment.customer
import kotlinx.android.synthetic.main.fragment_add_appointment.date
import kotlinx.android.synthetic.main.fragment_add_appointment.phone
import kotlinx.android.synthetic.main.fragment_add_appointment.reason
import kotlinx.android.synthetic.main.fragment_add_appointment.returntomain_button
import kotlinx.android.synthetic.main.fragment_add_appointment.submit_appointment
import kotlinx.android.synthetic.main.fragment_add_appointment.time
import org.koin.android.viewmodel.ext.android.sharedViewModel

class AddAppointmentFragment : Fragment() {
    private val viewModel by sharedViewModel<AddAppointmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_appointment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        returntomain_button.setOnClickListener {
            findNavController().navigate(R.id.from_form_to_list)
        }
        submit_appointment.setOnClickListener {
            onAddAppointment()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onAddAppointment() {
        try {
            // Récupérer et formater la date
            val realDate = date.text.toString().split("/").reversed().joinToString("-")

            // Récupérer et formater l'heure
            val timeParts = time.text.toString().split(":")
            val hours = timeParts[0].padStart(2, '0')
            val minutes = timeParts[1].padStart(2, '0')
            val realTime = "$hours:$minutes:00"

            // Créer l'objet NewAppointmentDTO
            val newAppointment = NewAppointmentDTO(
                "$realDate $realTime",
                customer.text.toString(),
                phone.text.toString(),
                car.text.toString(),
                reason.text.toString()
            )
            Log.d("AddAppointment", "New appointment: $newAppointment")

            // Appeler l'API pour ajouter le nouveau rendez-vous
            viewModel.addNewAppointment(newAppointment, onSuccess = {
                Log.d("AddAppointment", "Appointment added successfully")
                Toast.makeText(context, "RDV ajouté", Toast.LENGTH_SHORT).show()
                // Naviguer vers une autre vue si nécessaire
                findNavController().navigate(R.id.from_form_to_list)
            }, onFailure = {
                Log.e("AddAppointment", "Failed to add appointment: ${it.message}")
                Toast.makeText(context, "Impossible d'ajouter le RDV", Toast.LENGTH_SHORT).show()
            })
        } catch (e: Exception) {
            // Gérer les exceptions
            Toast.makeText(context, "Format de date invalide", Toast.LENGTH_SHORT).show()
            Log.e("AddAppointment", "Date parsing error", e)
        }
    }

}