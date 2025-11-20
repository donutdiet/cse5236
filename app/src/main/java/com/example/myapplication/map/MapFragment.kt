package com.example.myapplication.map

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.reports.Report
import com.example.myapplication.viewmodel.ReportsViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.getValue

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private var googleMap: GoogleMap? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val viewModel: ReportsViewModel by viewModels()


    private val mapFragTag = "MapFragment"

    companion object {
        private const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001

        fun formatTimestamp(date: Date): String {
            val sdf = SimpleDateFormat("MMM dd, yyyy • h:mm a", Locale.getDefault())
            return sdf.format(date)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(mapFragTag, "onCreateView")

        val view = inflater.inflate(R.layout.fragment_map, container, false)
        mapView = view.findViewById(R.id.mapView)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY)
        }
        mapView.onCreate(mapViewBundle)
        mapView.getMapAsync(this)

        return view
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap?.uiSettings?.isMyLocationButtonEnabled = true
        googleMap?.uiSettings?.isZoomControlsEnabled = true

        enableMyLocation()

        viewModel.getAllReports().observe(viewLifecycleOwner) { reports ->
            addMarkersForReports(reports)
        }
    }

    private fun addMarkersForReports(reports: List<Report>) {
        googleMap?.let { map ->
            for (report in reports) {
                val geo = report.lastSeen
                if (geo != null) {
                    val position = LatLng(geo.latitude, geo.longitude)

                    val formattedTime = report.timestamp?.let { formatTimestamp(it) } ?: "Unknown time"

                    val title = "Missing ${report.petType.ifBlank { "Unknown" }}: ${report.petName.ifBlank { "Unknown" }}"

                    val snippet = """
                        Contact: ${report.contact.ifEmpty { "No contact provided" }}
                        Reported: $formattedTime
                    """.trimIndent()

                    map.addMarker(
                        MarkerOptions()
                            .position(position)
                            .title(title)
                            .snippet(snippet)
                    )
                }
            }
        }
    }

    private fun enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        googleMap?.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val userLatLng = LatLng(location.latitude, location.longitude)
                googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 16f))

                googleMap?.addCircle(
                    CircleOptions()
                        .center(userLatLng)
                        .radius(800.0)
                        .strokeColor(Color.BLUE)
                        .fillColor(0x220000FF)
                        .strokeWidth(3f)
                )
            } else {
                Toast.makeText(requireContext(), "Unable to get current location", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            enableMyLocation()
        } else {
            Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle)
        }
        mapView.onSaveInstanceState(mapViewBundle)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(mapFragTag, "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d(mapFragTag, "onStart")
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
        if (::fusedLocationClient.isInitialized && googleMap != null) {
            enableMyLocation()
        }
        Log.d(mapFragTag, "onResume")
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
        Log.d(mapFragTag, "onPause")
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
        Log.d(mapFragTag, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(mapFragTag, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
        Log.d(mapFragTag, "onDestroy")
    }
}