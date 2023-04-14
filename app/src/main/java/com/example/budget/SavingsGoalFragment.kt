package com.example.budget

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.activityViewModels



class SavingsGoalFragment : Fragment() {
    private val viewModel: AppViewModel by activityViewModels()
    private lateinit var radioGroupSavings: RadioGroup
    private lateinit var radioSafetyPillow: RadioButton
    private lateinit var etSafetyPillowPercentage: EditText
    private lateinit var radioProduct: RadioButton
    private lateinit var etProductPrice: EditText
    private lateinit var etMonthsToSave: EditText
    private lateinit var btnSavingsNext: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_savings_goal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        radioGroupSavings = view.findViewById(R.id.radioGroupSavings)
        radioSafetyPillow = view.findViewById(R.id.radioSafetyPillow)
        etSafetyPillowPercentage = view.findViewById(R.id.etSafetyPillowPercentage)
        radioProduct = view.findViewById(R.id.radioProduct)
        etProductPrice = view.findViewById(R.id.etProductPrice)
        etMonthsToSave = view.findViewById(R.id.etMonthsToSave)
        btnSavingsNext = view.findViewById(R.id.btnSavingsNext)
        var savingsGoalTypeValue = ""
        var savingsGoalValue = 0.0
        var savingsGoalMonthsValue = 0
        var savingsGoalPercentValue = 0.0

        radioGroupSavings.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioSafetyPillow -> {
                    savingsGoalTypeValue = "SafetyPillow"
                    etSafetyPillowPercentage.visibility = View.VISIBLE
                    etProductPrice.visibility = View.GONE
                    etMonthsToSave.visibility = View.GONE
                }
                R.id.radioProduct -> {
                    savingsGoalTypeValue = "Product"
                    etSafetyPillowPercentage.visibility = View.GONE
                    etProductPrice.visibility = View.VISIBLE
                    etMonthsToSave.visibility = View.VISIBLE
                }
                R.id.radioNoSavings -> {
                    savingsGoalTypeValue = "NoSavings"
                    etSafetyPillowPercentage.visibility = View.GONE
                    etProductPrice.visibility = View.GONE
                    etMonthsToSave.visibility = View.GONE
                }
            }
        }

        view.findViewById<Button>(R.id.btnSavingsNext).setOnClickListener {
            val csvImportFragment = ImportCSVFragment()

//            if (etProductPrice.text.toString().isNotEmpty()){
//                savingsGoalValue = etProductPrice.text.toString().toDouble() // get savings goal value from input
//                savingsGoalMonthsValue = etMonthsToSave.text.toString().toInt() // get savings goal months from inp
//            }
//            if (etSafetyPillowPercentage.toString().isNotEmpty()){
//                savingsGoalPercentValue = etSafetyPillowPercentage.toString().toDouble() / 100
//            }

            viewModel.savingsGoal.value = savingsGoalValue
            viewModel.savingsGoalType.value = savingsGoalTypeValue
            viewModel.savingsPercent.value = savingsGoalPercentValue
            viewModel.savingsGoalMonths.value = savingsGoalMonthsValue

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, csvImportFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}