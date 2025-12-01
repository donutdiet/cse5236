package com.example.myapplication.reports

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.viewmodel.SettingsViewModel

class AllMyReportsAdapter(
    private var reports: MutableList<Report>,
    private val settingsViewModel: SettingsViewModel,
    private val lifecycleOwner: LifecycleOwner
) :
    RecyclerView.Adapter<AllMyReportsAdapter.ViewHolder>() {

    var onItemClickListener: ((Report) -> Unit)? = null
    private val originalTextSizes = mutableMapOf<Int, Float>()

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

        val dateString = report.timestamp?.let {
            android.text.format.DateFormat.format("MMM dd, yyyy h:mm a", it).toString()
        } ?: "Unknown time"

        val lastSeenString = report.lastSeen?.let {
            "(${String.format("%.4f", it.latitude)}, ${String.format("%.4f", it.longitude)})"
        } ?: "Not available"

        holder.titleText.text = "Missing ${report.petType.ifBlank { "Pet" }}: ${report.petName.ifBlank { "Unnamed" }}"

        holder.descriptionText.text = """
            Last Seen: $lastSeenString
            Contact: ${report.contact.ifBlank { "No contact info" }}
            Reported: $dateString
        """.trimIndent()

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(report)
        }
        val itemTextViews = listOf(
            holder.titleText,
            holder.descriptionText
        )
        itemTextViews.forEach { textView ->
            if (!originalTextSizes.containsKey(textView.id)) {
                originalTextSizes[textView.id] = textView.textSize
            }
        }
        settingsViewModel.isTextSizeIncreased.observe(lifecycleOwner) { isIncreased ->

            val scaleMultiplier: Float
            if (isIncreased) {
                scaleMultiplier = 1.2f
            } else {
                scaleMultiplier = 1.0f
            }

            itemTextViews.forEach { textView ->
                originalTextSizes[textView.id]?.let { originalSize ->
                    textView.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        originalSize * scaleMultiplier
                    )
                }
            }
        }
    }

    override fun getItemCount(): Int = reports.size

    fun updateReports(newReports: MutableList<Report>) {
        reports = newReports
        notifyDataSetChanged()
    }
}
