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
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.viewmodel.ReportsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.GeoPoint

class ReportFragment : Fragment() {
    private val reportFragTag = "ReportFragment"
    private val myReports = mutableListOf<Report>()
    private lateinit var adapter: AllMyReportsAdapter
    private lateinit var viewModel: ReportsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_report, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.reportRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val user = FirebaseAuth.getInstance().currentUser
        val currentUserId = user?.uid ?: ""

        val userReports = myReports.filter { it.userId == currentUserId }.toMutableList()
        adapter = AllMyReportsAdapter(userReports)
        recyclerView.adapter = adapter
        viewModel = ViewModelProvider(this)[ReportsViewModel::class.java]
        viewModel.getUserReports(currentUserId).observe(viewLifecycleOwner) { reports ->
            adapter.updateReports(reports.toMutableList())
            recyclerView.scrollToPosition(adapter.itemCount - 1)
        }
        val petNameText = view.findViewById<EditText>(R.id.petNameEditText)
        val petTypeText = view.findViewById<EditText>(R.id.petTypeEditText)
        val latitudeText = view.findViewById<EditText>(R.id.latitudeEditText)
        val longitudeText = view.findViewById<EditText>(R.id.longitudeEditText)
        val contactText = view.findViewById<EditText>(R.id.contactEditText)
        val submitButton = view.findViewById<Button>(R.id.submitButton)

        submitButton.setOnClickListener {
            val lat = latitudeText.text.toString().toDoubleOrNull()
            val lon = longitudeText.text.toString().toDoubleOrNull()
            var geoPoint: GeoPoint? = null
            if (lat != null && lon != null) {
                geoPoint = GeoPoint(lat, lon)
            }

            val report = Report(
                petName = petNameText.text.toString(),
                petType = petTypeText.text.toString(),
                lastSeen = geoPoint,
                contact = contactText.text.toString(),
                userId = currentUserId
            )
            viewModel.addReport(report)
            petNameText.text.clear()
            petTypeText.text.clear()
            latitudeText.text.clear()
            longitudeText.text.clear()
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
