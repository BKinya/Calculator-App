package com.beatrice.calculatorapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beatrice.calculatorapp.presentation.intent.MathIntent
import com.beatrice.calculatorapp.presentation.model.Sum
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MathViewModel : ViewModel() {

    val mathIntent = Channel<MathIntent>(Channel.UNLIMITED)

    private val _sumUiState = MutableStateFlow(Sum())
    val sumUiState: StateFlow<Sum>
        get() = _sumUiState

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            mathIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    is MathIntent.UpdateSum -> updateSum(intent)
                }
            }
        }

    }

    private fun updateSum(intent: MathIntent.UpdateSum) {
        with(intent) {
            val result = num1.plus(num2)
            val newSum = Sum(num1 = num1, num2 = num2, sum = result)
            _sumUiState.value = newSum
        }

    }
}