package com.example.myapplication.reports

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.viewmodel.ReportsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.GeoPoint
import kotlin.getValue

class ReportFragment : Fragment() {
    private val reportFragTag = "ReportFragment"
    private val myReports = mutableListOf<Report>()
    private var selectedReportId: String? = null
    private lateinit var adapter: AllMyReportsAdapter
    private val viewModel: ReportsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_report, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.reportRecyclerView)
        val petNameText = view.findViewById<EditText>(R.id.petNameEditText)
        val petTypeText = view.findViewById<EditText>(R.id.petTypeEditText)
        val latitudeText = view.findViewById<EditText>(R.id.latitudeEditText)
        val longitudeText = view.findViewById<EditText>(R.id.longitudeEditText)
        val contactText = view.findViewById<EditText>(R.id.contactEditText)
        val submitButton = view.findViewById<Button>(R.id.submitButton)

        val user = FirebaseAuth.getInstance().currentUser
        val currentUserId = user?.uid ?: ""

        recyclerView.layoutManager = LinearLayoutManager(activity)

        val userReports = myReports.filter { it.userId == currentUserId }.toMutableList()
        adapter = AllMyReportsAdapter(userReports)
        recyclerView.adapter = adapter

        adapter.onItemClickListener = { report ->
            val action = ReportFragmentDirections
                .actionReportFragmentToReportDetailFragment(report.id)
            findNavController().navigate(action)
        }

        viewModel.getUserReports(currentUserId).observe(viewLifecycleOwner) { reports ->
            adapter.updateReports(reports.toMutableList())
            recyclerView.scrollToPosition(adapter.itemCount - 1)
        }

        submitButton.setOnClickListener {
            val lat = latitudeText.text.toString().toDoubleOrNull()
            val lon = longitudeText.text.toString().toDoubleOrNull()

            if (lat != null && lon != null && lat in -90.0..90.0 && lon in -180.0..180.0) {
                val geoPoint = GeoPoint(lat, lon)
                val report = Report(
                    petName = petNameText.text.toString(),
                    petType = petTypeText.text.toString(),
                    lastSeen = geoPoint,
                    contact = contactText.text.toString(),
                    userId = currentUserId
                )
                viewModel.addReport(report)

                Toast.makeText(requireContext(), "Report submitted successfully for ${petNameText.text}!", Toast.LENGTH_SHORT).show()

                petNameText.text.clear()
                petTypeText.text.clear()
                latitudeText.text.clear()
                longitudeText.text.clear()
                contactText.text.clear()
            } else {
                Toast.makeText(requireContext(), "Please enter valid latitude (-90 to 90) and longitude (-180 to 180).", Toast.LENGTH_SHORT).show()
            }
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
