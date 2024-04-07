package com.example.bitfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bitfit.databinding.ActivityMainBinding
import com.example.bitfit.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Call helper method to swap the FrameLayout with the fragment
        replaceFragment(HealthListFragment())

        val fragmentManager: FragmentManager = supportFragmentManager
        val statFragment: Fragment = StatisticsFragment()
        val listFragment: Fragment = HealthListFragment()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.log -> fragment = listFragment
                R.id.dashboard -> fragment = statFragment


            }
            fragmentManager.beginTransaction().replace(R.id.health_frame_layout, fragment).commit()
            true
        }
        //bottomNavigationView.selectedItemId = R.id.log


        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this, HealthActivity::class.java)
            startActivityForResult(intent, 0)
        }




    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val foodValue = data?.getStringExtra("FOOD_VALUE")
            val sleepValue = data?.getStringExtra("SLEEP_VALUE")

            // Forward the received data to HealthListFragment
            val fragment = supportFragmentManager.findFragmentById(R.id.health_recycler_view) as? HealthListFragment
            fragment?.updateData(foodValue, sleepValue)
        }
    }

    private fun replaceFragment(articleListFragment: HealthListFragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.health_frame_layout, articleListFragment)
        fragmentTransaction.commit()
    }

}