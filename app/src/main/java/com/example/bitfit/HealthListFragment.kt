package com.example.bitfit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bitfit.R
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class HealthListFragment : Fragment() {
    private val healths = mutableListOf<DisplayHealth>()
    private lateinit var healthRecyclerView: RecyclerView
    private lateinit var healthAdapter: HealthAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val  view = inflater.inflate(R.layout.fragment_health_list, container, false)
        val layoutManager = LinearLayoutManager(context)
        healthRecyclerView = view.findViewById(R.id.health_recycler_view)
        healthRecyclerView.layoutManager = layoutManager
        healthRecyclerView.setHasFixedSize(true)
        healthAdapter = HealthAdapter(view.context, healths)
        healthRecyclerView.adapter = healthAdapter


        return view
    }

    private fun fetchHealth() {
        var totalSleep = 0
        // Access the database instance
        val database = (requireActivity().application as HealthApplication).db

        // Execute a query to fetch health data asynchronously
        lifecycleScope.launch {
            try {
                // Retrieve data from the database using Kotlin Flow
                database.healthDao().getAll().collect { databaseList ->
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

                    // Update the list of healths in your fragment
                    healths.clear()
                    healths.addAll(mappedList)

                    // Notify the adapter that the data has changed
                    healthAdapter.notifyDataSetChanged()

                    // Calculate total sleep from the fetched data
                    var totalSleepFromDB = 0
                    for (health in mappedList) {
                        health.no_sleep?.toIntOrNull()?.let {
                            totalSleepFromDB += it
                        }
                    }

                    // Add the total sleep from the database to the current totalSleep
                    //totalSleep += totalSleepFromDB
                    //val intent = Intent(requireActivity(), Statistics::class.java)
                    //intent.putExtra("TOTAL_SLEEP", totalSleep)
                    //startActivity(intent)



                }
            } catch (e: Exception) {
                // Handle any errors that occur during data fetching
                Log.e("HealthListFragment", "Error fetching health data: ${e.message}")
            }
        }


    }



    fun updateData(foodValue: String?, sleepValue: String?) {
        // Create a DisplayHealth object with the received data
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().time)
        val health = DisplayHealth(currentDate, "Calories", "Sleep", foodValue ?: "", sleepValue ?: "")

        // Add the health object to the dataset
        healths.add(health)

        // Notify the adapter that the dataset has changed
        healthAdapter.notifyDataSetChanged()
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Call the new method within onViewCreated
        fetchHealth()
    }
    companion object {
        fun newInstance(): HealthListFragment {
            return HealthListFragment()
        }
    }


}