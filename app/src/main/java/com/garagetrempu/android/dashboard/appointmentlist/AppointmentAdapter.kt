package com.garagetrempu.android.dashboard.appointmentlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.garagetrempu.android.R
import com.garagetrempu.android.response.GetAppointmentsResponse

class AppointmentAdapter (val items:List<GetAppointmentsResponse>): RecyclerView.Adapter<AppointmentAdapter.ViewHolder> () {

    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_appointment, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // récupérer l'item avec sa position et bind le tout
    }
}