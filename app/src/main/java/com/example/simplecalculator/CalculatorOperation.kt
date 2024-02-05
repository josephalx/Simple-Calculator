package com.example.simplecalculator

sealed class CalculatorOperation(val Symbol:String) {
    object Add: CalculatorOperation("+")
    object Subtract: CalculatorOperation("-")
    object Divide: CalculatorOperation("/")
    object Multiply: CalculatorOperation("x")

}