package com.example.budget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainMenuActivity : AppCompatActivity() {

    private lateinit var btnLineChart: Button
    private lateinit var btnPlaceholder: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        btnLineChart = findViewById(R.id.btn_line_chart)
        btnPlaceholder = findViewById(R.id.btn_placeholder)

        btnLineChart.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnPlaceholder.setOnClickListener {
            val intent = Intent(this, PlaceholderActivity::class.java)
            startActivity(intent)
        }
    }
}