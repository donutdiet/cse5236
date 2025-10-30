package com.example.myapplication.reports

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Report(
    val petName: String = "",
    val petType: String = "",
    val lastSeen: String = "",
    val contact: String = "",
    val userId: String = "",
    @ServerTimestamp val timestamp: Date? = null
)
