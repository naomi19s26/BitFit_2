package com.example.bitfit

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var items: MutableList<DisplayHealth>
    private lateinit var adapter: HealthAdapter // Declare adapter at the activity level

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val healthRv: RecyclerView = findViewById(R.id.rvFit)
        healthRv.layoutManager = LinearLayoutManager(this)
        items = mutableListOf() // Initialize the list
        adapter = HealthAdapter(items) // Initialize the adapter
        healthRv.adapter = adapter // Set the adapter to RecyclerView

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this, HealthActivity::class.java)
            startActivityForResult(intent, 0)
        }

        lifecycleScope.launch {
            // Retrieve data from the database
            (application as HealthApplication).db.healthDao().getAll().collect { databaseList ->
                // Map database entities to DisplayHealth objects
                val mappedList = databaseList.map { entity ->
                    DisplayHealth(
                        entity.date,
                        entity.calories,
                        entity.sleep,
                        entity.noCalories,
                        entity.noSleep
                    )
                }
                // Update the items list with the database data
                items.clear()
                items.addAll(mappedList)
                // Notify the adapter that data has changed
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().time)
            val noCalories = data?.getStringExtra("FOOD_VALUE") ?: ""
            val noSleep = data?.getStringExtra("SLEEP_VALUE") ?: ""

            // Create a new Health object with the obtained data
            val health = DisplayHealth(currentDate, "Calories", "Sleep", noCalories, noSleep)
            items.add(health)
            adapter.notifyDataSetChanged()

            Log.d("MainActivity", "Adding new health data to RecyclerView: $health")

            // Insert data into the database on a background thread
            lifecycleScope.launch(IO) {
                try {
                    (application as HealthApplication).db.healthDao().insert(HealthEntity(
                        currentDate,
                        "Calories",
                        "Sleep",
                        noCalories,
                        noSleep
                    ))
                    Log.d("MainActivity", "Inserted new health data into database: $health")
                } catch (e: Exception) {
                    Log.e("MainActivity", "Error inserting data into database: ${e.message}")
                }
            }
        }
    }

}