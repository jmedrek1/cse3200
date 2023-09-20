package com.example.hw1_wiringupourrobots

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "RobotViewModel"
private var TURN_COUNT_KEY = "TURN_COUNT_KEY"
class RobotViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    //private var turnCount: Int = 0 // not in given code for hw2
    init {
        Log.d(TAG, "instance of RobotViewModel created.")
    }
    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "instance of RobotViewModel about to be destroyed.")
    }

    fun advanceTurn() {
//        turnCount++
//        if (turnCount > 3) {
//            turnCount = 1
//        }
        val currentTurn = savedStateHandle.get<Int>(TURN_COUNT_KEY) ?: 0
        val newTurn = (currentTurn % 3) + 1
        savedStateHandle[TURN_COUNT_KEY] = newTurn
    }
    fun getTurnCount(): Int {
        return savedStateHandle.get<Int>(TURN_COUNT_KEY) ?: 0
    }
//    from Prof's lecture
//    private var turnCount: Int
//        get() = savedStateHandle.get(TURN_COUNT_KEY) ?: 0
//        set(value) = savedStateHandle.set(TURN_COUNT_KEY, value)
}