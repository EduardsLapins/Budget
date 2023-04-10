package com.example.budget

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ImportCSVFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class ImportCSVFragment : Fragment() {

    private lateinit var btnImportCSV: Button
    private lateinit var btnSkip: Button
    private lateinit var tvStatus: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_import_csv, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnImportCSV = view.findViewById(R.id.btn_import_csv)
        btnSkip = view.findViewById(R.id.btn_skip)
        tvStatus = view.findViewById(R.id.tv_status)

        // Set onClickListener for the import button
        btnImportCSV.setOnClickListener {
            // Implement CSV import functionality here
            // In the future, this is where you'd establish a connection with the bank API
        }

        // Set onClickListener for the skip button
        btnSkip.setOnClickListener {
            // Retrieve income and expenses from arguments
            val income = arguments?.getDouble("income") ?: 0.0
            val expenses = arguments?.getDouble("expenses") ?: 0.0

            // Pass the user data as arguments to VisualizationFragment
            val visualizationFragment = VisualizationFragment().apply {
                arguments = Bundle().apply {
                    putDouble("income", income)
                    putDouble("expenses", expenses)
                }
            }

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, visualizationFragment)
                .commit()
        }


    }
}