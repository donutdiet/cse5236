package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DummyReportsFragment : Fragment() {
    private val dummyReportsFragTag = "DummyReportsFragment"

    private val dummyReports = listOf(
        DummyReport("Lost Dog", "Brown Labrador lost near Central Park."),
        DummyReport("Missing Cat", "White Persian last seen on 5th Avenue."),
        DummyReport("Parrot Missing", "Green parrot flew away from Elm Street."),
        DummyReport("Hamster", "Small hamster missing from Oak Street.")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dummy_reports, container, false)
        Log.d(dummyReportsFragTag, "onCreateView")
        val recyclerView = view.findViewById<RecyclerView>(R.id.dummyRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = DummyReportAdapter(dummyReports)
        return view
    }

    override fun onStart() {
        super.onStart()
        Log.d(dummyReportsFragTag, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(dummyReportsFragTag, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(dummyReportsFragTag, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(dummyReportsFragTag, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(dummyReportsFragTag, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(dummyReportsFragTag, "onDestroy")
    }
}