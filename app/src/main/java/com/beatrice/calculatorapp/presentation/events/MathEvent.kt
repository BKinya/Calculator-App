package com.beatrice.calculatorapp.presentation.events

sealed class MathEvent {
    data class UpdateSum(val num1: Int, val num2: Int) : MathEvent()
}

sealed class Event {
    object ShowSnackBar : Event()
}