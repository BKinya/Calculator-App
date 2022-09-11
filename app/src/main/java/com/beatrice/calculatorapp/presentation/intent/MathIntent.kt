package com.beatrice.calculatorapp.presentation.intent

sealed class MathIntent{
    data class UpdateSum(val num1: Int, val num2: Int): MathIntent()
}
