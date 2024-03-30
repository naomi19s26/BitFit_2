package com.example.bitfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar

class HealthActivity : AppCompatActivity() {

    private lateinit var button: Button

    private lateinit var foodEditText: EditText
    private lateinit var calEditText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health)


        foodEditText = findViewById(R.id.input_food)
        calEditText = findViewById(R.id.input_calories)
        button = findViewById(R.id.button2)


        button.setOnClickListener{
            val foodValue = foodEditText.text.toString()
            val calValue = calEditText.text.toString()

            val intent = Intent()
            intent.putExtra("FOOD_VALUE", foodValue)
            intent.putExtra("SLEEP_VALUE", calValue)
            setResult(RESULT_OK, intent)
            finish()

        }






    }
}