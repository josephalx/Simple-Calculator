package com.example.simplecalculator

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    var state by mutableStateOf(CalculatorState())
    fun onAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Clear -> state = CalculatorState()
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Calculate -> performCalculation()
            is CalculatorAction.Delete -> performDelete()


        }

    }

    private fun performDelete() {
        when {
            state.number2.isNotBlank() -> state = state.copy(number2 = state.number2.dropLast(1))
            state.operator != null -> state = state.copy(operator = null)
            state.number1.isNotBlank() -> state = state.copy(number1 = state.number1.dropLast(1))
        }
    }

    private fun performCalculation() {
        val number1 = state.number1.toDoubleOrNull()
        val number2 = state.number2.toDoubleOrNull()
        if (number1 != null && number2 != null) {
            val result = when (state.operator) {
                CalculatorOperation.Add -> number2.let { number1.plus(it) }
                CalculatorOperation.Subtract -> number2.let { number1.minus(it) }
                CalculatorOperation.Multiply -> number2.let { number1.times(it) }
                CalculatorOperation.Divide -> number2.let { number1.div(it) }
                null -> return
            }
            state = state.copy(number1 = result.toString().take(15), number2 = "",operator = null)
        }
    }

    private fun enterOperation(operation: CalculatorOperation) {
        if (state.number1.isNotBlank()) {
            state = state.copy(operator = operation)
        }

    }

    private fun enterDecimal() {
        if (state.operator == null && !state.number1.contains(".") && state.number1.isNotBlank()) {
            state = state.copy(number1 = state.number1 + ".")
            return
        }
        if (!state.number2.contains(".") && state.number2.isNotBlank()) {
            state = state.copy(number2 = state.number2 + ".")
            return
        }
    }

    private fun enterNumber(number: Int) {
        if (state.operator == null) {
            if (state.number1.length >= MAX_NUM_LENGTH) {
                return
            }
            state = state.copy(
                number1 = state.number1 + number
            )
            return
        }
        if (state.number2.length >= MAX_NUM_LENGTH) {
            return
        }
        state = state.copy(
            number2 = state.number2 + number
        )

    }

    companion object {
        const val MAX_NUM_LENGTH = 8
    }

}

