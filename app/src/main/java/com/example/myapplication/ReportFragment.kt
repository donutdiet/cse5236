package com.example.myapplication

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
    private val reportFragTag = "ReportFragment"
    private val reports = mutableListOf<DummyReport>()
    private lateinit var adapter: DummyReportAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_report, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.reportRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = DummyReportAdapter(reports)
        recyclerView.adapter = adapter

        val petNameText = view.findViewById<EditText>(R.id.petNameEditText)
        val petTypeText = view.findViewById<EditText>(R.id.petTypeEditText)
        val lastSeenText = view.findViewById<EditText>(R.id.lastSeenEditText)
        val contactText = view.findViewById<EditText>(R.id.contactEditText)
        val submitButton = view.findViewById<Button>(R.id.submitButton)

        submitButton.setOnClickListener {
            val title = petNameText.text.toString()
            val description = "Type: ${petTypeText.text}, Last Seen: ${lastSeenText.text}, Contact: ${contactText.text}"
            reports.add(DummyReport(title, description))
            adapter.notifyItemInserted(reports.size - 1)
            recyclerView.scrollToPosition(reports.size - 1)
            petNameText.text.clear()
            petTypeText.text.clear()
            lastSeenText.text.clear()
            contactText.text.clear()
        }
        return view
    }
    override fun onStart() {
        super.onStart()
        Log.d(reportFragTag, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(reportFragTag, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(reportFragTag, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(reportFragTag, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(reportFragTag, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(reportFragTag, "onDestroy")
    }
}
