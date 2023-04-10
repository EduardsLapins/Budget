package com.example.budget

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.time.LocalDate

class VisualizationFragment : Fragment() {

    private lateinit var lineChart: LineChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_visualization, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lineChart = view.findViewById(R.id.line_chart)

        // Retrieve income and expenses from arguments
        val income = arguments?.getDouble("income") ?: 0.0
        val expenses = arguments?.getDouble("expenses") ?: 0.0

        // Calculate daily income and daily expenses
        val daysInMonth = LocalDate.now().lengthOfMonth().toDouble()
        val dailyIncome = income / daysInMonth
        val dailyExpenses = expenses / daysInMonth

        // Populate chart data
        val incomeEntries = mutableListOf<Entry>()
        val expenseEntries = mutableListOf<Entry>()

        for (i in 1..daysInMonth.toInt()) {
            incomeEntries.add(Entry(i.toFloat(), (dailyIncome).toFloat()))
            expenseEntries.add(Entry(i.toFloat(), (dailyExpenses).toFloat()))
        }

        val incomeLineDataSet = LineDataSet(incomeEntries, "Daily Income").apply {
            color = Color.GREEN
            lineWidth = 3f // Make the line thicker
            setDrawCircles(false) // Remove data points
            setDrawValues(false) // Remove data values
        }

        val expenseLineDataSet = LineDataSet(expenseEntries, "Daily Expenses").apply {
            color = Color.RED
            lineWidth = 3f // Make the line thicker
            setDrawCircles(false) // Remove data points
            setDrawValues(false) // Remove data values
        }

        val lineData = LineData(incomeLineDataSet, expenseLineDataSet)
        lineChart.data = lineData

        // Customize the chart appearance
        lineChart.description.isEnabled = false
        lineChart.legend.isEnabled = true
        lineChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
        }
        lineChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
            granularity = 1f // Show labels for each day
            setLabelCount(daysInMonth.toInt(), true) // Set the number of labels to match the days in the month
        }

        lineChart.axisLeft.apply {
            setDrawGridLines(false)
            setDrawZeroLine(true) // Draw a line at the zero position
        }
        // Refresh the chart
        lineChart.invalidate()
    }


}