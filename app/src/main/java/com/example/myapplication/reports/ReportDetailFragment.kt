package com.example.myapplication.reports

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.myapplication.viewmodel.ReportsViewModel
import com.example.myapplication.R
import com.google.firebase.firestore.GeoPoint

class ReportDetailFragment : Fragment() {

    private val viewModel: ReportsViewModel by viewModels()

    private lateinit var petNameEditText: EditText
    private lateinit var petTypeEditText: EditText
    private lateinit var latitudeEditText: EditText
    private lateinit var longitudeEditText: EditText
    private lateinit var contactEditText: EditText
    private lateinit var updateButton: Button
    private lateinit var deleteButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_report_detail , container, false)

        val args: ReportDetailFragmentArgs by navArgs()

        val reportId = args.reportId
        Log.d("ReportDetail", "Received reportId: $reportId")

        // Bind views
        petNameEditText = view.findViewById(R.id.petNameEditText)
        petTypeEditText = view.findViewById(R.id.petTypeEditText)
        latitudeEditText = view.findViewById(R.id.latitudeEditText)
        longitudeEditText = view.findViewById(R.id.longitudeEditText)
        contactEditText = view.findViewById(R.id.contactEditText)
        updateButton = view.findViewById(R.id.updateButton)
        deleteButton = view.findViewById(R.id.deleteButton)

        // Observe report
        viewModel.getReportById(reportId).observe(viewLifecycleOwner) { report ->
            report?.let {
                petNameEditText.setText(it.petName)
                petTypeEditText.setText(it.petType)
                latitudeEditText.setText(it.lastSeen?.latitude?.toString() ?: "")
                longitudeEditText.setText(it.lastSeen?.longitude?.toString() ?: "")
                contactEditText.setText(it.contact)
            }
        }

//        updateButton.setOnClickListener {
//            val lat = latitudeEditText.text.toString().toDoubleOrNull()
//            val lon = longitudeEditText.text.toString().toDoubleOrNull()
//            if (lat == null || lon == null || lat !in -90.0..90.0 || lon !in -180.0..180.0) {
//                Toast.makeText(requireContext(), "Enter valid latitude/longitude", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            val updatedReport = Report(
//                petName = petNameEditText.text.toString(),
//                petType = petTypeEditText.text.toString(),
//                lastSeen = GeoPoint(lat, lon),
//                contact = contactEditText.text.toString(),
//                userId = "" // optional: keep same userId or pass from original
//            )
//
//            viewModel.updateReport(reportId, updatedReport)
//            Toast.makeText(requireContext(), "Report updated!", Toast.LENGTH_SHORT).show()
//        }
//
//        deleteButton.setOnClickListener {
//            viewModel.deleteReport(reportId)
//            Toast.makeText(requireContext(), "Report deleted", Toast.LENGTH_SHORT).show()
//            findNavController().popBackStack()
//        }

        return view
    }
}
