package com.beatrice.calculatorapp.presentation.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beatrice.calculatorapp.presentation.model.Sum
import com.beatrice.calculatorapp.presentation.ui.theme.CalculatorAppTheme

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(
    modifier: Modifier = Modifier,
    sum: Sum = Sum(),
    onNum1Changed: (String) -> Unit = {},
    onNum2Changed: (String) -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val showResult by remember(sum.sum) {
                Log.d("Calculated", "when")
                derivedStateOf { sum.sum > 0 }
            }
            val showSnackBar by remember(sum.sum) {
                derivedStateOf { sum.sum > 100 }
            }

            Text(
                "Num 1.",
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = modifier.height(10.dp))
            OutlinedTextField(
                value = "${sum.num1}",
                onValueChange = onNum1Changed,
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = modifier.height(18.dp))
            Text(
                "+",
                fontSize = 40.sp,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = modifier.height(18.dp))
            Text(
                "Num 2.",
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = modifier.height(10.dp))
            OutlinedTextField(
                value = "${sum.num2}",
                onValueChange = onNum2Changed,
                shape = RoundedCornerShape(12.dp)
            )
            if (showResult) {
                Spacer(modifier = modifier.height(18.dp))
                Text(
                    "=",
                    fontSize = 40.sp,
                    fontFamily = FontFamily.Serif
                )
                Spacer(modifier = modifier.height(20.dp))
                Text(
                    "${sum.sum}",
                    fontSize = 34.sp,
                    fontFamily = FontFamily.Serif
                )
            }
//            if (showSnackBar) {
//                scope.launch {
//                    snackbarHostState.showSnackbar("I am a snackbar! 😀")
//                }
//            }
        }
    }
}


@Preview
@Composable
fun CalculatorScreenPreview() {
    CalculatorAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            CalculatorScreen()
        }
    }
}