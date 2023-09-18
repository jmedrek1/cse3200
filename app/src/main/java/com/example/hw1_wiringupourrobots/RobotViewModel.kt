package com.example.hw1_wiringupourrobots

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "RobotViewModel"
class RobotViewModel : ViewModel() {
    init {
        Log.d(TAG, "instance of RobotViewModel created.")
    }
    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "instance of RobotViewModel about to be destroyed.")
    }
}