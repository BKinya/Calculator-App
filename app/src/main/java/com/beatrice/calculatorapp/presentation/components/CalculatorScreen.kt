package com.beatrice.calculatorapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beatrice.calculatorapp.presentation.ui.theme.CalculatorAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Num 1.",
            fontSize = 18.sp,
            fontFamily = FontFamily.Serif
        )
        Spacer(modifier = modifier.height(10.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {},
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
            value = "",
            onValueChange = {},
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = modifier.height(18.dp))
        Text(
            "=",
            fontSize = 40.sp,
            fontFamily = FontFamily.Serif
        )
        Spacer(modifier = modifier.height(20.dp))
        Text(
            "20",
            fontSize = 34.sp,
            fontFamily = FontFamily.Serif
        )
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