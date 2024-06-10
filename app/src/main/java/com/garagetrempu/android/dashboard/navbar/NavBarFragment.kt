package com.garagetrempu.android.dashboard.navbar

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.garagetrempu.android.AppManager
import com.garagetrempu.android.R
import com.garagetrempu.android.login.MainActivity
import kotlinx.android.synthetic.main.fragment_navbar.back_to_list

class NavBarFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navbar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurer la navigation pour le bouton back_to_list
        back_to_list.setOnClickListener {
            findNavController().navigate(R.id.from_navbar_to_list)
        }
        val newAppointmentButton = view.findViewById<Button>(R.id.newAppointment)
        newAppointmentButton.setOnClickListener {
            // Naviguer vers le fragment add_appointment
            findNavController().navigate(R.id.from_navbar_to_add_appointment)
        }

        val historyAppointmentButton = view.findViewById<Button>(R.id.historyAppointment)
        historyAppointmentButton.setOnClickListener {
            // Naviguer vers le fragment history
            findNavController().navigate(R.id.from_navbar_to_history)
        }
        val logoutButton = view.findViewById<Button>(R.id.logout)
        logoutButton.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }
    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Voulez-vous vraiment vous déconnecter?")
            .setPositiveButton("Oui") { dialog, id ->
                logout()
            }
            .setNegativeButton("Non") { dialog, id ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun logout() {
        // Logique de déconnexion, par exemple, nettoyer les informations de session
        AppManager.token = null
        val intent = Intent(activity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}