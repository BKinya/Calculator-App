package com.beatrice.calculatorapp.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.beatrice.calculatorapp.presentation.components.CalculatorScreen
import com.beatrice.calculatorapp.presentation.events.MathEvent
import com.beatrice.calculatorapp.presentation.ui.theme.CalculatorAppTheme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    @SuppressLint("FlowOperatorInvokedInComposition", "CoroutineCreationDuringComposition")
    @OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val mathViewModel: MathViewModel = viewModel()
            val scope = rememberCoroutineScope()
            val sum = mathViewModel.sumUiState.collectAsStateWithLifecycle()
            val snackbarHostState = remember { SnackbarHostState() }
            CalculatorAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.White,
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { paddingValue ->
                    mathViewModel.event.flowWithLifecycle(
                        lifecycle,
                        minActiveState = Lifecycle.State.STARTED
                    ).onEach {
                        it?.let {
                            snackbarHostState.showSnackbar("I am a snackbar! 😀")
                        }
                    }.launchIn(lifecycleScope)
                    CalculatorScreen(
                        sum = sum.value,
                        onNum1Changed = { newNum ->
                            if (newNum != "") {
                                val num = newNum.toInt()
                                scope.launch {
                                    mathViewModel.mathEvent.send(
                                        MathEvent.UpdateSum(
                                            num1 = num,
                                            num2 = sum.value.num2
                                        )
                                    )
                                }
                            }
                        },
                        onNum2Changed = { newNum ->
                            if (newNum != "") {
                                val num = newNum.toInt()
                                scope.launch {
                                    mathViewModel.mathEvent.send(
                                        MathEvent.UpdateSum(
                                            num1 = sum.value.num1,
                                            num2 = num
                                        )
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

