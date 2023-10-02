package com.example.hw1_wiringupourrobots

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

const val TURN_COUNT_KEY = "TURN_COUNT_KEY"
const val PURCHASES_KEY = "PURCHASES_KEY"
const val ENERGY_KEY = "ENERGY_KEY"

class RobotViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    var purchases: MutableList<String>
        get() = savedStateHandle.get(PURCHASES_KEY) ?: mutableListOf()
        set(value) = savedStateHandle.set(PURCHASES_KEY, value)
    var energy: Int
        get() = savedStateHandle.get(ENERGY_KEY) ?: 0
        set(value) = savedStateHandle.set(ENERGY_KEY, value)
    private var turnCount: Int
        get() = savedStateHandle.get(TURN_COUNT_KEY) ?: 0
        set(value) = savedStateHandle.set(TURN_COUNT_KEY, value)

    fun incrementTurnCount() {
        turnCount++
        savedStateHandle.set(TURN_COUNT_KEY, turnCount)
    }
}