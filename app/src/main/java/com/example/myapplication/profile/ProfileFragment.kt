package com.example.myapplication.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.R

class ProfileFragment : Fragment() {
    private val profileFragTag = "ProfileFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(profileFragTag, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(profileFragTag, "onCreateView")
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onStart() {
        super.onStart()
        Log.d(profileFragTag, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(profileFragTag, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(profileFragTag, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(profileFragTag, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(profileFragTag, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(profileFragTag, "onDestroy")
    }
}