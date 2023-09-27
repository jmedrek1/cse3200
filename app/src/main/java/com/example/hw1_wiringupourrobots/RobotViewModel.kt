package com.example.hw1_wiringupourrobots

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

const val TURN_COUNT_KEY = "TURN_COUNT_KEY"

class RobotViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    var currentTurn = 0
    private var turnCount: Int
        get() = savedStateHandle.get(TURN_COUNT_KEY) ?: 0
        set(value) = savedStateHandle.set(TURN_COUNT_KEY, value)
}