package com.example.hw1_wiringupourrobots

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "RobotViewModel"

private var TURN_COUNT_KEY = "TURN_COUNT_KEY"
private var ROBOT_IMAGE_RES_KEY = "ROBOT_IMAGE_RES_KEY"
private var ROBOT_ENERGY_KEY = "ROBOT_ENERGY_KEY"
class RobotViewModel(private val savedStateHandle : SavedStateHandle) : ViewModel() {
    init {
        Log.d(TAG, "Instance of RobotViewModel created.")
    }
    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "Instance of RobotViewModel destroyed.")
    }

    // TURN_COUNT
    fun advanceTurn() {
        val currentTurn = savedStateHandle.get<Int>(TURN_COUNT_KEY) ?: -1
        val newTurn = (currentTurn + 1) % 3 // if turn is 3 -> goes to 1
        savedStateHandle[TURN_COUNT_KEY] = newTurn
    }
    fun getTurnCount(): Int {
        return savedStateHandle.get<Int>(TURN_COUNT_KEY) ?: -1
    }

    // ROBOT_IMAGE_RES
    fun setRobotImageRes(res : Int) {
        savedStateHandle[ROBOT_IMAGE_RES_KEY] = res
    }
    fun getRobotImageRes(): Int {
        return savedStateHandle.get<Int>(ROBOT_IMAGE_RES_KEY) ?: R.drawable.king_of_detroit_robot_white_large
    }

    // ROBOT_ENERGY_KEY
    fun setRobotEnergy(energy : Int) {
        savedStateHandle[ROBOT_ENERGY_KEY] = energy
    }
    fun getRobotEnergy(): Int {
        return savedStateHandle.get<Int>(ROBOT_ENERGY_KEY) ?: 0
    }
}