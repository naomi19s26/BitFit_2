package com.example.bitfit

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class StatisticsFragment : Fragment() {
    private var minNoCalories: Int? = null
    private var maxNoCalories: Int? = null
    private var sumCalories = 0
    private var averageCalories = 0
    private var count = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_statistics, container, false)

        // Button to launch HealthActivity
        val button = view.findViewById<Button>(R.id.button3)
        button.setOnClickListener {
            count++
            val intent = Intent(requireContext(), HealthActivity::class.java)
            startActivityForResult(intent, 0)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == AppCompatActivity.RESULT_OK) {
            val caloriesValue = data?.getStringExtra("SLEEP_VALUE")?.toIntOrNull()

            if (caloriesValue != null) {
                // Update values asynchronously
                lifecycleScope.launch {
                    updateValues(caloriesValue)
                }
            }
        }
    }

    private suspend fun updateValues(caloriesValue: Int) {
        // Update minNoCalories if a smaller value is received
        minNoCalories = minNoCalories?.coerceAtMost(caloriesValue) ?: caloriesValue

        // Update maxNoCalories if a larger value is received
        maxNoCalories = maxNoCalories?.coerceAtLeast(caloriesValue) ?: caloriesValue

        // Update sum of calories
        sumCalories += caloriesValue

        // Calculate average calories
        averageCalories = sumCalories / count

        // Update TextViews with the calculated values on the main thread
        withContext(Dispatchers.Main) {
            requireView().findViewById<TextView>(R.id.minimum)?.text = minNoCalories?.toString() ?: "N/A"
            requireView().findViewById<TextView>(R.id.maximum)?.text = maxNoCalories?.toString() ?: "N/A"
            requireView().findViewById<TextView>(R.id.average)?.text = averageCalories.toString()
        }
    }
}
