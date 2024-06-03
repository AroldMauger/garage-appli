package com.garagetrempu.android.history

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.garagetrempu.android.R
import com.garagetrempu.android.response.GetAppointmentsResponse
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HistoryAdapter(
    private val items: List<GetAppointmentsResponse>,
    private val listener: Listener
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(appointment: GetAppointmentsResponse, listener: Listener) {
            val date = LocalDateTime.parse(appointment.date, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"))
            val formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            itemView.findViewById<TextView>(R.id.appointmentDate).text = formattedDate
            itemView.findViewById<TextView>(R.id.appointmentCar).text = appointment.car
            itemView.findViewById<View>(R.id.seeMore).setOnClickListener {
                listener.onItemSelected(appointment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    interface Listener {
        fun onItemSelected(appointment: GetAppointmentsResponse)
    }
}
