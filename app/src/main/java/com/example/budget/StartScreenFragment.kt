package com.example.budget

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StartScreenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class StartScreenFragment : Fragment() {
    private lateinit var etIncome: EditText
    private lateinit var etExpenses: EditText
    private lateinit var btnSubmit: Button


    private fun saveUserData(income: Double, expenses: Double) {
        val sharedPreferences = requireActivity().getSharedPreferences("budgetAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("income", income.toFloat())
        editor.putFloat("expenses", expenses.toFloat())
        editor.apply()
    }

    private fun loadUserData(): UserData {
        val sharedPreferences = requireActivity().getSharedPreferences("budgetAppPrefs", Context.MODE_PRIVATE)
        val income = sharedPreferences.getFloat("income", 0f).toDouble()
        val expenses = sharedPreferences.getFloat("expenses", 0f).toDouble()

        return UserData(income, expenses)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: AppViewModel by activityViewModels()

        etIncome = view.findViewById(R.id.et_income)
        etExpenses = view.findViewById(R.id.et_expenses)
        btnSubmit = view.findViewById(R.id.btn_submit)

        btnSubmit.setOnClickListener {
            val income = etIncome.text.toString().toDouble()
            val expenses = etExpenses.text.toString().toDouble()

            viewLifecycleOwner.lifecycleScope.launch {
                saveUserData(income, expenses)
            }
        }

        view.findViewById<Button>(R.id.btn_submit).setOnClickListener {
            val income = etIncome.text.toString().toDouble()
            val expenses = etExpenses.text.toString().toDouble()

            viewModel.income.value = income
            viewModel.expenses.value = expenses

            val savingsGoalFragment = SavingsGoalFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, savingsGoalFragment)
                .addToBackStack(null)
                .commit()
        }



    }
}