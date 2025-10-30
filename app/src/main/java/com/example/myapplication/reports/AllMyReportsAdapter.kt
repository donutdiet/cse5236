package com.example.myapplication.reports

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class AllMyReportsAdapter(private var reports: MutableList<Report>) :
    RecyclerView.Adapter<AllMyReportsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.reportTitle)
        val descriptionText: TextView = itemView.findViewById(R.id.reportDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_report, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val report = reports[position]
        holder.titleText.text = report.petName
        val lastSeenString = report.lastSeen?.let { "Lat: ${it.latitude}, Lon: ${it.longitude}" } ?: "Not available"
        holder.descriptionText.text = "Type: ${report.petType}, Last Seen: $lastSeenString, Contact: ${report.contact}"
    }

    override fun getItemCount(): Int = reports.size

    fun updateReports(newReports: MutableList<Report>) {
        reports = newReports
        notifyDataSetChanged()
    }
}