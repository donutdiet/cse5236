package com.example.myapplication

import DummyReportAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView

class ReportFragment : Fragment() {

    private val TAG = "ReportFragment"
    private val reports = mutableListOf<DummyReport>()
    private lateinit var adapter: DummyReportAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_report, container, false)

        // Setup RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.reportRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = DummyReportAdapter(reports)
        recyclerView.adapter = adapter

        // Setup form
        val petNameEditText = view.findViewById<EditText>(R.id.petNameEditText)
        val petTypeEditText = view.findViewById<EditText>(R.id.petTypeEditText)
        val lastSeenEditText = view.findViewById<EditText>(R.id.lastSeenEditText)
        val contactEditText = view.findViewById<EditText>(R.id.contactEditText)
        val submitButton = view.findViewById<Button>(R.id.submitButton)

        submitButton.setOnClickListener {
            val title = petNameEditText.text.toString().ifEmpty { "Unnamed Pet" }
            val description = "Type: ${petTypeEditText.text}, Last Seen: ${lastSeenEditText.text}, Contact: ${contactEditText.text}"

            // Add new report to list
            reports.add(DummyReport(title, description))
            adapter.notifyItemInserted(reports.size - 1)

            // Clear form
            petNameEditText.text.clear()
            petTypeEditText.text.clear()
            lastSeenEditText.text.clear()
            contactEditText.text.clear()

            // Scroll to the newly added item
            recyclerView.scrollToPosition(reports.size - 1)
        }

        return view
    }
}
