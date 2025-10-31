package com.example.myapplication.reports

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs

class ReportDetailFragment : Fragment() {

    private val args: ReportDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val reportId = args.reportId
        Log.d("ReportDetail", "Navigated to details for report ID: $reportId")
    }
}
