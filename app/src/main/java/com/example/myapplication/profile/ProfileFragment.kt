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
import com.google.firebase.auth.FirebaseUser

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
        val currentPasswordEditText = view.findViewById<EditText>(R.id.currentPasswordEditText)
        val newPasswordEditText = view.findViewById<EditText>(R.id.newPasswordEditText)
        val updatePasswordButton = view.findViewById<Button>(R.id.updatePasswordButton)
        val logoutButton = view.findViewById<Button>(R.id.logoutButton)
        var isShowingUID = false

        val user = FirebaseAuth.getInstance().currentUser

        fun reauthenticateUser(user: FirebaseUser, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
            val credential = EmailAuthProvider.getCredential(user.email!!, password)
            user.reauthenticate(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception?.message ?: "Re-authentication failed")
                }
            }
        }

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
                val currentPassword = currentPasswordEditText.text.toString().trim()

                if (newEmail.isEmpty() || currentPassword.isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter a new email and your current password.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                reauthenticateUser(
                    user,
                    currentPassword,
                    onSuccess = {
                        user.verifyBeforeUpdateEmail(newEmail).addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                Toast.makeText(requireContext(), "Verification email sent. Please verify to update.", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(requireContext(), "Failed to send verification email", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    onFailure = { error ->
                        Toast.makeText(requireContext(), "Re-authentication failed", Toast.LENGTH_SHORT).show()
                    }
                )
            }

            updatePasswordButton.setOnClickListener {
                val newPassword = newPasswordEditText.text.toString().trim()
                val currentPassword = currentPasswordEditText.text.toString().trim()

                if (newPassword.isEmpty() || currentPassword.isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter your current password and a new password.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                reauthenticateUser(
                    user,
                    currentPassword,
                    onSuccess = {
                        user.updatePassword(newPassword).addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                Toast.makeText(requireContext(), "Password updated successfully", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(requireContext(), "Failed to update password", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    onFailure = { error ->
                        Toast.makeText(requireContext(), "Re-authentication failed", Toast.LENGTH_SHORT).show()
                    }
                )
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
