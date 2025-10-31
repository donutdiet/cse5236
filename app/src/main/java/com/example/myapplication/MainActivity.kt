package com.example.myapplication
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    val mainActTag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(mainActTag, "onCreate")

        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setupWithNavController(navController)
    }

    override fun onStart() {
        super.onStart()
        Log.d(mainActTag, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(mainActTag, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(mainActTag, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(mainActTag, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(mainActTag, "onDestroy")
    }
}
