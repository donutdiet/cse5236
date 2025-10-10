package com.example.myapplication.auth

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val AUTH_TAG = "Auth"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        val email = "user@example.com"
        val password = "password"

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { signUp ->
                if (signUp.isSuccessful) {
                    Log.d(AUTH_TAG, "Sign up successful: ${auth.currentUser?.email}")
                } else {
                    Log.e(AUTH_TAG, "Sign up failed", signUp.exception)
                }
            }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { signIn ->
                if (signIn.isSuccessful) {
                    Log.d(AUTH_TAG, "Sign in successful: ${auth.currentUser?.email}")
                } else {
                    Log.e(AUTH_TAG, "Sign in failed", signIn.exception)
                }
            }
    }
}