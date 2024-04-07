package com.example.bitfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.bitfit.R
import com.example.bitfit.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class Statistics : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_statistics) // Inflate the correct layout

        // Call helper method to swap the FrameLayout with the fragment


        // Call helper method to swap the FrameLayout with the fragment
        replaceFragment(StatisticsFragment())

        val fragmentManager: FragmentManager = supportFragmentManager
        val statFragment: Fragment = StatisticsFragment()
        val listFragment: Fragment = HealthListFragment()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.dashboard -> fragment = statFragment
                R.id.log -> fragment = listFragment
            }
            fragmentManager.beginTransaction().replace(R.id.stat_frame_layout, fragment).commit()
            true
        }
        //bottomNavigationView.selectedItemId = R.id.dashboard
    }


    private fun replaceFragment(articleListFragment: StatisticsFragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.stat_frame_layout, articleListFragment)
        fragmentTransaction.commit()
    }




}