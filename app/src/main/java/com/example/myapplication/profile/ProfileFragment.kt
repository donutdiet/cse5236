package com.example.myapplication.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.auth.LoginActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {
    private val profileFragTag = "ProfileFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val emailTextView = view.findViewById<TextView>(R.id.emailTextView)
        val uidTextView = view.findViewById<TextView>(R.id.uidTextView)
        val newEmailEditText = view.findViewById<EditText>(R.id.newEmailEditText)
        val updateEmailButton = view.findViewById<Button>(R.id.updateEmailButton)
        val newPasswordEditText = view.findViewById<EditText>(R.id.newPasswordEditText)
        val updatePasswordButton = view.findViewById<Button>(R.id.updatePasswordButton)
        val logoutButton = view.findViewById<Button>(R.id.logoutButton)
        var isShowingUID = false

        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            emailTextView.text = "Email: ${user.email}"
            uidTextView.text = "Tap to show UID"

            uidTextView.setOnClickListener {
                isShowingUID = !isShowingUID
                if (isShowingUID) {
                    uidTextView.text = "UID: ${user.uid}"
                } else {
                    uidTextView.text = "Tap to show UID"
                }
            }

            updateEmailButton.setOnClickListener {
                val newEmail = newEmailEditText.text.toString().trim()

                if (newEmail.isNotEmpty()) {
                    val task = user.verifyBeforeUpdateEmail(newEmail)

                    task.addOnCompleteListener { result ->
                        if (result.isSuccessful) {
                            emailTextView.text = "Email: $newEmail"
                            Toast.makeText(requireContext(), "Verification email sent. Please verify to update.", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), result.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }

                } else {
                    Toast.makeText(requireContext(), "Please enter a new email", Toast.LENGTH_SHORT).show()
                }
            }

            updatePasswordButton.setOnClickListener {
                val newPassword = newPasswordEditText.text.toString().trim()

                if (newPassword.isNotEmpty()) {

                    val task = user.updatePassword(newPassword)

                    task.addOnCompleteListener { result ->
                        if (result.isSuccessful) {
                            Toast.makeText(requireContext(), "Password updated successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), result.exception?.message ?: "Failed to update password", Toast.LENGTH_SHORT).show()
                        }
                    }

                } else {
                    Toast.makeText(requireContext(), "Please enter a new password", Toast.LENGTH_SHORT).show()
                }
            }

        } else {
            emailTextView.text = "Not logged in"
            uidTextView.text = ""
        }

        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        return view
    }
}
