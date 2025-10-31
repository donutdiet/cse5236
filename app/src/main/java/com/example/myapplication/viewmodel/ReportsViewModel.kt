package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.reports.Report
import com.example.myapplication.repository.ReportsRepository
class ReportsViewModel : ViewModel() {
    private val repository = ReportsRepository()

    fun getAllReports(): LiveData<List<Report>> = repository.getAllReports()
    fun getUserReports(userId: String): LiveData<List<Report>> = repository.getUserReports(userId)
    fun getReportById(reportId: String): LiveData<Report?> = repository.getReportById(reportId)
    fun addReport(report: Report) = repository.addReport(report)

    private val _updateResult = MutableLiveData<Boolean>()
    val updateResult: LiveData<Boolean> get() = _updateResult

    private val _deleteResult = MutableLiveData<Boolean>()
    val deleteResult: LiveData<Boolean> get() = _deleteResult

    fun updateReport(report: Report) {
        repository.updateReport(report) { success ->
            _updateResult.postValue(success)
        }
    }

    fun deleteReport(reportId: String) {
        repository.deleteReport(reportId) { success ->
            _deleteResult.postValue(success)
        }
    }
}
