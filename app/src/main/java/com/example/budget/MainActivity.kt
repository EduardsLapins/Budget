package com.example.budget

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.view.View
//import android.widget.Button
//import com.github.mikephil.charting.charts.LineChart
//import com.github.mikephil.charting.data.Entry
//import com.github.mikephil.charting.data.LineData
//import com.github.mikephil.charting.data.LineDataSet


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Display StartScreenFragment at the start
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, StartScreenFragment())
            .commit()
    }
}