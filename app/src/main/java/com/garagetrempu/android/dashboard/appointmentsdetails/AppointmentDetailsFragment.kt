package com.garagetrempu.android.dashboard.appointmentsdetails

import android.app.AlertDialog
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
import com.garagetrempu.android.dashboard.DashboardViewModel
import com.garagetrempu.android.response.NewAppointmentDTO
import kotlinx.android.synthetic.main.fragment_appointment_details.car_modal
import kotlinx.android.synthetic.main.fragment_appointment_details.closeModal
import kotlinx.android.synthetic.main.fragment_appointment_details.customer_modal
import kotlinx.android.synthetic.main.fragment_appointment_details.date_modal
import kotlinx.android.synthetic.main.fragment_appointment_details.delete
import kotlinx.android.synthetic.main.fragment_appointment_details.finished
import kotlinx.android.synthetic.main.fragment_appointment_details.modify
import kotlinx.android.synthetic.main.fragment_appointment_details.phone_modal
import kotlinx.android.synthetic.main.fragment_appointment_details.reason_modal
import kotlinx.android.synthetic.main.fragment_appointment_details.time_modal
import org.koin.android.viewmodel.ext.android.sharedViewModel
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class AppointmentDetailsFragment : Fragment() {
    private val viewModel by sharedViewModel<DashboardViewModel>()

    // Ajoutez cette variable pour suivre l'état de modification
    private var isEditing = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_appointment_details, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appointment = viewModel.selectedAppointment ?: return

        // Parse and format the date before displaying it
        val date = OffsetDateTime.parse(appointment.date, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        val formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        val formattedTime = date.format(DateTimeFormatter.ofPattern("HH':'mm"))
        // Set the formatted date in the modals
        date_modal.setText(formattedDate)
        time_modal.setText(formattedTime)
        reason_modal.setText(appointment.reason)
        car_modal.setText(appointment.car)
        customer_modal.setText(appointment.customer)
        phone_modal.setText(appointment.phone)

        // Définissez le texte et le comportement initial du bouton Modifier/Valider
        modify.text = if (isEditing) "Valider" else "Modifier"
        modify.setOnClickListener {
            if (isEditing) {
                // Appeler la fonction de modification
                onAppointmentModified(appointment.id)
            } else {
                // Activer le mode d'édition
                enableEditMode()
            }
        }

        finished.setOnClickListener {
            onAppointmentFinished(appointment.id)
        }

        delete.setOnClickListener {
            onAppointmentDeleted(appointment.id)
        }

        closeModal.setOnClickListener {
            findNavController().navigate(R.id.action_closeModal_to_appointment_list)
        }
    }

    // CODE AJOUTÉ
    private fun onAppointmentFinished(appointmentId: Int) {
        // Utilisez une boîte de dialogue pour demander la confirmation
        val alertDialog = AlertDialog.Builder(requireContext())
            .setMessage("Êtes-vous sûr de vouloir terminer le RDV?")
            .setPositiveButton("Oui") { _, _ ->
                // Si l'utilisateur clique sur "Oui", terminez le rendez-vous
                viewModel.finishAppointment(appointmentId, onSuccess = {
                    Toast.makeText(context, "RDV terminé", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_to_dashboard)
                }, onFailure = {
                    Toast.makeText(context, "Impossible de terminer le RDV", Toast.LENGTH_SHORT).show()
                })
            }
            .setNegativeButton("Non", null)
            .create()

        alertDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onAppointmentModified(appointmentId: Int) {
        // Log pour vérifier la chaîne de caractères obtenue
        try {
            // Utilisez le bon format pour analyser la date sans le décalage de fuseau horaire
            // Si la conversion est réussie, créer un objet NewAppointmentDTO avec la date analysée
            val realDate = date_modal
                .text
                .toString()
                .split("/")
                .reversed()
                .joinToString("-")

            // Récupérer l'heure et les minutes de time_modal
            val timeParts = time_modal.text.toString().split(":")
            val hours = timeParts[0].padStart(2, '0')
            val minutes = timeParts[1].padStart(2, '0')
            val realTime = "$hours:$minutes:00"  // mettre en dur les secondes à 00

            // Créer l'objet LocalDateTime en combinant la date et l'heure
            val newAppointment = NewAppointmentDTO(
                "$realDate $realTime",
                customer_modal.text.toString(),
                phone_modal.text.toString(),
                car_modal.text.toString(),
                reason_modal.text.toString()
            )
            Log.d("AppointmentDetails", "New appointment: $newAppointment")

            // Imprimer les données envoyées
            Log.d("AppointmentDetails", "Sending data to API: $newAppointment")

            // Utilisez newAppointment comme prévu
            viewModel.modifyAppointment(appointmentId, newAppointment, onSuccess = {
                Log.d("AppointmentDetails", "Appointment modified successfully")
                Toast.makeText(context, "RDV modifié", Toast.LENGTH_SHORT).show()
                // Réinitialiser l'état de modification et mettre à jour l'UI si nécessaire
                isEditing = false
                modify.text = "Modifier"

                // Désactiver le mode d'édition
                disableEditMode()
            }, onFailure = {
                Log.e("AppointmentDetails", "Failed to modify appointment: ${it.message}")
                Toast.makeText(context, "Impossible de modifier le RDV", Toast.LENGTH_SHORT).show()
            })
        } catch (e: Exception) {
            // Gérer l'exception si la conversion de la date échoue
            // Par exemple, afficher un message d'erreur à l'utilisateur
            Toast.makeText(context, "Format de date invalide", Toast.LENGTH_SHORT).show()
            Log.e("AppointmentDetails", "Date parsing error", e)
        }
    }




    private fun enableEditMode() {
        // Activer le mode d'édition et changer le texte et le comportement du bouton
        isEditing = true
        modify.text = "Valider"

        // Activer les champs pour permettre la modification
        date_modal.isEnabled = true
        time_modal.isEnabled = true
        reason_modal.isEnabled = true
        car_modal.isEnabled = true
        customer_modal.isEnabled = true
        phone_modal.isEnabled = true
    }

    private fun disableEditMode() {
        // Désactiver les champs pour empêcher la modification
        date_modal.isEnabled = false
        time_modal.isEnabled = false
        reason_modal.isEnabled = false
        car_modal.isEnabled = false
        customer_modal.isEnabled = false
        phone_modal.isEnabled = false
    }

    private fun onAppointmentDeleted(appointmentId: Int) {
        // Utilisez une boîte de dialogue pour demander la confirmation
        val alertDialog = AlertDialog.Builder(requireContext())
            .setMessage("Êtes-vous sûr de vouloir supprimer le RDV? Les données du RDV seront perdues.")
            .setPositiveButton("Oui") { _, _ ->
                // Si l'utilisateur clique sur "Oui", supprimez le rendez-vous
                viewModel.deleteAppointment(appointmentId, onSuccess = {
                    Toast.makeText(context, "RDV supprimé", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_to_dashboard)
                }, onFailure = {
                    Toast.makeText(context, "Impossible de supprimer le RDV", Toast.LENGTH_SHORT).show()
                })
            }
            .setNegativeButton("Non", null)
            .create()

        alertDialog.show()
    }
}