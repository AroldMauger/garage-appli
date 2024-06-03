package com.garagetrempu.android.dashboard.navbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.garagetrempu.android.R
import kotlinx.android.synthetic.main.fragment_navbar.back_to_list

class NavBar : Fragment() {
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
    }
}