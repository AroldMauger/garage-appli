package com.garagetrempu.android.dashboard.appointmentlist

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.garagetrempu.android.R
import com.garagetrempu.android.response.GetAppointmentsResponse
import kotlinx.android.synthetic.main.item_appointment.view.appointmentCar
import kotlinx.android.synthetic.main.item_appointment.view.appointmentDate
import kotlinx.android.synthetic.main.item_appointment.view.appointmentReason
import kotlinx.android.synthetic.main.item_appointment.view.appointmentTime
import kotlinx.android.synthetic.main.item_appointment.view.seeMore
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AppointmentAdapter (val items:List<GetAppointmentsResponse>): RecyclerView.Adapter<AppointmentAdapter.ViewHolder> () {

    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(appointment: GetAppointmentsResponse){
            val date = LocalDateTime.parse(appointment.date, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"))
            itemView.appointmentDate.text = "${date.year}/${date.month.value}/${date.dayOfMonth}"
            itemView.appointmentTime.text = "${date.hour}h${date.minute}"
            itemView.appointmentCar.text = appointment.car
            itemView.appointmentReason.text = appointment.reason
            itemView.seeMore.setOnClickListener{
                var toto = ""
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_appointment, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}