package com.example.myapplication
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.map.MapFragment
import com.example.myapplication.profile.ProfileFragment
import com.example.myapplication.reports.AllReportsFragment
import com.example.myapplication.reports.ReportFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    val mainActTag = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(mainActTag, "onCreate")

        setContentView(R.layout.activity_main)
        currentFragment(MapFragment())

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_map -> currentFragment(MapFragment())
                R.id.nav_report -> currentFragment(ReportFragment())
                R.id.nav_allReports -> currentFragment(AllReportsFragment())
                R.id.nav_profile -> currentFragment(ProfileFragment())
            }
            true
        }
    }
    private fun currentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
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
