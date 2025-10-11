package com.example.myapplication.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth

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
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val emailTextView = view.findViewById<TextView>(R.id.emailTextView)
        val uidTextView = view.findViewById<TextView>(R.id.uidTextView)
        val logoutButton = view.findViewById<Button>(R.id.logoutButton)

        // ✅ Get current user
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            emailTextView.text = "Email: ${user.email}"
            uidTextView.text = "UID: ${user.uid}"
        } else {
            emailTextView.text = "Not logged in"
            uidTextView.text = ""
        }

        // 🚪 Log out button
        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        return view

        return view
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