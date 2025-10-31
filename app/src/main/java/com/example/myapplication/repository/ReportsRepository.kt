package com.example.myapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.reports.Report
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class ReportsRepository {
    private val db = Firebase.firestore
    private val reportsCollection = db.collection("reports")

    fun getAllReports(): MutableLiveData<List<Report>> {
        val liveData = MutableLiveData<List<Report>>()
        reportsCollection
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("ReportsRepository", "Error fetching all reports", e)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val reports = snapshot.documents.mapNotNull { doc ->
                        val report = doc.toObject(Report::class.java)
                        report?.apply { id = doc.id }
                    }
                    liveData.value = reports
                }
            }
        return liveData
    }

    fun getUserReports(userId: String): MutableLiveData<List<Report>> {
        val liveData = MutableLiveData<List<Report>>()
        reportsCollection
            .whereEqualTo("userId", userId)
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("ReportsRepository", "Error fetching reports", e)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val reports = snapshot.documents.mapNotNull { doc ->
                        val report = doc.toObject(Report::class.java)
                        report?.apply { id = doc.id }
                    }
                    liveData.value = reports
                }
            }
        return liveData
    }

    fun getReportById(reportId: String): MutableLiveData<Report?> {
        val liveData = MutableLiveData<Report?>()
        reportsCollection
            .document(reportId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("ReportsRepository", "Error fetching report $reportId", e)
                    liveData.value = null
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val report = snapshot.toObject(Report::class.java)
                    report?.id = snapshot.id
                    liveData.value = report
                } else {
                    liveData.value = null
                }
            }
        return liveData
    }

    fun addReport(report: Report) {
        reportsCollection.add(report)
    }
}
