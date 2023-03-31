package com.example.budget

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LineChartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LineChartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_line_chart, container, false)
        val lineChart: LineChart = view.findViewById(R.id.line_chart)

        // Generate random data points
        val dataPoints = mutableListOf<Entry>()
        for (i in 0..10) {
            dataPoints.add(Entry(i.toFloat(), (0..100).random().toFloat()))
        }

        // Create a LineDataSet with the data points
        val lineDataSet = LineDataSet(dataPoints, "Example Line Chart")
        lineDataSet.color = Color.RED
        lineDataSet.valueTextColor = Color.BLACK
        lineDataSet.valueTextSize = 12f

        // Create a LineData object with the LineDataSet
        val lineData = LineData(lineDataSet)

        // Set the LineData object to the LineChart and refresh
        lineChart.data = lineData
        lineChart.invalidate()


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LineChartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LineChartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}