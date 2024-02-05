package com.example.simplecalculator

data class CalculatorState( 
    val number1:String="",
    val number2:String="",
    val operator: CalculatorOperation? = null
)
