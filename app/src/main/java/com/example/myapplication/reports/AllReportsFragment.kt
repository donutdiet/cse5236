package com.example.myapplication.reports

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.viewmodel.ReportsViewModel
import androidx.lifecycle.ViewModelProvider


class AllReportsFragment : Fragment() {
    private val allReportsFragTag = "AllReportsFragment"
    private lateinit var viewModel: ReportsViewModel
    private lateinit var adapter: AllMyReportsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_reports, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.allReportsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = AllMyReportsAdapter(mutableListOf())
        recyclerView.adapter = adapter
        viewModel = ViewModelProvider(this)[ReportsViewModel::class.java]
        viewModel.getAllReports().observe(viewLifecycleOwner) { reports ->
            adapter.updateReports(reports.toMutableList())
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        Log.d(allReportsFragTag, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(allReportsFragTag, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(allReportsFragTag, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(allReportsFragTag, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(allReportsFragTag, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(allReportsFragTag, "onDestroy")
    }
}