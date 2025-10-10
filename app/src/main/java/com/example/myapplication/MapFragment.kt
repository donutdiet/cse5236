package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class MapFragment : Fragment() {
    val mapFragTag = "MapFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(mapFragTag, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(mapFragTag, "onCreateView")
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onStart() {
        super.onStart()
        Log.d(mapFragTag, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(mapFragTag, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(mapFragTag, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(mapFragTag, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(mapFragTag, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(mapFragTag, "onDestroy")
    }
}
