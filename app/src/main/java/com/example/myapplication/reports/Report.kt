package com.example.myapplication.reports

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Report(
    val petName: String = "",
    val petType: String = "",
    val lastSeen: GeoPoint? = null,
    val contact: String = "",
    val userId: String = "",
    val found: Boolean = false,
    @ServerTimestamp val timestamp: Date? = null,
    @get:Exclude var id: String = ""
)
