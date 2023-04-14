package com.example.budget
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppViewModel : ViewModel() {
    val income = MutableLiveData<Double>()
    val expenses = MutableLiveData<Double>()
    val savingsPercent = MutableLiveData<Double>()
    val savingsGoal = MutableLiveData<Double>()
    val savingsGoalType = MutableLiveData<String>()
    val savingsGoalMonths = MutableLiveData<Int>()

}