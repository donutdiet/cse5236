package com.example.myapplication
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    val mainActTag = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(mainActTag, "onCreate")
        setContentView(R.layout.activity_main)
        currentFragment(MapFragment())
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_map -> currentFragment(MapFragment())
                R.id.nav_report -> currentFragment(ReportFragment())
                R.id.nav_profile -> currentFragment(ProfileFragment())
                R.id.nav_dummy -> currentFragment(DummyReportsFragment())
            }
            true
        }
    }
    private fun currentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
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
