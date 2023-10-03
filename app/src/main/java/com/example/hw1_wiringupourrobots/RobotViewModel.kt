package com.example.hw1_wiringupourrobots

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "RobotViewModel"
private var TURN_COUNT_KEY = "TURN_COUNT_KEY"
class RobotViewModel(private val savedStateHandle : SavedStateHandle) : ViewModel() {
    init {
        Log.d(TAG, "Instance of RobotViewModel created.")
    }
    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "Instance of RobotViewModel destroyed.")
    }

    fun advanceTurn() {
        val currentTurn = savedStateHandle.get<Int>(TURN_COUNT_KEY) ?: -1
        val newTurn = (currentTurn + 1) % 3 // if turn is 3 -> goes to 1
        savedStateHandle[TURN_COUNT_KEY] = newTurn
    }
    fun getTurnCount(): Int {
        return savedStateHandle.get<Int>(TURN_COUNT_KEY) ?: -1
    }
//    private var turnCount : Int
//        get() = savedStateHandle.get(TURN_COUNT_KEY) ?: 0
//        set(value) = savedStateHandle.set(TURN_COUNT_KEY, value)
}