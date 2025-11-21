package com.example.myapplication.map

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
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
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.getValue
import androidx.core.graphics.scale

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private var googleMap: GoogleMap? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val viewModel: ReportsViewModel by viewModels()


    private val mapFragTag = "MapFragment"

    companion object {
        private const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
        private const val CIRCLE_RADIUS = 3000.0

        private var userLatLng: LatLng? = null
        private var showOnlyWithinRadius = true
        private var currentReports: List<Report> = emptyList()


        fun formatTimestamp(date: Date): String {
            val sdf = SimpleDateFormat("MMM dd, yyyy • h:mm a", Locale.getDefault())
            return sdf.format(date)
        }
    }

    inner class ReportInfoWindowAdapter : GoogleMap.InfoWindowAdapter {

        private val window = layoutInflater.inflate(R.layout.marker_info_window, null)

        private fun render(marker: Marker, view: View) {
            val titleView = view.findViewById<TextView>(R.id.title)
            val snippetView = view.findViewById<TextView>(R.id.snippet)

            titleView.text = marker.title
            snippetView.text = marker.snippet
        }

        override fun getInfoWindow(marker: Marker): View? {
            render(marker, window)
            return window
        }

        override fun getInfoContents(marker: Marker): View? {
            return null
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(mapFragTag, "onCreateView")

        val view = inflater.inflate(R.layout.fragment_map, container, false)

        val toggle = view.findViewById<SwitchCompat>(R.id.toggleRadius)
        toggle.setOnCheckedChangeListener { _, isChecked ->
            showOnlyWithinRadius = isChecked
            refreshMarkers()
        }

        val refreshButton = view.findViewById<Button>(R.id.refreshMarkersButton)
        refreshButton.setOnClickListener {
            refreshMarkers()
        }

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
        googleMap?.setInfoWindowAdapter(ReportInfoWindowAdapter())

        enableMyLocation()

        viewModel.getAllReports().observe(viewLifecycleOwner) { reports ->
            addMarkersForReports(reports)
        }
    }

    private fun addMarkersForReports(reports: List<Report>) {
        currentReports = reports

        googleMap?.let { map ->
            for (report in reports.filter { !it.found }) {
                val geo = report.lastSeen ?: continue
                val position = LatLng(geo.latitude, geo.longitude)

                if (showOnlyWithinRadius) {
                    val center = userLatLng ?: continue
                    if (!isWithinRadius(center, position, CIRCLE_RADIUS)) {
                        continue
                    }
                }

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
                        .icon(getMarkerIcon())
                )
            }
        }
    }

    private fun getMarkerIcon(): BitmapDescriptor {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_pet)
        val scaled = bitmap.scale(80, 80, false)

        return BitmapDescriptorFactory.fromBitmap(scaled)
    }

    private fun enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        googleMap?.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                userLatLng = LatLng(location.latitude, location.longitude)
                googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng!!, 13f))

                googleMap?.addCircle(
                    CircleOptions()
                        .center(userLatLng!!)
                        .radius(CIRCLE_RADIUS)
                        .strokeColor(Color.BLUE)
                        .fillColor(0x220000FF)
                        .strokeWidth(3f)
                )
            } else {
                Toast.makeText(requireContext(), "Unable to get current location", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun refreshMarkers() {
        googleMap?.clear()

        // Re-add circle
        userLatLng?.let {
            googleMap?.addCircle(
                CircleOptions()
                    .center(it)
                    .radius(CIRCLE_RADIUS)
                    .strokeColor(Color.BLUE)
                    .fillColor(0x220000FF)
                    .strokeWidth(3f)
            )
        }

        // Re-add filtered or unfiltered markers
        addMarkersForReports(currentReports)
    }


    private fun isWithinRadius(
        center: LatLng,
        point: LatLng,
        radiusMeters: Double
    ): Boolean {
        val results = FloatArray(1)
        Location.distanceBetween(
            center.latitude,
            center.longitude,
            point.latitude,
            point.longitude,
            results
        )
        return results[0] <= radiusMeters
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