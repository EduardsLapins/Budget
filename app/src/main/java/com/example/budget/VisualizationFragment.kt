package com.example.budget

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class VisualizationFragment : Fragment() {

    private lateinit var lineChart: LineChart
    private lateinit var donutChart: PieChart

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
        val viewModel: AppViewModel by activityViewModels()

        lineChart = view.findViewById(R.id.line_chart)

        // Retrieve income and expenses from arguments
        val income = viewModel.income.value ?: 0.0
        val expenses = viewModel.expenses.value ?: 0.0
        val savingsGoal = viewModel.savingsGoal.value ?: 0.0
        val savingsGoalPercent = viewModel.savingsPercent.value ?: 0.0
        val savingsGoalType = viewModel.savingsGoalType.value ?: ""
        val savingsGoalMonths = viewModel.savingsGoalMonths.value ?: 0


        // Calculate daily income and daily expenses
        val daysInMonth = LocalDate.now().lengthOfMonth().toDouble()
        val dailyIncome = income / daysInMonth
        val dailyExpenses = expenses / daysInMonth

        // Populate chart data
        val incomeEntries = mutableListOf<Entry>()
        val expenseEntries = mutableListOf<Entry>()

        donutChart = view.findViewById(R.id.donut_chart)

        // Set up graphics
        setupDonutChart(donutChart, dailyIncome, dailyExpenses)
        updateFreeDailyAssets(view, dailyIncome, dailyExpenses)
        val freeAssetsEntries = ArrayList<Entry>()

        for (i in 1..daysInMonth.toInt()) {
            val freeAssets = dailyIncome - (dailyExpenses + Random.nextInt(0, 21))
            freeAssetsEntries.add(Entry(i.toFloat(), freeAssets.toFloat()))
        }

        val freeAssetsLineDataSet = LineDataSet(freeAssetsEntries, "Free Assets").apply {
            color = Color.BLUE
            lineWidth = 3f // Make the line thicker
            setDrawCircles(false) // Remove data points
            setDrawValues(false) // Remove data values
        }

        val lineData = LineData(freeAssetsLineDataSet)
        lineChart.data = lineData

// Customize the chart appearance
        lineChart.description.isEnabled = false
        lineChart.legend.isEnabled = true
        lineChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
            granularity = 1f // Show labels for each day
            setLabelCount(
                daysInMonth.toInt(),
                true
            ) // Set the number of labels to match the days in the month
        }

        lineChart.axisLeft.apply {
            setDrawGridLines(false)
            setDrawZeroLine(true) // Draw a line at the zero position
        }

        lineChart.axisRight.isEnabled = false // Disable the right axis

// Refresh the chart
        lineChart.invalidate()
    }
}

private fun setupDonutChart(donutChart: PieChart, dailyIncome: Double, dailyExpenses: Double) {
    val expensePercentage = (dailyExpenses / dailyIncome) * 100

    val entries = ArrayList<PieEntry>().apply {
        add(PieEntry(expensePercentage.toFloat(), "Expenses"))
        add(PieEntry((100 - expensePercentage).toFloat(), "Remaining"))
    }

    val expenseColor = getGradientColor(expensePercentage.toFloat()) // Get the gradient color for expenses

    val dataSet = PieDataSet(entries, "").apply {
        setDrawIcons(false)
        sliceSpace = 2f
        iconsOffset = MPPointF(0f, 40f)
        selectionShift = 5f
        colors = listOf(expenseColor, ColorTemplate.COLOR_NONE) // Use the gradient color for expenses
    }

    // Customize the legend
    val legend = donutChart.legend.apply {
        verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        orientation = Legend.LegendOrientation.HORIZONTAL
        setDrawInside(false)
        xEntrySpace = 7f
        yEntrySpace = 0f
        textColor = Color.DKGRAY
    }

    val pieData = PieData(dataSet)
    pieData.setDrawValues(false)

    donutChart.apply {
        data = pieData
        description.isEnabled = false
        isRotationEnabled = false
        setHoleColor(Color.TRANSPARENT)
        setDrawCenterText(false)
        setDrawEntryLabels(false)
        holeRadius = 60f // Adjust the hole radius to create the donut shape
        setTransparentCircleAlpha(0)
        setUsePercentValues(false)
        legend.isEnabled = true
        setExtraOffsets(5f, 10f, 5f, 5f)
        rotationAngle = 180f
        maxAngle = 180f // Set the max angle to 180 to make it a semi-circular chart
        animateY(1400, Easing.EaseInOutQuad)
    }
}

private fun interpolateColor(a: Int, b: Int, ratio: Float): Int {
    val alpha = Color.alpha(a) + (Color.alpha(b) - Color.alpha(a)) * ratio
    val red = Color.red(a) + (Color.red(b) - Color.red(a)) * ratio
    val green = Color.green(a) + (Color.green(b) - Color.green(a)) * ratio
    val blue = Color.blue(a) + (Color.blue(b) - Color.blue(a)) * ratio
    return Color.argb(alpha.toInt(), red.toInt(), green.toInt(), blue.toInt())
}
private fun getGradientColor(expensePercentage: Float): Int {
    val gradientColors = intArrayOf(
        Color.GREEN, // Green for low expense percentage
        Color.YELLOW, // Yellow for medium expense percentage
        Color.RED // Red for high expense percentage
    )

    // Update color positions for the gradient
    val colorPositions = floatArrayOf(0f, 0.5f, 0.8f) // Set color positions for the gradient

    // Calculate the interpolated color based on the expense percentage
    return when (expensePercentage) {
        in 0f..50f -> interpolateColor(gradientColors[0], gradientColors[1], (expensePercentage - colorPositions[0]) / (colorPositions[1] - colorPositions[0]))
        in 50f..80f -> interpolateColor(gradientColors[1], gradientColors[2], (expensePercentage - colorPositions[1]) / (colorPositions[2] - colorPositions[1]))
        in 80f..100f -> interpolateColor(gradientColors[2], gradientColors[2], 1f)
        else -> gradientColors[0]
    }
}

private fun updateFreeDailyAssets(view:View, dailyIncome: Double, dailyExpenses: Double) {
    val totalMonthlyIncome = dailyIncome * daysInMonth()
    val freeDailyAssets = (0.4 * totalMonthlyIncome - (totalDailyIncomeSoFar(dailyIncome) - totalDailyExpensesSoFar(dailyExpenses))) / daysInMonth()

    val freeDailyAssetsText = "Free Daily Assets: %.2f".format(freeDailyAssets)
    view.findViewById<TextView>(R.id.tv_free_daily_assets)?.text = freeDailyAssetsText
}

private fun daysInMonth(): Int {
    val calendar = Calendar.getInstance()
    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
}

private fun totalDailyIncomeSoFar(dailyIncome: Double): Double {
    // Replace with the logic to calculate the total daily income so far
    return dailyIncome * daysInMonth()
}

private fun totalDailyExpensesSoFar(dailyExpenses: Double): Double {
    // Replace with the logic to calculate the total daily expenses so far
    return dailyExpenses * daysInMonth()
}