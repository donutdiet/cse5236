package com.example.myapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.reports.Report
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class ReportsRepository {
    private val db = Firebase.firestore
    private val reportsCollection = db.collection("reports")
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
                    liveData.value = snapshot.toObjects(Report::class.java)
                }
            }
        return liveData
    }

    fun addReport(report: Report) {
        reportsCollection.add(report)
    }

    fun getAllReports(): MutableLiveData<List<Report>> {
        val liveData = MutableLiveData<List<Report>>()
        reportsCollection
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, e ->
                if (e != null) { Log.e("ReportsRepository", "Error fetching all reports", e); return@addSnapshotListener }
                if (snapshot != null) liveData.value = snapshot.toObjects(Report::class.java)
            }
        return liveData
    }
}
