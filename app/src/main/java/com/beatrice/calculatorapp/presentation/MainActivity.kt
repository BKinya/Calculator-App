package com.beatrice.calculatorapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.beatrice.calculatorapp.presentation.components.CalculatorScreen
import com.beatrice.calculatorapp.presentation.intent.MathIntent
import com.beatrice.calculatorapp.presentation.ui.theme.CalculatorAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mathViewModel: MathViewModel = viewModel()
            val scope = rememberCoroutineScope()
            val sum = mathViewModel.sumUiState.collectAsStateWithLifecycle()
            CalculatorAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    CalculatorScreen(
                        sum = sum.value,
                        onNum1Changed = {
                            val num =
                                try {
                                    it.toInt()
                                } catch (e: Exception) {
                                    0
                                }
                            scope.launch {
                                mathViewModel.mathIntent.send(
                                    MathIntent.UpdateSum(
                                        num1 = num,
                                        num2 = sum.value.num2
                                    )
                                )
                            }
                        },
                        onNum2Changed = {
                            val num =
                                try {
                                    it.toInt()
                                } catch (e: Exception) {
                                    0
                                }
                            scope.launch {
                                mathViewModel.mathIntent.send(
                                    MathIntent.UpdateSum(
                                        num1 = sum.value.num1,
                                        num2 = num
                                    )
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

