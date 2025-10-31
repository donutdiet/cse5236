package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.reports.Report
import com.example.myapplication.repository.ReportsRepository
class ReportsViewModel : ViewModel() {
    private val repository = ReportsRepository()

    fun getAllReports(): LiveData<List<Report>> {
        return repository.getAllReports()
    }

    fun getUserReports(userId: String): LiveData<List<Report>> {
        return repository.getUserReports(userId)
    }

    fun getReportById(reportId: String): LiveData<Report?> {
        return repository.getReportById(reportId)
    }

    fun addReport(report: Report) {
        repository.addReport(report)
    }
}
