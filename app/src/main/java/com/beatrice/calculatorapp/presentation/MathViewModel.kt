package com.beatrice.calculatorapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beatrice.calculatorapp.presentation.events.Event
import com.beatrice.calculatorapp.presentation.events.MathEvent
import com.beatrice.calculatorapp.presentation.model.Sum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch

class MathViewModel : ViewModel() {

    val mathEvent = Channel<MathEvent>(Channel.UNLIMITED)

    private val _sumUiState = MutableStateFlow(Sum())
    val sumUiState: StateFlow<Sum>
        get() = _sumUiState

    private val hundredsEvent = Channel<Event?>(Channel.BUFFERED)
    val event = hundredsEvent.receiveAsFlow()

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            mathEvent.consumeAsFlow().collect { intent ->
                when (intent) {
                    is MathEvent.UpdateSum -> updateSum(intent)
                }
            }
        }

    }

    private suspend fun updateSum(intent: MathEvent.UpdateSum) {
        with(intent) {
            val result = num1.plus(num2)
            val newSum = Sum(num1 = num1, num2 = num2, sum = result)
            _sumUiState.value = newSum
            if (result > 100) {
                Dispatchers.Main.immediate.invoke {
                    hundredsEvent.send(Event.ShowSnackBar)
                }
            }
        }

    }
}